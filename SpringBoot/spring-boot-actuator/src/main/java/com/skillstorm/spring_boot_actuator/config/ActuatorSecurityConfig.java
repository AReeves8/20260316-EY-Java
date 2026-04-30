package com.skillstorm.spring_boot_actuator.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class ActuatorSecurityConfig {

    @Bean
    UserDetailsService userDetailsService() {

        // withDefaultPasswordEncoder is deprecated - want to use BCrypt instead to encode passwords
        UserDetails actuatorUser = User.withDefaultPasswordEncoder()
            .username("actuator")
            .password("admin")
            .roles("ACTUATOR_ADMIN")
            .build();

        return new InMemoryUserDetailsManager(actuatorUser);
    }


    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> 
                auth
                    // allowing anyone to access basic actuator endpoints
                    .requestMatchers("/actuator/health", "/actuator/health").permitAll()

                    // more specific endpoints need to be accessed by an admin only
                    .requestMatchers("/actuator/**").hasRole("ACTUATOR_ADMIN")

                    // anything else we aren't worried about right now for this demo
                    .anyRequest().permitAll()
            )
            .httpBasic(Customizer.withDefaults())
            .csrf(csrf -> csrf.disable());

        return http.build();
    }

}
