package com.amongalen.jokesapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.logout.RedirectServerLogoutSuccessHandler;
import org.springframework.security.web.server.authentication.logout.ServerLogoutSuccessHandler;

import java.net.URI;

@Configuration
@EnableWebFluxSecurity
public class WebSecurityConfig {

    @Bean
    protected SecurityWebFilterChain configure(ServerHttpSecurity http) {
        http
                .authorizeExchange()
                .pathMatchers("/index", "", "/", "index", "/findJoke")
                .permitAll()
//                .matchers(EndpointRequest.to(FeaturesEndpoint.class))
//                .permitAll()
                .anyExchange()
                .authenticated()
                .and()
                .httpBasic()
                .and()
                .formLogin()
                .and()
                .logout()
                .logoutSuccessHandler(getLogoutSuccessHandler())
                .and()
                .csrf()
                .disable();
        return http.build();
    }

    private ServerLogoutSuccessHandler getLogoutSuccessHandler() {
        RedirectServerLogoutSuccessHandler handler = new RedirectServerLogoutSuccessHandler();
        handler.setLogoutSuccessUrl(URI.create("index"));
        return handler;
    }

    @Bean
    public MapReactiveUserDetailsService userDetailsService() {
        UserDetails user =
                User.withUsername("user")
                        .password(passwordEncoder().encode("user"))
                        .roles("USER")
                        .build();

        return new MapReactiveUserDetailsService(user);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
