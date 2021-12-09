package com.myproject.iw2ebetter.controller;


import com.github.pagehelper.PageInfo;
import com.myproject.iw2ebetter.pojo.UserDTO;
import com.myproject.iw2ebetter.pojo.Appraise;
import com.myproject.iw2ebetter.pojo.Response;
import com.myproject.iw2ebetter.pojo.ResponseData;
import com.myproject.iw2ebetter.service.ResponseService;
import com.myproject.iw2ebetter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Controller
@RequestMapping("/response")
public class ResponseController {

    @Autowired
    private ResponseService responseService;

    @Autowired
    private UserService userService;



    @GetMapping("/disableResponseList")
    public String disableResponseList(){
        return "response/disableResponses";
    }

    @GetMapping("/ableResponseList")
    public String ableResponseList(){
        return "response/ableResponses";
    }

    @ResponseBody
    @GetMapping("/ajaxDisableResponses")
    public ResponseData ajaxDisableResponses(@RequestParam(value="pageNo",defaultValue = "1") Integer pageNo, @RequestParam(value = "pageSize",defaultValue = "4")Integer pageSize){
        PageInfo<Response> responses = responseService.getAllResponsesByEnable(pageNo, pageSize);
        return ResponseData.success("查询成功").addData("pageInfo",responses);
    }

    @ResponseBody
    @GetMapping("/ajaxAbleResponses")
    public ResponseData ajaxAbleResponses(@RequestParam(value="pageNo",defaultValue = "1") Integer pageNo, @RequestParam(value = "pageSize",defaultValue = "4")Integer pageSize){
        PageInfo<Response> responses = responseService.getAllResponses(pageNo,pageSize);
        return ResponseData.success("查询成功").addData("pageInfo",responses);
    }

    @ResponseBody
    @GetMapping("/getResponseByReid")
    public ResponseData getResponseByReid(@RequestParam("reid")Integer reid){
        Response response = responseService.getResponseByReid(reid);
        return ResponseData.success("查询成功").addData("response",response);
    }

    @ResponseBody
    @GetMapping("/acceptResponse")
    public ResponseData acceptResponse(@RequestParam("reid") Integer reid){
        int status = responseService.acceptResponse(reid);
        if(status != 0){
            return ResponseData.success("审核成功");
        }else{
            return ResponseData.fail("审核失败,请重试！");
        }
    }

    @GetMapping("/myResponseList")
    public String myResponseList(){
        return "response/myResponses";
    }

    @ResponseBody
    @GetMapping("/myResponses")
    public ResponseData myResponses(@RequestParam(value="pageNo",defaultValue = "1") Integer pageNo, @RequestParam(value = "pageSize",defaultValue = "4")Integer pageSize){
        UserDTO user = userService.getUserWithoutThatHost();
        PageInfo<Response> pageInfo = responseService.getAllResponsesByUid(pageNo, pageSize, user.getUid());
        return ResponseData.success("查询成功").addData("pageInfo",pageInfo);
    }

    @ResponseBody
    @GetMapping("/deleteResponseByReid")
    public ResponseData deleteResponseByReid(@RequestParam("reid")Integer reid){
        int status = responseService.deleteResponseByReid(reid);
        if(status != 0){
            return ResponseData.success("删除成功");
        }else{
            return ResponseData.fail("删除失败,请重试！");
        }
    }

    @ResponseBody
    @PostMapping("/addResponse")
    public ResponseData addResponse(@RequestParam("responseContent") String content,@RequestParam("aid")Integer aid){
        Response response = new Response();
        Appraise appraise = new Appraise();
        appraise.setAid(aid);
        response.setResponseTime(new Date());
        response.setAppraise(appraise);
        response.setContent(content);
        response.setUser(userService.getUserWithoutThatHost());
        int status = responseService.addResponse(response);
        if(status != 0){
            return ResponseData.success("回复成功，审核通过后将会展示");
        }else{
            return ResponseData.success("回复失败，请重试");
        }
    }


}
