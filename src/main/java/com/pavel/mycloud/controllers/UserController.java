package com.pavel.mycloud.controllers;

import com.pavel.mycloud.dtos.UserDTO;
import com.pavel.mycloud.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/api/users/register")
    public String register() {
        return "files/register.html";
    }

    @PostMapping("/api/users/register")
    public String register(UserDTO userDTO) {

        userService.registerAndLogin(userDTO);
        return "redirect:/";
    }

    @GetMapping("/api/users/login")
    public String login() {
        return "files/login.html";
    }

    @PostMapping("/api/users/login")
    public String login(UserDTO userDTO) {
        userService.login(userDTO);
        return "redirect:/";
    }

//    // NOTE: This should be post mapping!
//    @PostMapping("/users/login-error")
//    public String onFailedLogin(
//            @ModelAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY) String userName,
//            RedirectAttributes redirectAttributes) {
//
//        redirectAttributes.addFlashAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY, userName);
//        redirectAttributes.addFlashAttribute("bad_credentials",
//                true);
//
//        return "redirect:/users/login";
//    }


}
