package com.ezybytes.demo_spring_security.configuration.security.prod;

import com.ezybytes.demo_spring_security.exceptionHandling.CustomAccessDeniedHandler;
import com.ezybytes.demo_spring_security.exceptionHandling.CustomBasicAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;

import javax.sql.DataSource;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@Profile("prod")
@RequiredArgsConstructor
public class SecurityProdConfig {
    private final DataSource dataSource;

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.sessionManagement(smc -> smc.invalidSessionUrl("/invalid-session").maximumSessions(1).maxSessionsPreventsLogin(true).expiredUrl("/expired-session"))
                .requiresChannel(rcc -> rcc.anyRequest().requiresSecure())//only https
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/notices", "/contact", "/error", "/welcome", "/users/register").permitAll()
                        .requestMatchers("/myAccount", "/myBalance", "/myLoans", "/myCards", "/invalid-session", "/expired-session").authenticated());
        http.formLogin(withDefaults());
        http.httpBasic(httpBasicConfig -> httpBasicConfig.authenticationEntryPoint(new CustomBasicAuthenticationEntryPoint()));
        http.exceptionHandling(exceptionHandlingConfig -> exceptionHandlingConfig.accessDeniedHandler(new CustomAccessDeniedHandler()));
        return http.build();
    }

//	@Bean
//	UserDetailsService getUserDetails() {
//        UserDetails user = User.withUsername("admin").password("{bcrypt}$2a$12$KKeVeC/qCP6/2RPK0ZYN7u7yo08zqet1L6AjNPNsXNK2OAQ..tle.").authorities("admin").build();
//        UserDetails admin = User.withUsername("user").password("{noop}EazyBytes@12345").authorities("read").build();
//        return new InMemoryUserDetailsManager(user, admin);
//		return new JdbcUserDetailsManager(dataSource);
//	}

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public CompromisedPasswordChecker compromisedPasswordChecker() {
        return new HaveIBeenPwnedRestApiPasswordChecker();
    }
}
