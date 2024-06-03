package com.mysite.library.controller;

import com.mysite.library.dto.UserDTO;
import com.mysite.library.entity.UserEntity;
import com.mysite.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
//마이페이지: 회원정보 수정, 회원정보 삭제(계정탈퇴), 회원도서대여 현황 확인
public class MyPageController {

    @Autowired
    private UserService userService;

//    //마이페이지 리턴
//    @GetMapping("/mypage")
//    public String mypage(Principal principal) {
//        String name = principal.getName();
//        UserEntity user = userService.findByName(아니뭐어쩌라고요);
//        return "mypage";
//    }

    @GetMapping("/mypage")
    public String myPage() {
        return "mypage";
    }


    //update: 수정 페이지 리턴
    @GetMapping("/update/{idx}")
    public String updateForm(@PathVariable("idx") Long idx, Model model){
        UserDTO userDTO = userService.findById(idx);
        if (userDTO != null) {
            model.addAttribute("userDTO", userDTO);
            return "update";
        } else {
            model.addAttribute("error", "회원정보를 찾을 수 없습니다");
            return "error";
        }
    }

    //update: 수정된 정보 전송
    @PostMapping("/mypage/update")
    public String update(@ModelAttribute UserDTO userDTO, Model model) {
        UserDTO updateUser = userService.update(userDTO);
        if (updateUser != null) {
            model.addAttribute("user", updateUser);
            model.addAttribute("message", "업데이트 성공했습니다 !");
            return "index";
        } else {
            model.addAttribute("error", "업데이트 오류. 수정을 실패했습니다.");
            return "update";
        }
    }

//    //delete
//    @GetMapping("/delete/{idx}")
//    public String deleteForm(Principal principal, @PathVariable("idx") Long idx) {
//        UserDTO userDTO = userService.findById(idx);
//
//        return "delete";
//    }

    //borrow
    @GetMapping("/borrowBook")
    public String borrowBook() {
        return "borrowBook";
    }

    //logout
    @GetMapping("/logout")
    public String logoutPage() {
        return "logout";
    }
}
