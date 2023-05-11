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
    private String adminPass;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       UserDetailsService appUserDetailsService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.appUserDetailsService = appUserDetailsService;
    }

//    public void init() {
//        if (userRepository.count() == 0 && userRoleRepository.count() == 0) {
//            UserRoleEntity adminRole = new UserRoleEntity().setUserRole(UserRoleEnum.ADMIN);
//            UserRoleEntity moderatorRole = new UserRoleEntity().setUserRole(UserRoleEnum.MODERATOR);
//
//            adminRole = userRoleRepository.save(adminRole);
//            moderatorRole = userRoleRepository.save(moderatorRole);
//
//            initAdmin(List.of(adminRole, moderatorRole));
//            initModerator(List.of(moderatorRole));
//            initUser(List.of());
//        }
//    }
//
//    private void initAdmin(List<UserRoleEntity> roles) {
//        UserEntity admin = new UserEntity().
//                setUserRoles(roles).
//                setFirstName("Admin").
//                setLastName("Adminov").
//                setEmail("admin@example.com").
//                setPassword(passwordEncoder.encode(adminPass));
//
//        userRepository.save(admin);
//    }
//
//    private void initModerator(List<UserRoleEntity> roles) {
//        UserEntity moderator = new UserEntity().
//                setUserRoles(roles).
//                setFirstName("Moderator").
//                setLastName("Moderatorov").
//                setEmail("moderator@example.com").
//                setPassword(passwordEncoder.encode(adminPass));
//
//        userRepository.save(moderator);
//    }
//
//    private void initUser(List<UserRoleEntity> roles) {
//        UserEntity user = new UserEntity().
//                setUserRoles(roles).
//                setFirstName("User").
//                setLastName("Userov").
//                setEmail("user@example.com").
//                setPassword(passwordEncoder.encode(adminPass));
//
//        userRepository.save(user);
//    }

    public void registerAndLogin(UserDTO userDTO) {
        UserEntity newUser = new UserEntity()
                .setName(userDTO.getName())
                .setRole(UserRole.NORMAL)
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