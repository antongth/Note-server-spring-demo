package net.tack.school.notes.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import net.tack.school.notes.model.User;
import net.tack.school.notes.validator.MaxLenValid;
import net.tack.school.notes.validator.PassLenValid;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Date;

//https://habr.com/ru/post/513072/
public enum UserDTO {;
    private interface Id {          int getId(); }

    private interface FirstName {   @NotBlank
                                    @MaxLenValid
                                    @Pattern(regexp = "[A-Za-zА-Яа-я- ]*", message = "must be only char")
                                    String getFirstName(); }

    private interface LastName {    @NotBlank
                                    @MaxLenValid
                                    @Pattern(regexp = "[A-Za-zА-Яа-я- ]*", message = "must be only char")
                                    String getLastName(); }

    private interface Patronymic {  @MaxLenValid
                                    @Pattern(regexp = "[A-Za-zА-Яа-я- ]*", message = "must be only char")
                                    String getPatronymic(); }

    private interface Login {       @NotBlank
                                    @MaxLenValid
                                    @Pattern(regexp = "[A-Za-zА-Яа-я0-9]*", message = "must be char or num")
                                    String getLogin(); }

    private interface TimeRegistered { Date getTimeRegistered(); }

    private interface Online {      boolean isOnline(); }

    private interface Deleted {     boolean isDeleted(); }

    private interface SuperUser {   boolean isSuperUser(); }

    private interface Rating {      int getRating(); }

    private interface Password {    @NotBlank
                                    @PassLenValid
                                    String getPassword(); }

    private interface OldPassword { @NotBlank
                                    @PassLenValid
                                    String getOldPassword(); }

    private interface NewPassword { @NotBlank
                                    @PassLenValid
                                    String getNewPassword(); }

    public enum Request{;
        @Setter
        @Getter
        @NoArgsConstructor
        @AllArgsConstructor
        @JsonInclude(JsonInclude.Include.NON_NULL)
        //@JsonPropertyOrder({ "name", "id" })
        public static class RegisterUser implements FirstName, LastName, Patronymic, Login, Password {
            String firstName;
            String lastName;
            String patronymic;
            String login;
            String password;

            public User ToUser() {
                User user = new User();
                user.setFirstName(firstName);
                user.setLastName(lastName);
                user.setPatronymic(patronymic);
                user.setLogin(login);
                user.setPass(password);
                return user;
            }
        }

        @Setter
        @Getter
        @NoArgsConstructor
        @AllArgsConstructor
        @JsonInclude(JsonInclude.Include.NON_NULL)
        public static class LoginUser implements Login, Password {
            String login;
            String password;
        }

        @Setter
        @Getter
        @NoArgsConstructor
        @AllArgsConstructor
        @JsonInclude(JsonInclude.Include.NON_NULL)
        public static class DeleteUser implements Password {
            String password;
        }

        @Setter
        @Getter
        @NoArgsConstructor
        @AllArgsConstructor
        @JsonInclude(JsonInclude.Include.NON_NULL)
        public static class EditUser implements FirstName, LastName, Patronymic, OldPassword, NewPassword {
            String firstName;
            String lastName;
            String patronymic;
            String oldPassword;
            String newPassword;
        }

        @Setter
        @Getter
        @NoArgsConstructor
        @AllArgsConstructor
        @JsonInclude(JsonInclude.Include.NON_NULL)
        public static class SetRelationshipForUser implements Login {
            String login;
        }

        @Setter
        @Getter
        @NoArgsConstructor
        @AllArgsConstructor
        @JsonInclude(JsonInclude.Include.NON_NULL)
        public static class SetUserFollowingForUser implements Login {
            String login;
        }

        @Setter
        @Getter
        @NoArgsConstructor
        @AllArgsConstructor
        @JsonInclude(JsonInclude.Include.NON_NULL)
        public static class SetUserIgnoreUser implements Login {
            String login;
        }
    }

    public enum Response {;
        @Setter
        @Getter
        @NoArgsConstructor
        @AllArgsConstructor
        @JsonInclude(JsonInclude.Include.NON_NULL)
        public static class RegisterUser implements FirstName, LastName, Patronymic, Login {
            String firstName;
            String lastName;
            String patronymic;
            String login;

            public RegisterUser(User user) {
                this.firstName = user.getFirstName();
                this.lastName = user.getLastName();
                this.patronymic = user.getPatronymic();
                this.login = user.getLogin();
            }
        }

        @Setter
        @Getter
        @NoArgsConstructor
        @AllArgsConstructor
        @JsonInclude(JsonInclude.Include.NON_NULL)
        public static class GetCurrentUser implements FirstName, LastName, Password, Login {
            String firstName;
            String lastName;
            String password;
            String login;

            public GetCurrentUser(User user) {
                this.firstName = user.getFirstName();
                this.lastName = user.getLastName();
                this.password = user.getPass();
                this.login = user.getLogin();
            }
        }

        @Setter
        @Getter
        @NoArgsConstructor
        @AllArgsConstructor
        @JsonInclude(JsonInclude.Include.NON_NULL)
        public static class EditUser implements Id, FirstName, LastName, Password, Login {
            int id;
            String firstName;
            String lastName;
            String password;
            String login;

            public EditUser(User user) {
                this.id = user.getId();
                this.firstName = user.getFirstName();
                this.lastName = user.getLastName();
                this.password = user.getPass();
                this.login = user.getLogin();
            }
        }

        @Setter
        @Getter
        @NoArgsConstructor
        @AllArgsConstructor
        @JsonInclude(JsonInclude.Include.NON_NULL)
        public static class AllUsersToList implements Id, FirstName, LastName, Patronymic, Password, Login,
                                                        TimeRegistered, Online, Deleted, SuperUser, Rating {
            int     id;
            String  firstName;
            String  lastName;
            String  patronymic;
            String  password;
            String  login;
            Date    timeRegistered;
            boolean online;
            boolean deleted;
            @JsonProperty("super")
            @JsonAlias("super")
            boolean superUser;
            int rating;
        }
    }
}
