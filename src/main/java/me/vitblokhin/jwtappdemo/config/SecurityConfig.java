package me.vitblokhin.jwtappdemo.config;

import me.vitblokhin.jwtappdemo.security.jwt.JwtConfigurer;
import me.vitblokhin.jwtappdemo.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationEntryPoint entryPointUnauthorizedHandler;

    private final String ADMIN_ENDPOINT = "/api/v1/admin/**";
    private final String LOGIN_ENDPOINT = "/api/v1/auth/login";

    @Autowired
    public SecurityConfig(JwtTokenProvider jwtTokenProvider, AuthenticationEntryPoint entryPointUnauthorizedHandler) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.entryPointUnauthorizedHandler = entryPointUnauthorizedHandler;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(this.entryPointUnauthorizedHandler)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/", "/api/v1/users/**").permitAll()
                .antMatchers("/csrf",
                        "/v2/api-docs",
                        "/webjars/**",
                        "/swagger-resources/**",
                        "/swagger-ui.html").permitAll()
                .antMatchers(LOGIN_ENDPOINT).permitAll()
                .antMatchers(ADMIN_ENDPOINT).hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .apply(new JwtConfigurer(jwtTokenProvider));
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
} // class SecurityConfig
