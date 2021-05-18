package net.tack.school.notes.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

//@Component
//public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

//    @Autowired
//    private ObjectMapper mapper;
//
//    @Override
//    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
//        UsernamePasswordAuthenticationToken authRequest;
//        try (InputStream is = request.getInputStream()) {
//            UserDto details = mapper.readValue(is,UserDto.class);
//            authRequest = new UsernamePasswordAuthenticationToken(details.getLogin(), details.getPass());
//        } catch (IOException e) {
//            authRequest = new UsernamePasswordAuthenticationToken("", "");
//        }
//        setDetails(request, authRequest);
//        return this.getAuthenticationManager().authenticate(authRequest);
//    }

//}
