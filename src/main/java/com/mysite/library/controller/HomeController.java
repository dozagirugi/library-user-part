package com.mysite.library.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    //기본 페이지 요청 메서드
    //@GetMapping("/") : 이 주소를 치면 해당 메소드가 실행된다.
    @GetMapping(value = {"/","/index"})
    public String index() {
        //"index"라는 HTML 파일을 브라우저에 띄우겠다
        //templates 폴더의 index.html을 찾아감.
        return "index";
    }

    @GetMapping("/main")
    public String main() {
        return "main";
    }
}
