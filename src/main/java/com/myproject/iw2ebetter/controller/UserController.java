package com.myproject.iw2ebetter.controller;

import com.github.pagehelper.PageInfo;
import com.myproject.iw2ebetter.pojo.UserDTO;
import com.myproject.iw2ebetter.pojo.Img;
import com.myproject.iw2ebetter.pojo.ResponseData;
import com.myproject.iw2ebetter.pojo.Role;
import com.myproject.iw2ebetter.pojo.User;
import com.myproject.iw2ebetter.component.CustomUserDetailService;
import com.myproject.iw2ebetter.service.RoleService;
import com.myproject.iw2ebetter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Controller
public class UserController {

    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @PostMapping("/register/user")
    public String registerUser(UserDTO user){
        //普通用户
        userService.registerUser(user);
        return "redirect:/toLogin";
    }

    @PostMapping("/register/host")
    public String registerHost(UserDTO user){
        Set<Role> roles = new HashSet<>();
        Role host = roleService.getRoleByrole("host");
        roles.add(host);
        user.setRoles(roles);
        userService.registerUser(user);
        return "redirect:/toLogin";
    }

    @PostMapping("/register/admin")
    public String registerAdmin(UserDTO user){
        Set<Role> roles = new HashSet<>();
        Role admin = roleService.getRoleByrole("admin");
        roles.add(admin);
        user.setRoles(roles);
        userService.registerUser(user);
        return "redirect:/toLogin";
    }


    @ResponseBody
    @GetMapping("/usernameIsExisted")
    public Boolean usernameIsExisted(String username){
        UserDTO user = userService.getUserByUsername(username);
        return user != null;
    }

    @GetMapping({"/index","/index.html"})
    public String toIndex(){
        return "index";
    }


    @GetMapping({"/toLogin"})
    public String toLogin(){
        return "user/login";
    }

    @GetMapping("/toRegister")
    public String toRegister(){
        return "user/register";
    }

    @GetMapping("/adminRegister")
    public String adminRegister(){
        return "user/registerAdmin";
    }

    @PostMapping("/failLogin")
    public String failLogin(HttpServletRequest req){
        req.setAttribute("message","用户名或密码错误");
        return "user/login";
    }

    @PostMapping("/successLogin")
    public String successLogin(HttpSession session){
        UserDetails principal = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //保存用户信息
        UserDTO user = userService.getUserByUsername(principal.getUsername());
        session.setAttribute("user",user);
        return "redirect:/index";
    }

    @GetMapping("/userInfo/{username}")
    public String userInfo(@PathVariable("username") String username,Model model){
        UserDTO user = userService.getUserByUsername(username);
        model.addAttribute("user",user);
        return "user/userInfo";
    }

    @GetMapping("/toUpdateInfo/{username}")
    public String toUpdatePage(@PathVariable("username") String username,Model model){
        UserDTO user = userService.getUserByUsername(username);
        model.addAttribute("user",user);
        return "user/userUpdate";
    }

    @PutMapping("/updateInfo/{username}")
    public String updateUserInfo(@PathVariable("username") String username,UserDTO user,HttpSession session){
        userService.updateInfoByUsername(user,username);
        UserDTO user1 = userService.getUserByUsername(username);
        session.setAttribute("user",user1);
        return "redirect:/index";
    }

    @GetMapping("/toUpdatePwd")
    public String toUpdatePwdPage(){
        return "user/updatePwd";
    }

    @GetMapping("/updatePwd")
    public String updatePwd(@RequestParam("oldPwd")String oldPwd,@RequestParam("newPwd") String newPwd,Model model){
        if(userService.checkPwd(oldPwd)){
            userService.updatePwd(newPwd);
            return "redirect:/logout";
        }else {
            model.addAttribute("message","旧密码错误");
            return "forward:/toUpdatePwd";
        }
    }

    @ResponseBody
    @GetMapping("/ajaxUsers")
    public ResponseData getUsersAjax(@RequestParam(value="pageNo",defaultValue = "1") Integer pageNo, @RequestParam(value = "pageSize",defaultValue = "4")Integer pageSize){
        PageInfo<User> pageInfo = userService.getUsers(pageNo,pageSize);
        return ResponseData.success("查询成功").addData("pageInfo",pageInfo);
    }

    @GetMapping("/users")
    public String users(){
        return "user/users";
    }

    @ResponseBody
    @GetMapping("/ajaxResetPwd")
    public ResponseData resetPwd(@RequestParam("uid") Integer uid){
        int status = userService.resetPwd(uid);
        if(status == 0){
            return ResponseData.fail("重置失败，请重试");
        }else{
            return ResponseData.success("重置成功");
        }
    }

    @ResponseBody
    @GetMapping("/getUser")
    public ResponseData getUser(){
        UserDTO user = userService.getUserWithoutThatHost();
        if(user != null){
            return ResponseData.success("查询成功").addData("user",user);
        }else{
            return ResponseData.success("查询失败，请重试");
        }
    }

    @GetMapping("/saveUserIntoSession")
    public void saveUserIntoSession(HttpSession session){
        UserDTO user = userService.getUserWithoutThatHost();
        session.setAttribute("userOnline",user);
    }

    @PostMapping("/uploadHeadImage")
    public String uploadHeadImage(@RequestPart("head_image")MultipartFile headImage){
        UserDTO user = userService.getUserWithoutThatHost();
        String[] split = headImage.getOriginalFilename().split("\\.");
        String type = split[split.length - 1];
        UUID uuid = UUID.randomUUID();
        String perfixImage = uuid.toString().substring(0, 7);
        String fileName = perfixImage + "." + type;
        int status = userService.uploadImage(user.getUid(), Img.BASE_HEADIMAGE + fileName);
        try {
            headImage.transferTo(new File(System.getProperty("user.dir") + "\\src\\main\\resources\\static\\images\\headImage\\"+fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:/index";
    }


}
