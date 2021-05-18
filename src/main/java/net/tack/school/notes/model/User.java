package net.tack.school.notes.model;

import lombok.*;
import net.tack.school.notes.model.params.UserState;
import net.tack.school.notes.model.params.UserStatus;

import java.util.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User{
    private int id;
    private String login;
    private String pass;

    private int rating;
    private String firstName;
    private String lastName;
    private String patronymic;
    private Date timeRegistered;
    private String sessionId;
    private Date lastAction;
    private String oldPassword;

    transient private UserStatus status;
    transient private UserState state;

    //те за кем юзер следит
    private List<User> following;
    //те кто следит за юзером
    private List<User> followers;
    //те кого юзер игнорит
    private List<User> ignore;
    //те кот игнорит юзер
    private List<User> ignoredBy;
    private List<Note> notes;
    private List<Comment> comments;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return getId() == user.getId() && Objects.equals(getLogin(), user.getLogin());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getLogin());
    }
}
