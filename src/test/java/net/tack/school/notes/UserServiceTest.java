package net.tack.school.notes;

import net.tack.school.notes.dto.mappers.AllUsersToListMapper;
import net.tack.school.notes.dto.requestparams.GetUsersBy;
import net.tack.school.notes.dto.requestparams.SortByRating;
import net.tack.school.notes.model.params.UserState;
import net.tack.school.notes.model.params.UserStatus;
import net.tack.school.notes.service.UserService;
import net.tack.school.notes.debug.DebugDaoImpl;
import net.tack.school.notes.dto.UserDTO;
import net.tack.school.notes.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class UserServiceTest {
    @Autowired
    private UserService userService;
    @Autowired
    private DebugDaoImpl debugDao;

    private User user;
    private User klein;
    private User admin;
    private final int idleTime = 120000;

    public void insertTestData(){
        User test = new User();
        test.setLogin("test1");
        test.setPass("12345678");
        test.setLastName("t");
        test.setFirstName("t");

        userService.registerUser(test);
        test.setLogin("test2");
        userService.registerUser(test);
        test.setLogin("test3");
        userService.registerUser(test);
        test.setLogin("test4");
        userService.registerUser(test);
        test.setLogin("test5");
        userService.registerUser(test);
        test.setLogin("test6");
        userService.registerUser(test);
    }

    @BeforeEach
    public void setUp() {
        debugDao.clear();
        debugDao.ins();

        user = new User();
        user.setLogin("Isak17");
        user.setPass("apple111");
        user.setLastName("Newton");
        user.setFirstName("Isak");
        user.setStatus(UserStatus.USER);

        klein = new User();
        klein.setLogin("Klein");
        klein.setPass("12345678");
        klein.setLastName("notanykey");
        klein.setFirstName("notsysadmin");
        klein.setPatronymic("devops");
        //klein.setSessionId(null);
        klein.setStatus(UserStatus.USER);
        klein.setState(UserState.ACTIVE);
        klein.setRating(1);

        admin = new User();
        admin.setLogin("admin");
        admin.setPass("123456");
        admin.setLastName("anykey");
        admin.setFirstName("sysadmin");
        admin.setPatronymic("notdevops");
        //admin.setSessionId(null);
        admin.setStatus(UserStatus.SUPER);
        admin.setState(UserState.ACTIVE);
    }

    @Test
    public void testThrowable() throws DuplicateKeyException {
        Throwable thrown = assertThrows(DuplicateKeyException.class, () -> {
            userService.registerUser(admin);
        });
        assertNotNull(thrown.getMessage());
    }

   @Test
   void testUserMapper() {
        UserDTO.Response.AllUsersToList userDto = new UserDTO.Response.AllUsersToList();
        userDto.setLogin("Isak17");
        userDto.setPassword("apple");
        userDto.setLastName("N");
        userDto.setFirstName("Isak");
        userDto.setPatronymic("F");
        userDto.setOnline(false);
        userDto.setSuperUser(false);
        User userFromMapper = AllUsersToListMapper.INSTANCE.userDtoToUser(userDto);
        Assertions.assertAll("user",
                () -> assertEquals(user.getLogin(), userFromMapper.getLogin()));
   }

    @Test
    void testRegister() {
        userService.registerUser(user);
        Assertions.assertAll(
                () -> assertTrue(user.getId() >= 1));
    }

    @Test
    void testLogin() {
        String userToken = userService.login(userService.getUserByLogin(klein.getLogin()));
        Assertions.assertAll(
                () -> assertNotNull(userToken));
    }

    @Test
    void testLogout() {
        userService.registerUser(user);
        String token = userService.login(user);
        User user = userService.getUserBySessionId(token);
        Assertions.assertAll(
                () -> assertNotNull(token));
        userService.logout(user);
        Throwable thrown = assertThrows(ResponseStatusException.class, () -> {
            userService.getUserBySessionId(token);
        });
        assertNotNull(thrown.getMessage());
    }

    @Test
    void testDelete() {
        User user = userService.getUserByLogin(klein.getLogin());
        userService.delete(user);
        Assertions.assertAll(
                () -> assertSame(UserState.DELETED, user.getState()),
                () -> assertEquals(UserState.DELETED, userService.getUserByLogin(klein.getLogin()).getState()));
    }

    @Test
    void testGetUserBySessionId() {
        User user = userService.getUserByLogin(klein.getLogin());
        String token = userService.login(user);
        assertEquals(user, userService.getUserBySessionId(token));

    }

    @Test
    void testGetCurrentUser() {
        User user = userService.getUserByLogin(klein.getLogin());
        String token = userService.login(user);
        assertEquals(user, userService.getCurrentUser(user));
    }

    @Test
    void testGetUserByLogin() {
        User user = userService.getUserByLogin(klein.getLogin());
        assertEquals(klein.getLogin(), user.getLogin());
    }

    @Test
    void testUpdate() {
        User kleinFromDb = userService.getUserByLogin(klein.getLogin());
        String kleinToken = userService.login(kleinFromDb);
        kleinFromDb.setLastName("Jenkins");
        userService.update(kleinFromDb);
        Assertions.assertAll(
                () -> assertEquals("Jenkins", userService.getUserByLogin(kleinFromDb.getLogin()).getLastName()));
    }

    @Test
    void testGetUsersAllNoSort() {
        SortByRating sortBy = null;
        GetUsersBy getBy = null;
        int from = 0;
        int count = 0;
        String kleinToken = userService.login(userService.getUserByLogin(admin.getLogin()));

        List<User> users = userService.getUsers(klein, sortBy, getBy, from, count);

        assertEquals(2, users.size());
    }

    @Test
    void testGetUsersAllSorted() {
        insertTestData();
        SortByRating sortBy = SortByRating.valueOf("asc".toUpperCase());
        GetUsersBy getBy = null;
        int from = 0;
        int count = 0;
        String kleinToken = userService.login(userService.getUserByLogin(admin.getLogin()));

        List<User> users = userService.getUsers(klein, sortBy, getBy, from, count);

        assertTrue(users.get(0).getRating() > users.get(1).getRating());
    }

    @Test
    void testSetUserToSuper() {
        String token = userService.login(userService.getUserByLogin(admin.getLogin()));
        User user = userService.getUserByLogin(klein.getLogin());
        userService.setUserToSuper(admin, user.getId());
        assertEquals(UserStatus.SUPER, userService.getUserByLogin(klein.getLogin()).getStatus());

    }
    @Test
    void testUserRelationshipsFollowingFollowers(){
        User kleinFromDb = userService.getUserByLogin(klein.getLogin());
        User adminFromDb = userService.getUserByLogin(admin.getLogin());
        userService.login(kleinFromDb);
        userService.setUserFollowingForUser(
                kleinFromDb,
                adminFromDb);

        assertTrue(kleinFromDb.getFollowing().size() >= 1);
        assertTrue(adminFromDb.getFollowers().size() >= 1);

        userService.setUserFollowingForUserOff(
                kleinFromDb,
                adminFromDb);

        kleinFromDb = userService.getUserByLogin(klein.getLogin());
        adminFromDb = userService.getUserByLogin(admin.getLogin());
        assertEquals(kleinFromDb.getFollowing().size(), 0);
        assertEquals(adminFromDb.getFollowers().size(), 0);

    }

    @Test void testUserRelationshipsIgnoreIgnoredBy(){
        User kleinFromDb = userService.getUserByLogin(klein.getLogin());
        User adminFromDb = userService.getUserByLogin(admin.getLogin());
        userService.login(kleinFromDb);
        userService.setUserIgnoringUser(
                kleinFromDb,
                adminFromDb);

        assertTrue(kleinFromDb.getIgnore().size() >= 1);
        assertTrue(adminFromDb.getIgnoredBy().size() >= 1);

        userService.setUserIgnoringUserOff(
                kleinFromDb,
                adminFromDb);

        kleinFromDb = userService.getUserByLogin(klein.getLogin());
        adminFromDb = userService.getUserByLogin(admin.getLogin());
        assertEquals(kleinFromDb.getIgnore().size(), 0);
        assertEquals(adminFromDb.getIgnoredBy().size(), 0);
    }

    @Test
    void testUserRelationshipsAutoFollowingOFF(){
        User kleinFromDb = userService.getUserByLogin(klein.getLogin());
        userService.login(kleinFromDb);
        userService.setUserFollowingForUser(
                kleinFromDb,
                userService.getUserByLogin(admin.getLogin()));
        userService.setUserIgnoringUser(
                kleinFromDb,
                userService.getUserByLogin(admin.getLogin()));

        assertTrue(kleinFromDb.getFollowing().size() == 0);
    }

    @Test
    void testUserRelationshipsAutoIgnoreOFF(){
        User kleinFromDb = userService.getUserByLogin(klein.getLogin());

        userService.login(kleinFromDb);
        userService.setUserIgnoringUser(
                kleinFromDb,
                userService.getUserByLogin(admin.getLogin()));
        userService.setUserFollowingForUser(
                kleinFromDb,
                userService.getUserByLogin(admin.getLogin()));

        assertEquals(kleinFromDb.getIgnore().size(), 0);
    }

    @Test
    void testGetUserFollowers() {
        SortByRating sortBy = null;
        GetUsersBy getBy = GetUsersBy.FOLLOWERS;
        int from = 0;
        int count = 0;
        User kleinFromDb = userService.getUserByLogin(klein.getLogin());
        User adminFromDb = userService.getUserByLogin(admin.getLogin());
        userService.login(kleinFromDb);
        userService.setUserFollowingForUser(
                kleinFromDb,
                adminFromDb);

        List<User> users = userService.getUsers(adminFromDb, sortBy, getBy, from, count);

        assertTrue(users.contains(kleinFromDb));
    }

    @Test
    void testGetUsersFollowing() {
        SortByRating sortBy = null;
        GetUsersBy getBy = GetUsersBy.FOLLOWING;
        int from = 0;
        int count = 0;
        User kleinFromDb = userService.getUserByLogin(klein.getLogin());
        userService.login(kleinFromDb);
        userService.setUserFollowingForUser(
                kleinFromDb,
                userService.getUserByLogin(admin.getLogin()));

        List<User> users = userService.getUsers(kleinFromDb, sortBy, getBy, from, count);

        assertTrue(users.size()==1);
        assertEquals(users, kleinFromDb.getFollowing());
    }

    @Test
    void testGetUserIgnore() {
        SortByRating sortBy = null;
        GetUsersBy getBy = GetUsersBy.IGNORE;
        int from = 0;
        int count = 0;
        User kleinFromDb = userService.getUserByLogin(klein.getLogin());
        userService.login(kleinFromDb);
        userService.setUserIgnoringUser(
                kleinFromDb,
                userService.getUserByLogin(admin.getLogin()));

        List<User> users = userService.getUsers(kleinFromDb, sortBy, getBy, from, count);

        assertEquals(users.size(), 1);
        assertEquals(admin.getLogin(), users.get(0).getLogin());
    }

    @Test
    void testGetUserIgnoredBy() {
        SortByRating sortBy = null;
        GetUsersBy getBy = GetUsersBy.IGNORED_BY;
        int from = 0;
        int count = 0;
        User kleinFromDb = userService.getUserByLogin(klein.getLogin());
        User adminFromDb = userService.getUserByLogin(admin.getLogin());
        userService.login(kleinFromDb);
        userService.setUserIgnoringUser(
                kleinFromDb,
                userService.getUserByLogin(admin.getLogin()));

        List<User> users = userService.getUsers(adminFromDb, sortBy, getBy, from, count);

        assertEquals(users.size(), 1);
    }

}