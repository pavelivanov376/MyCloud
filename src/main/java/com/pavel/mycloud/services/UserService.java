package com.pavel.mycloud.services;

import com.pavel.mycloud.dtos.UserDTO;
import com.pavel.mycloud.entities.UserEntity;
import com.pavel.mycloud.entities.UserRole;
import com.pavel.mycloud.repositories.UserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService appUserDetailsService;
    private final FolderService folderService;


    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, UserDetailsService appUserDetailsService, FolderService folderService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.appUserDetailsService = appUserDetailsService;
        this.folderService = folderService;
    }

    public void registerAndLogin(UserDTO userDTO) {
        Long homeFolderId = folderService.createHomeFolderForUser(userDTO);

        UserEntity newUser = new UserEntity()
                .setName(userDTO.getName())
                .setRole(UserRole.NORMAL)
                .setHomeFolderID(homeFolderId)
                .setPassword(passwordEncoder.encode(userDTO.getPassword()));

        userRepository.save(newUser);

        UserDetails userDetails = appUserDetailsService.loadUserByUsername(newUser.getName());

        Authentication auth = new UsernamePasswordAuthenticationToken(
                userDetails,
                userDetails.getPassword(),
                userDetails.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    public void login(UserDTO userDTO) {
        UserDetails userDetails = appUserDetailsService.loadUserByUsername(userDTO.getName());

        Authentication auth = new UsernamePasswordAuthenticationToken(
                userDetails,
                userDetails.getPassword(),
                userDetails.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(auth);
    }
}