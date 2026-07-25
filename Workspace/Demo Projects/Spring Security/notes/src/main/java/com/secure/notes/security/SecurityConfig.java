package com.secure.notes.security;

import com.secure.notes.entity.AppRole;
import com.secure.notes.entity.AppUsers;
import com.secure.notes.entity.Role;
import com.secure.notes.entity.User;
import com.secure.notes.repository.RoleRepository;
import com.secure.notes.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

import java.time.LocalDate;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public CommandLineRunner initData(RoleRepository roleRepository, UserRepository userRepository) {
        return args -> {
            Role user1Role = roleRepository.findByRoleName(AppRole.ROLE_USER).orElseGet(() -> roleRepository.save(new Role(AppRole.ROLE_USER)));
            Role adminRole = roleRepository.findByRoleName(AppRole.ROLE_ADMIN).orElseGet(() -> roleRepository.save(new Role(AppRole.ROLE_ADMIN)));
            if (!userRepository.existsByUserName(AppUsers.user1.toString())) {
                User user = new User("user1", "user1@example.conm", "{noop}password1");
                user.setCredentialNonExpired(true);
                user.setAccountNonExpired(true);
                user.setAccountExpiryDate(LocalDate.now().plusYears(1));
                user.setEnabled(true);
                user.setAccountNonLocked(false);
                user.setCredentialsExpiryDate(LocalDate.now().plusYears(1));
                user.setSignUpMethod("email");
                user.setTwoFactorEnabled(false);
                user.setRole(user1Role);
                userRepository.save(user);
            }
            if (!userRepository.existsByUserName(AppUsers.admin.toString())) {
                User user = new User("admin", "admin@example.conm", "{noop}password2");
                user.setCredentialNonExpired(true);
                user.setAccountNonExpired(true);
                user.setAccountExpiryDate(LocalDate.now().plusYears(1));
                user.setEnabled(true);
                user.setAccountNonLocked(false);
                user.setCredentialsExpiryDate(LocalDate.now().plusYears(1));
                user.setSignUpMethod("email");
                user.setTwoFactorEnabled(false);
                user.setRole(adminRole);
                userRepository.save(user);
            }
        };
    }

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(
                (requests) -> requests
                        .requestMatchers("/contact").permitAll()
                        .requestMatchers("/public/**").permitAll()
//                        .requestMatchers("/admin").denyAll()
                        .anyRequest().authenticated());
//        http.formLogin(withDefaults());
        http.csrf(AbstractHttpConfigurer::disable);
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.httpBasic(withDefaults());
        return http.build();
    }

//    @Bean
//    public UserDetailsService userDetailsService(DataSource dataSource) {
////        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//        JdbcUserDetailsManager manager = new JdbcUserDetailsManager(dataSource);
//        if (!manager.userExists("user1")) {
//            manager.createUser(User.withUsername("user1").password("{noop}password1").roles("USER").build());
//        }
//        if (!manager.userExists("admin")) {
//            manager.createUser(User.withUsername("admin").password("{noop}password2").roles("ADMIN").build());
//        }
//        return manager;
//    }
}
