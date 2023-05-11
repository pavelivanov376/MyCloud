package com.pavel.mycloud.configs;

import com.pavel.mycloud.entities.UserRole;
import com.pavel.mycloud.repositories.UserRepository;
import com.pavel.mycloud.services.AppUserDetailsService;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfiguration {
    //TODO expose pass encoder, securityfilterchain, userdetailssetvice

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new Pbkdf2PasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeRequests().requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                .antMatchers("/", "/api/users/register", "/api/users/login", "/api/download/{filename}", "/api/folder/{id}").permitAll()
//                .antMatchers("/", "/api/users/register", "/api/*", "/login", "users/register", "/api/files", "/api/download/{filename}", "/api/folder/{id}").permitAll()
                .antMatchers("/api/upload", "/api/folder/create").hasRole(UserRole.NORMAL.name())
                .anyRequest().authenticated()//.permitAll()
                .and()
                .formLogin()//.loginPage("/api/users/login")
                .usernameParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY)
                .passwordParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY)
                .defaultSuccessUrl("/")
                .failureForwardUrl("/users/login-error")
                .and()
                .logout().logoutUrl("/users/logout").invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
        ;// For all non-listed just logged is needed

        return httpSecurity.build();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return new AppUserDetailsService(userRepository);
    }
}
