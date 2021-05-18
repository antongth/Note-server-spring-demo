package net.tack.school.notes.api;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.tack.school.notes.dto.mappers.AllUsersToListMapper;
import net.tack.school.notes.dto.requestparams.Cookies;
import net.tack.school.notes.dto.requestparams.GetUsersBy;
import net.tack.school.notes.dto.requestparams.SortByRating;
import net.tack.school.notes.model.params.UserStatus;
import net.tack.school.notes.service.UserService;
import net.tack.school.notes.dto.UserDTO;
import net.tack.school.notes.dto.SuccessResponse;
import net.tack.school.notes.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class UserEndPoints {
    @NonNull
    private final UserService userService;

    @PostMapping(path = "/accounts", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public UserDTO.Response.RegisterUser RegisterUser(@RequestBody @Valid UserDTO.Request.RegisterUser userDto,
                                                      HttpServletResponse response) {

        User user = userDto.ToUser();
        userService.registerUser(user);
        String sessionId = userService.login(user);
        Cookie cookie = new Cookie(Cookies.JAVASESSIONID.toString(), sessionId);
        //cookie.setSecure(true);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        return new UserDTO.Response.RegisterUser(user);
    }

    @PostMapping(path = "/sessions")
    public void Login(@RequestBody @Valid UserDTO.Request.LoginUser userDto,
                      HttpServletResponse response) {

        User user = userService.getUserByLogin(userDto.getLogin());
        if (!user.getPass().equals(userDto.getPassword()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password", new Exception("pass incorrect"));
        String sessionId = userService.login(user);
        Cookie cookie = new Cookie(Cookies.JAVASESSIONID.toString(), sessionId);
        //cookie.setSecure(true);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
    }

    @DeleteMapping(path = "/sessions")
    public SuccessResponse Logout(@CookieValue(value = "JAVASESSIONID") String sessionId,
                                  HttpServletResponse response) {

        User user = userService.getUserBySessionId(sessionId);
        userService.logout(user);
        Cookie cookie = new Cookie(Cookies.JAVASESSIONID.toString(), sessionId);
        cookie.setMaxAge(0);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        return new SuccessResponse();
    }

    @GetMapping(path = "/account", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDTO.Response.GetCurrentUser getCurrentUser(@CookieValue(value = "JAVASESSIONID") String sessionId) {

        User user = userService.getUserBySessionId(sessionId);
        userService.getCurrentUser(user);
        return new UserDTO.Response.GetCurrentUser(user);
    }

    @DeleteMapping(path = "/accounts", consumes = MediaType.APPLICATION_JSON_VALUE)
    public SuccessResponse deleteUser(@CookieValue(value = "JAVASESSIONID") String sessionId,
                                      @RequestBody UserDTO.Request.DeleteUser userDto,
                                      HttpServletResponse response) {

        User user = userService.getUserBySessionId(sessionId);
        if (!userDto.getPassword().equals(user.getPass()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "pass", new Exception("pass incorrect"));
        userService.logout(user);
        userService.delete(user);
        Cookie cookie = new Cookie(Cookies.JAVASESSIONID.toString(), sessionId);
        cookie.setMaxAge(0);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        return new SuccessResponse();
    }

    @PutMapping(path = "/accounts", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public UserDTO.Response.EditUser editUser(@CookieValue(value = "JAVASESSIONID") String sessionId,
                                 @RequestBody @Valid UserDTO.Request.EditUser userDto) {

        User user = userService.getUserBySessionId(sessionId);
        if (!userDto.getOldPassword().equals(user.getPass()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "oldPassword", new Exception("old pass incorrect"));
        user.setPass(userDto.getNewPassword());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPatronymic(userDto.getPatronymic());
        userService.update(user);
        return new UserDTO.Response.EditUser(user);
    }

    @PutMapping(path = "/accounts/{uid}/super")
    public SuccessResponse setUserToSuper(@CookieValue(value = "JAVASESSIONID") String sessionId,
                                          @PathVariable("uid") Integer uid) {

        User user = userService.getUserBySessionId(sessionId);
        if (user.getStatus() != UserStatus.SUPER)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "type", new Throwable("user have no permit"));
        userService.setUserToSuper(user, uid);
        return new SuccessResponse();
    }

    @GetMapping(path = "/accounts", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserDTO.Response.AllUsersToList> getAllUsersToList(@CookieValue(value = "JAVASESSIONID") String sessionId,
                                                                @RequestParam(required = false) SortByRating sortByRating,
                                                                @RequestParam(required = false) GetUsersBy type,
                                                                @RequestParam(value = "from", required = false, defaultValue = "0") int from,
                                                                @RequestParam(required = false, defaultValue = "0") int count) {

        User user = userService.getUserBySessionId(sessionId);
//        SortByRating sortBy = null;
//        GetUsersBy getBy = null;
//        if (sortByRating != null)
//            sortBy = SortByRating.valueOf(sortByRating.toUpperCase());
//        if (type != null)
//            getBy = GetUsersBy.valueOf(type.toUpperCase());
        List<User> listOfUsers = userService.getUsers(user, sortByRating, type, from, count);
        List<UserDTO.Response.AllUsersToList> list = new ArrayList<>(listOfUsers.size());
        for (var u : listOfUsers)
            list.add(AllUsersToListMapper.INSTANCE.userToUserDto(u));
        return list;
    }

    @PostMapping(path = "/followings")
    public SuccessResponse setUserFollowingForUser(@CookieValue(value = "JAVASESSIONID") String sessionId,
                                                   @RequestBody @Valid UserDTO.Request.SetUserFollowingForUser userDto) {

        User user = userService.getUserBySessionId(sessionId);
        User forUser = userService.getUserByLogin(userDto.getLogin());
        userService.setUserFollowingForUser(user, forUser);
        return new SuccessResponse();
    }

    @PostMapping(path = "/ignore")
    public SuccessResponse setUserIgnoringUser(@CookieValue(value = "JAVASESSIONID") String sessionId,
                                             @RequestBody @Valid UserDTO.Request.SetUserIgnoreUser userDto) {

        User user = userService.getUserBySessionId(sessionId);
        User toUser = userService.getUserByLogin(userDto.getLogin());
        userService.setUserIgnoringUser(user, toUser);
        return new SuccessResponse();
    }

    @DeleteMapping(path = "/followings/{login}")
    public SuccessResponse setUserFollowingForUserOff(@CookieValue(value = "JAVASESSIONID") String sessionId,
                                                       @PathVariable("login") String login) {

        User user = userService.getUserBySessionId(sessionId);
        User forUser = userService.getUserByLogin(login);
        userService.setUserFollowingForUserOff(user, forUser);
        return new SuccessResponse();
    }

    @DeleteMapping(path = "/ignore/{login}")
    public SuccessResponse setUserIgnoreUserOff(@CookieValue(value = "JAVASESSIONID") String sessionId,
                                                 @PathVariable("login") String login) {

        User user = userService.getUserBySessionId(sessionId);
        User toUser = userService.getUserByLogin(login);
        userService.setUserIgnoringUserOff(user, toUser);
        return new SuccessResponse();
    }
}
