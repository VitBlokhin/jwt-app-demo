package me.vitblokhin.jwtappdemo.security;

import lombok.extern.slf4j.Slf4j;
import me.vitblokhin.jwtappdemo.model.User;
import me.vitblokhin.jwtappdemo.security.jwt.JwtUser;
import me.vitblokhin.jwtappdemo.security.jwt.JwtUserFactory;
import me.vitblokhin.jwtappdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service("userDetailsService")
public class JwtUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Autowired
    public JwtUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUsername(username);
        if(user == null) {
            throw new UsernameNotFoundException("Username " + username + " not found");
        }

        JwtUser jwtUser = JwtUserFactory.create(user);
        log.info("loadUserByUsername: user with username {} successfully loaded", username);
        return jwtUser;
    }
} // class JwtUserDetailsService
