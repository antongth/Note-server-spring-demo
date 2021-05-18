//package net.thumbtack.school.notes.security;
//
//import net.thumbtack.school.notes.model.User;
//import net.thumbtack.school.notes.model.UserType;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.util.Collection;
//
//public class SecurityUser implements UserDetails {
//    private final String login;
//    private final String pass;
//
//    public SecurityUser(String login, String pass) {
//        this.login = login;
//        this.pass = pass;
//    }
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return null;
//    }
//
//    @Override
//    public String getPassword() {
//        return pass;
//    }
//
//    @Override
//    public String getUsername() {
//        return login;
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }
//
//    public static UserDetails fromUser(User user) {
//        return new org.springframework.security.core.userdetails.User(
//                user.getLogin(),
//                user.getPass(),
//                null);
//    }
//
//}
