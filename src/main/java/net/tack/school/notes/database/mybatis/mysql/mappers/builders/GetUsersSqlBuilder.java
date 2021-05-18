package net.tack.school.notes.database.mybatis.mysql.mappers.builders;

import net.tack.school.notes.dto.requestparams.GetUsersBy;
import net.tack.school.notes.dto.requestparams.SortByRating;
import net.tack.school.notes.model.User;
import net.tack.school.notes.model.params.UserStatus;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

public class GetUsersSqlBuilder {

    public static String buildGetUsers(
            @Param("user") final User user,
            @Param("count") final Integer count,
            @Param("sortBy") final SortByRating sortBy,
            @Param("getBy") final GetUsersBy getBy) {
        return new SQL(){{
            if (user.getStatus() == UserStatus.SUPER)
                SELECT("id, firstName, lastName, patronymic, pass, login, timeRegistered, sessionId, state, rating, status");
            else
                SELECT("id, firstName, lastName, patronymic, pass, login, timeRegistered, sessionId, state, rating");
            FROM("notes.user");
            LEFT_OUTER_JOIN("notes.usersession ON user.id = usersession.userId");
            if (getBy == GetUsersBy.SUPER && user.getStatus() == UserStatus.SUPER)
                WHERE("type = 'SUPER'");
            if (getBy == GetUsersBy.DELETED)
                WHERE("deleted = true");
            if (getBy == GetUsersBy.FOLLOWERS)
                WHERE("id = (SELECT userIdFollower FROM notes.follower WHERE userId = #{user.id})");
            if (getBy == GetUsersBy.FOLLOWING)
                WHERE("id = (SELECT userId FROM notes.follower WHERE userIdFollower = #{user.id})");
            if (getBy == GetUsersBy.IGNORE)
                WHERE("id = (SELECT userIdIgnored FROM notes.ignore WHERE userId = #{user.id})");
            if (getBy == GetUsersBy.IGNORED_BY)
                WHERE("id = (SELECT userId FROM notes.ignore WHERE userIdIgnored = #{user.id})");
            if (sortBy == SortByRating.ASC)
                ORDER_BY("rating DESC");
            if (sortBy == SortByRating.DESC)
                ORDER_BY("rating ASC");
            if (count != 0)
                LIMIT(count);
        }}.toString();
    }

}
