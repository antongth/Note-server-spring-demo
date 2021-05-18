package net.tack.school.notes.dao;

import net.tack.school.notes.dto.requestparams.GetUsersBy;
import net.tack.school.notes.dto.requestparams.SortByRating;
import net.tack.school.notes.model.User;

import java.util.List;

public interface UserDao {

    int insert(User user);

    int login(User user);

    int logout(User user);

    int delete(User user);

    int update(User user);

    int setUserFollowingForUser(User thisUser, User forUser);

    int setUserFollowingForUserOff(User thisUser, User forUser);

    int setUserIgnoringUser(User thisUser, User toUser);

    int setUserIgnoringUserOff(User thisUser, User toUser);

    List<User> getUsers(User user, int count, SortByRating sortBy, GetUsersBy getBy);

    User getUserBySessionId(String sessionId);

    int setUserToSuper(int id);

    String getUserSessionByUser(User user);
}
