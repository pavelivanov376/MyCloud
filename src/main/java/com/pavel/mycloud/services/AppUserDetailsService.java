package com.pavel.mycloud.services;

import com.pavel.mycloud.entities.UserEntity;
import com.pavel.mycloud.repositories.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class AppUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    public AppUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return userRepository.findByName(username)
                .map(this::map)
                .orElseThrow(() -> new UsernameNotFoundException("User with name: " + username + " not found"));
    }

    public UserDetails map(UserEntity userEntity) {
        return User.builder()
                .username(userEntity.getName())
                .password(userEntity.getPassword())
                .authorities(new SimpleGrantedAuthority("ROLE_" + userEntity.getRole().name()))
                .build();
    }
}
