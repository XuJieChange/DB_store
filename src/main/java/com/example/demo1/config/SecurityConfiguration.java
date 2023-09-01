package com.example.demo1.config;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import com.example.demo1.dao.entity.JwtBlacklist;
import com.example.demo1.dao.response.JwtBlacklistReponsitory;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import com.example.demo1.service.UserService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final UserService userService;
    private final JwtBlacklistReponsitory jwtBlacklistReponsitory;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        System.out.println("securityFilterChain");
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> request
                        .requestMatchers(new AntPathRequestMatcher("/auth/**"))
                        .permitAll().anyRequest().authenticated())
//                .logout(logout -> logout
//                        .logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout", "POST"))
//                        .invalidateHttpSession(true)
//                        .deleteCookies("JSESSIONID")
//                        .clearAuthentication(true)
//                        .logoutSuccessHandler((request, response, authentication) -> {
//                            String authHeader = request.getHeader("Authorization");
//                            if (authHeader != null && authHeader.startsWith("Bearer ")) {
//                                String jwt = authHeader.substring(7);
//                                JwtBlacklist blacklistedToken = new JwtBlacklist();
//                                blacklistedToken.setToken(jwt);
//                                jwtBlacklistReponsitory.save(blacklistedToken); // 使用save方法来添加令牌到黑名单
//                            }
//                            response.setStatus(HttpServletResponse.SC_OK);
//                        }))
                .sessionManagement(manager -> manager.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                ;
        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService.userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
    }


}
