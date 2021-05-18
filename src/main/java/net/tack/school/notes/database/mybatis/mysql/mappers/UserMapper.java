package net.tack.school.notes.database.mybatis.mysql.mappers;

import net.tack.school.notes.dto.requestparams.GetUsersBy;
import net.tack.school.notes.dto.requestparams.SortByRating;
import net.tack.school.notes.database.mybatis.mysql.mappers.builders.GetUsersSqlBuilder;
import net.tack.school.notes.model.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("SELECT * FROM notes.user WHERE login = #{login}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "sessionId", column = "id", javaType = String.class,
                    one = @One(select = "getUserSessionByUser", fetchType = FetchType.EAGER)),
            @Result(property = "following", column = "id", javaType = List.class,
                    many = @Many(select = "getFollowing", fetchType = FetchType.LAZY)),
            @Result(property = "ignore", column = "id", javaType = List.class,
                    many = @Many(select = "getIgnore", fetchType = FetchType.LAZY)),
            @Result(property = "followers", column = "id", javaType = List.class,
                    many = @Many(select = "getFollowers", fetchType = FetchType.LAZY)),
            @Result(property = "ignoredBy", column = "id", javaType = List.class,
                    many = @Many(select = "getIgnoredBy", fetchType = FetchType.LAZY))
    })
    User getUserByLogin(String login);

    @Select("SELECT * from user JOIN usersession ON user.id = usersession.userId WHERE sessionId = #{sessionId}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "following", column = "id", javaType = List.class,
                    many = @Many(select = "getFollowing", fetchType = FetchType.LAZY)),
            @Result(property = "ignore", column = "id", javaType = List.class,
                    many = @Many(select = "getIgnore", fetchType = FetchType.LAZY)),
            @Result(property = "followers", column = "id", javaType = List.class,
                    many = @Many(select = "getFollowers", fetchType = FetchType.LAZY)),
            @Result(property = "ignoredBy", column = "id", javaType = List.class,
                    many = @Many(select = "getIgnoredBy", fetchType = FetchType.LAZY))
    })
    User getUserBySessionId(String sessionId);

    @Select("SELECT notes.usersession.sessionId " +
            "FROM notes.usersession " +
            "WHERE userId = #{id}")
    String getUserSessionByUser(User user);

    @Select("SELECT * FROM notes.user WHERE id = " +
            "( SELECT userId FROM notes.follower WHERE userIdFollower = #{id} )")
    List<User> getFollowing(int id);

    @Select("SELECT * FROM notes.user WHERE id = " +
            "( SELECT userIdIgnored FROM notes.ignore WHERE userId = #{id} )")
    List<User> getIgnore(int id);

    @Select("SELECT * FROM notes.user WHERE id = " +
            "( SELECT userId FROM notes.ignore WHERE userIdIgnored = #{id} )")
    List<User> getIgnoredBy(int id);

    @Select("SELECT * FROM notes.user WHERE id = " +
            "( SELECT userIdFollower FROM notes.follower WHERE userId = #{id} )")
    List<User> getFollowers(int id);

    @Insert("INSERT INTO notes.user (login, pass, firstName, lastName, patronymic, timeRegistered) " +
            "VALUES (#{login}, #{pass}, #{firstName}, #{lastName}, #{patronymic}, #{timeRegistered})")
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    int insert(User user);

    @Insert("INSERT INTO notes.usersession (userId, sessionId, lastAction) VALUES (#{id}, #{sessionId}, #{lastAction})")
    int login(User user);

    @Delete("DELETE FROM notes.usersession WHERE sessionId = #{sessionId}")
    int logout(User user);

    @Delete("UPDATE notes.user SET state = 'DELETED' WHERE id = #{id}")
    int delete(User user);

    @Update("UPDATE notes.user " +
            "SET pass = #{pass}, " +
                "lastName = #{lastName}, " +
                "firstName = #{firstName}, " +
                "patronymic = #{patronymic} " +
            "WHERE id = #{id}")
    int update(User user);

    @SelectProvider(type = GetUsersSqlBuilder.class, method = "buildGetUsers")
    //@MapKey("login")
    List<User> getUsers(@Param("user") User user,
                        @Param("count") Integer count,
                        @Param("sortBy") SortByRating sortBy,
                        @Param("getBy") GetUsersBy getBy);

    @Update("UPDATE notes.user SET status = 'SUPER' WHERE id = #{id}")
    //@SelectKey(statement="call rowcount()", keyProperty="", before=false, resultType=int.class)
    int setUserToSuper(int id);

    @Insert("INSERT INTO notes.follower (userId, userIdFollower) VALUES (#{forUser.id}, #{thisUser.id})")
    //@SelectKey(statement="call rowcount()", keyProperty="thisUser.following", before=false, resultType=List.class)
    //@Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")//resultType=int.class
    int setUserFollowingForUser(@Param("thisUser") User thisUser, @Param("forUser") User forUser);

    @Insert("INSERT INTO notes.ignore (userId, userIdIgnored) VALUES (#{thisUser.id}, #{toUser.id})")
    //@Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")//resultType=int.class
    int setUserIgnoringUser(@Param("thisUser") User thisUser, @Param("toUser") User toUser);

    @Delete("DELETE FROM notes.follower WHERE userId = #{forUser.id} AND userIdFollower = #{thisUser.id}")
    int setUserFollowingForUserOff(@Param("thisUser") User thisUser, @Param("forUser") User forUser);

    @Delete("DELETE FROM notes.ignore WHERE userId = #{thisUser.id} AND userIdIgnored = #{toUser.id}")
    int setUserIgnoringUserOff(@Param("thisUser") User thisUser, @Param("toUser") User toUser);
}
