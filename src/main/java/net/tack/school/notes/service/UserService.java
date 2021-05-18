package net.tack.school.notes.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.tack.school.notes.dto.requestparams.GetUsersBy;
import net.tack.school.notes.dto.requestparams.SortByRating;
import net.tack.school.notes.model.params.UserState;
import net.tack.school.notes.database.mybatis.mysql.daoimpl.UserDaoImpl;
import net.tack.school.notes.model.User;
import net.tack.school.notes.model.UserSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@RequiredArgsConstructor
@Service
public class UserService extends UserSessionService {
    @NonNull
    private final UserDaoImpl userDao;

    @Value("${user_idle_timeout}")
    private int idleTime;


    public void registerUser(User user) {
        user.setTimeRegistered(new Date());
        userDao.insert(user);
    }

    public String login(User user) {
        if (user.getState() == UserState.DELETED)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Deleted", new Exception("user is deleted"));
        if (user.getSessionId() != null)
            logout(user);
        String sessionId = UUID.randomUUID().toString();
        user.setSessionId(sessionId);
        user.setLastAction(new Date());
        userDao.login(user);

        UserSession userSession = new UserSession(this, idleTime, user, new Date());
        userSession.start();
        putSession(user.getLogin(), userSession);

        return sessionId;
    }

    public void logout(User user) {
        userDao.logout(user);
        removeSession(user.getLogin());
    }

    public void delete(User user) {
        userDao.delete(user);
        user.setState(UserState.DELETED);
        removeSession(user.getLogin());
    }

    public User getUserBySessionId(String sessionId) {
        return userDao.getUserBySessionId(sessionId);
    }

    public User getCurrentUser(User user) {
        updateTimeOfAction(user.getLogin());
        return user;
    }

    public User getUserByLogin(String login) {
        return userDao.getUserByLogin(login);
    }

    public void update(User user) {
        updateTimeOfAction(user.getLogin());
        userDao.update(user);
    }

    public List<User> getUsers(User user, SortByRating sortBy, GetUsersBy getBy, int from, int count) {
        updateTimeOfAction(user.getLogin());
        int range;
        if (from != 0 && count == 0)
            range = count;
        else range = count + from;
//        switch ((getBy == null ? GetUsersBy.NON : getBy)) {
//            default:
//                userList = userDao.getUsers(count, sortBy);
//        }
        List<User> list = userDao.getUsers(user, range, sortBy, getBy);
        if (list == null)
            return new ArrayList<>();
        try {
            return list.subList(from, list.size());
        } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "from", e);
        }
    }

    public void setUserToSuper(User user, int uid) {
        updateTimeOfAction(user.getLogin());
        if (userDao.setUserToSuper(uid) != 1)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "uid", new Throwable("user id is incorrect"));
    }

    public void setUserFollowingForUser(User thisUser, User forUser) {
        updateTimeOfAction(thisUser.getLogin());
        if (thisUser.getIgnore().contains(forUser)) {
            userDao.setUserIgnoringUserOff(thisUser, forUser);
            thisUser.getIgnore().remove(forUser);
        }
        if (userDao.setUserFollowingForUser(thisUser, forUser) != 1)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "login", new Exception("no effect was made"));
    }

    public void setUserIgnoringUser(User thisUser, User toUser) {
        updateTimeOfAction(thisUser.getLogin());
        if (thisUser.getFollowing().contains(toUser)) {
            userDao.setUserFollowingForUserOff(thisUser, toUser);
            thisUser.getFollowing().remove(toUser);
        }
        if (userDao.setUserIgnoringUser(thisUser, toUser) != 1)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "login", new Exception("no effect was made"));
    }

    public void setUserFollowingForUserOff(User thisUser, User forUser) {
        updateTimeOfAction(thisUser.getLogin());
        if (userDao.setUserFollowingForUserOff(thisUser, forUser) != 1)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "login", new Exception("already not following"));
    }

    public void setUserIgnoringUserOff(User thisUser, User toUser) {
        updateTimeOfAction(thisUser.getLogin());
        if (userDao.setUserIgnoringUserOff(thisUser, toUser) != 1)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "login", new Exception("already not ignoring"));
    }
}
