package com.mysite.library.controller;

import com.mysite.library.dto.UserDTO;
import com.mysite.library.entity.UserEntity;
import com.mysite.library.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.val;
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

    @GetMapping("/mypage")
    public String myPage() {
        return "mypage";
    }

//    //마이페이지 리턴
//    @GetMapping("/mypage")
//    public String mypage(Model model, Principal principal) {
//        UserEntity user = userService.findByName(principal.getName)
    //model.addAttribute("userID"
//        return "/mypage";
//    }

//    @GetMapping("/mypage")
//    public String mypage(Principal principal) {
//
//        if (principal == null) {
//            return "mypage";
//        }
//        return "/update/{id}";
//    }


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
    @PostMapping("/update")
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


    @GetMapping("/delete")
    public String deletePage(Principal principal, Model model) {
        String email = principal.getName();
        UserDTO user = userService.findByEmail(email);
        model.addAttribute("user", user);
        System.out.println("유저번호:" + user.getIdx());
        return "delete";
    }
    //delete
    @PostMapping("/delete")
    public String deleteById(@ModelAttribute("user") UserDTO userDTO) {
        userService.deleteByid(userDTO.getIdx());

        return "redirect:/";
    }



    //borrow
    @GetMapping("/borrowBook")
    public String borrowBook() {
        return "borrowBook";
    }
}
