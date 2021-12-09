package com.myproject.iw2ebetter.controller;


import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class TestController {

    @ResponseBody
    @GetMapping("/test")
    public String test(){
        return "test";
    }

    @PostMapping("/edio")
    @ResponseBody
    public String test2(@RequestParam("title")String name,@RequestParam("content")String content){
        System.out.println(name);
        System.out.println(content);
        return content;
    }

}
