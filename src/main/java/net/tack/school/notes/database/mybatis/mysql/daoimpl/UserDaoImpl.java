package net.tack.school.notes.database.mybatis.mysql.daoimpl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.tack.school.notes.dto.requestparams.GetUsersBy;
import net.tack.school.notes.dto.requestparams.SortByRating;
import net.tack.school.notes.dao.UserDao;
import net.tack.school.notes.database.mybatis.mysql.mappers.UserMapper;
import net.tack.school.notes.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;


@RequiredArgsConstructor
@Repository
public class UserDaoImpl implements UserDao {
    @NonNull
    private final UserMapper userMapper;

    @Override
    public int insert(User user) {
        return userMapper.insert(user);
    }

    @Override
    public int login(User user) {
        return userMapper.login(user);
    }

    @Override
    public int logout(User user) {
        return userMapper.logout(user);
    }

    @Override
    public int delete(User user) {
        return userMapper.delete(user);
    }

    @Override
    public int update(User user) {
        return userMapper.update(user);
    }

    @Override
    public int setUserFollowingForUser(User thisUser, User forUser) {
        return userMapper.setUserFollowingForUser(thisUser, forUser);
    }

    @Override
    public int setUserFollowingForUserOff(User thisUser, User forUser) {
        return userMapper.setUserFollowingForUserOff(thisUser, forUser);
    }

    @Override
    public int setUserIgnoringUser(User thisUser, User toUser) {
        return userMapper.setUserIgnoringUser(thisUser, toUser);
    }

    @Override
    public int setUserIgnoringUserOff(User thisUser, User toUser) {
        return userMapper.setUserIgnoringUserOff(thisUser, toUser);
    }

    @Override
    public List<User> getUsers(User user, int count, SortByRating sortBy, GetUsersBy getBy) {
        return userMapper.getUsers(user, count, sortBy, getBy);
    }

    @Override
    public int setUserToSuper(int id) {
        return userMapper.setUserToSuper(id);
    }

    @Override
    public String getUserSessionByUser(User user) {
        return userMapper.getUserSessionByUser(user);
    }

    public User getUserByLogin(String login) {
        return Optional.ofNullable(userMapper.getUserByLogin(login)).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.BAD_REQUEST, "Login", new Exception("no such user by Login")));
    }

    public User getUserBySessionId(String sessionId) {
        return Optional.ofNullable(userMapper.getUserBySessionId(sessionId)).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cookie", new Exception("no such user by Cookie")));
    }
}
