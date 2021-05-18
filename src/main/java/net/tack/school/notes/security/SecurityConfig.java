//package net.thumbtack.school.notes.configuration;
//
//
//import net.thumbtack.school.notes.model.UserType;
//import net.thumbtack.school.notes.security.UserDetailsServiceImpl;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//    @Autowired
//    private UserDetailsServiceImpl userDetailsService;
//
////    @Autowired
////    private CustomAuthenticationFilter customAuthenticationFilter;
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService);
//    }
//
//    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable().formLogin().disable()
//                .authorizeRequests()
//                .antMatchers(HttpMethod.POST, "/api/accounts").permitAll()
//                .antMatchers(HttpMethod.POST, "/api/sessions").permitAll()
//                .antMatchers("/api/**").permitAll()
//                .antMatchers("/api/debug/**").hasRole(UserType.SUPER.name())
//                .anyRequest().authenticated().and().httpBasic();
//                //.addFilterAt(customAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
//    }
//
//    //    @Bean
////    @Override
////    public AuthenticationManager authenticationManagerBean() throws Exception {
////        return super.authenticationManagerBean();
////    }
//}
