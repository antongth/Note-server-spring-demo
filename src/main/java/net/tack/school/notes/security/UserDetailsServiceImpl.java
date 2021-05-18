//package net.thumbtack.school.notes.security;
//
//
//import lombok.NonNull;
//import lombok.RequiredArgsConstructor;
//import net.thumbtack.school.notes.database.daoimpl.UserDaoImpl;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//@RequiredArgsConstructor
////@Service
//public class UserDetailsServiceImpl implements UserDetailsService {
//
//    @NonNull
//    private UserDaoImpl userDao;
//
//    @Override
//    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
//        return SecurityUser.fromUser(userDao.getUserByLogin(login).orElseThrow(() -> new UsernameNotFoundException("username " + login + " not found")));
//    }
//
//}