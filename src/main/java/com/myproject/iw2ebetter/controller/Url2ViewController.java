package com.myproject.iw2ebetter.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Url2ViewController {

    @GetMapping("/toSquare")
    public String toSquare(){
        return "square/square";
    }
}
