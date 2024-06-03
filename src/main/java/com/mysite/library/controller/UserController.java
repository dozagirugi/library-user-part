package com.mysite.library.controller;

import com.mysite.library.dto.UserDTO;
import com.mysite.library.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class UserController {
    //생성자 주입
    private final UserService userService;

    //registerForm: 회원가입 페이지 출력
    @GetMapping(value = {"/user/register","register"})
    public String registerForm(Model model) {
        model.addAttribute("user", new UserDTO());
        return "register";
    }

    //register: 회원가입 정보 전송
    @PostMapping(value = {"/user/register","register"})
    public String register(@ModelAttribute("userDTO") UserDTO userDTO,
                           BindingResult bindingResult,
                           Model model) {

        System.out.println("PostMapping register page");
        System.out.println("userDTO = " + userDTO);

        if (bindingResult.hasErrors()) {
            return "register";
        }
        userService.save(userDTO);

        model.addAttribute("success", true);
        return "login";
    }

    //loginForm: 로그인 페이지 출력
    @GetMapping(value = {"/user/login","/login"})
    public String loginForm(Principal principal) {
        System.out.println("GetMapping login page");

        if(principal == null) {
            return "login";
        }
        return "/main";
    }

    //logout
    @GetMapping("/logout")
    public String logoutPage() {
        return "logout";
    }
}
