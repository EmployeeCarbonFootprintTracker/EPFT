package org.kevin.garrett.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/test").permitAll() // Allow access to  test endpoint
                        .anyRequest().authenticated() // Require authentication for all other requests
                )
                .formLogin(form -> form
                        .loginPage("/login") // Specify a custom login page if needed at some pointt
                        .permitAll() // Allow access to the login page
                )
                .logout(logout -> logout
                        .logoutUrl("/logout") // Define a custom logout URL if needed at some point
                        .logoutSuccessUrl("/") // Redirect after successful logout
                        .invalidateHttpSession(true) // Invalidate session
                        .deleteCookies("JSESSIONID") // Remove cookies
                );

        return http.build();
    }
}
