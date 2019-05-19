package me.vitblokhin.jwtappdemo.controller;

import me.vitblokhin.jwtappdemo.dto.AuthenticationRequestDto;
import me.vitblokhin.jwtappdemo.dto.AuthenticationResponseDto;
import me.vitblokhin.jwtappdemo.model.User;
import me.vitblokhin.jwtappdemo.repository.UserRepository;
import me.vitblokhin.jwtappdemo.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class AuthenticationRestController {
    private final String URL = "/api/v1/auth/";

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final UserDetailsService jwtUserDetailsService;

    @Autowired
    public AuthenticationRestController(AuthenticationManager authenticationManager,
                                        JwtTokenProvider jwtTokenProvider,
                                        UserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.jwtUserDetailsService = userDetailsService;
    }

    @PostMapping(URL + "login")
    public ResponseEntity<AuthenticationResponseDto> login(@RequestBody AuthenticationRequestDto requestDto){
        try{
            String username = requestDto.getUsername();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, requestDto.getPassword()));
            UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(username);

            String token = jwtTokenProvider.createToken(userDetails);

            AuthenticationResponseDto authenticationResponse = new AuthenticationResponseDto();
            authenticationResponse.setUsername(username);
            authenticationResponse.setToken(token);

            return ResponseEntity.ok(authenticationResponse);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password");
        }

    }
} // class AuthenticationRestController
