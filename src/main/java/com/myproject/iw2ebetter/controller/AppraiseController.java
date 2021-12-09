package com.myproject.iw2ebetter.controller;


import com.github.pagehelper.PageInfo;
import com.myproject.iw2ebetter.pojo.*;
import com.myproject.iw2ebetter.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Controller
@RequestMapping("/appraise")
public class AppraiseController {

    @Autowired
    private AppraiseService appraiseService;

    @Autowired
    private ImgService imgService;


    @Autowired
    private UserService userService;

    @Autowired
    private ResponseService responseService;

    @Autowired
    private AppraiseLikeService appraiseLikeService;

    @Autowired
    private RedisService redisService;

    @GetMapping("/disableAppraiseList")
    public String getDisableAppraiseList() {
        return "appraise/disableAppraises";
    }

    @GetMapping("/ableAppraiseList")
    public String getAppraiseList() {
        return "appraise/ableAppraises";
    }

    @GetMapping("/myAppraiseList")
    public String toMyAppraises() {
        return "appraise/myAppraises";
    }

    @ResponseBody
    @GetMapping("/ajaxDisableAppraises")
    public ResponseData ajaxDisableAppraises(@RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo, @RequestParam(value = "pageSize", defaultValue = "4") Integer pageSize) {
        PageInfo<Appraise> appraises = appraiseService.getAllAppraisesByEnable(pageNo, pageSize);
        return ResponseData.success("查询成功").addData("pageInfo", appraises);
    }

    @ResponseBody
    @GetMapping("/ajaxAbleAppraises")
    public ResponseData ajaxAppraises(@RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo, @RequestParam(value = "pageSize", defaultValue = "4") Integer pageSize) {
        PageInfo<Appraise> appraises = appraiseService.getAllAppraises(pageNo, pageSize);
        return ResponseData.success("查询成功").addData("pageInfo", appraises);
    }

    @ResponseBody
    @GetMapping("/getAppraiseByAid")
    public ResponseData getAppraiseByAid(@RequestParam("aid") Integer aid) {
        Appraise appraise = appraiseService.getAppraiseByAid(aid);
        List<Img> imgs = imgService.getImgsByAid(aid);
        List<Response> responses = responseService.getAllResponsesByAid(aid);
        appraise.setImgs(imgs);
        return ResponseData.success("查询成功")
                .addData("appraise", appraise)
                .addData("responses", responses);
    }

    @ResponseBody
    @GetMapping("/acceptAppraise")
    public ResponseData acceptAppraise(@RequestParam("aid") Integer aid) {
        int status = appraiseService.acceptAppraise(aid);
        if (status != 0) {
            return ResponseData.success("发布成功");
        } else {
            return ResponseData.fail("发布异常");
        }
    }

    @ResponseBody
    @GetMapping("/myAppraises")
    public ResponseData myAppraises(@RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo, @RequestParam(value = "pageSize", defaultValue = "4") Integer pageSize) {
        UserDTO user = userService.getUserWithoutThatHost();
        PageInfo<Appraise> pageInfo = appraiseService.getAllAppraisesByUid(pageNo, pageSize, user.getUid());
        return ResponseData.success("查询成功").addData("pageInfo", pageInfo);
    }

    @ResponseBody
    @GetMapping("/deleteAppraiseByAid")
    public ResponseData deleteAppraiseByAid(@RequestParam("aid") Integer aid) {
        int status = appraiseService.deleteAppraiseByAid(aid);
        if (status != 0) {
            return ResponseData.success("删除成功");
        } else {
            return ResponseData.success("删除失败，请重试！");
        }
    }

    @PostMapping("/addAppraise")
    public String addAppraise(@RequestParam("title") String title,
                              @RequestParam("appraise_content") String appraise_content,
                              @RequestPart("photos") MultipartFile[] photos) {
        Appraise appraise = new Appraise();
        appraise.setCreateTime(new Date());
        appraise.setTitle(title);
        appraise.setContent(appraise_content);
        appraise.setUser(userService.getUserWithoutThatHost());
        int status = appraiseService.addAppraise(appraise);
        int aid = appraise.getAid();
        List<Img> imgs = new ArrayList<>();
        for (int i = 0; i < photos.length; i++) {
            MultipartFile photo = photos[i];
            String[] split = photo.getOriginalFilename().split("\\.");
            String type = split[split.length - 1];
            try {
                photo.transferTo(new File(System.getProperty("user.dir") + "\\src\\main\\resources\\static\\images\\appraise\\" + aid + i + "." + type));
            } catch (IOException e) {
                e.printStackTrace();
            }

            Img img = new Img();
            img.setAid(aid);
            img.setSrc(Img.BASE_APPRAISE + aid + i + "." + type);
            imgs.add(img);
        }

        imgService.addImgs(imgs);
        return "redirect:/appraise/myAppraiseList";
    }

    @ResponseBody
    @GetMapping("/allAppraises")
    public ResponseData allAppraises(@RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo, @RequestParam(value = "pageSize", defaultValue = "4") Integer pageSize) {
        PageInfo<Appraise> pageInfo = appraiseService.getAllAppraises(pageNo, pageSize);
        List<Appraise> appraises = pageInfo.getList();
        List<Integer> likeCounts = new ArrayList<>();
        //保存点评是否被当前用户点过赞的状态
        List<Integer> status = new ArrayList<>();
        UserDTO host = userService.getUserWithoutThatHost();
        Integer uid = host.getUid();
        for (Appraise appraise : appraises) {
            List<Response> responses = responseService.getAllResponsesByAid(appraise.getAid());
            appraise.setResponses(responses.size());
        }

        for (int i = 0; i < appraises.size(); i++) {
            //先从redis中查 如果有，就对应添加返回前端
            //否则走数据库 并且添加到 redis
            Integer aid = appraises.get(i).getAid();
            LikeCountDTO lcdto = redisService.getLikeCountDTOByAid(aid);
            if (lcdto == null) {
                lcdto = new LikeCountDTO();
                lcdto.setAid(aid);
                //redis中没有 从数据库拿取 并添加至redis中
                List<AppraiseLike> likes = appraiseLikeService.getLikedListByAid(aid);
                if (likes == null) {
                    //数据库中也没有 这是一个没有被点过赞的点评
                    redisService.saveLikeCount2Redis(aid, 0);
                    lcdto.setCount(0);
                } else {
                    redisService.saveLikeCount2Redis(aid, likes.size());
                    lcdto.setCount(likes.size());
                }
            }
            likeCounts.add(lcdto.getCount());

            AppraiseLike appraiseLike = redisService.getAppraiseLikeByAidAndUid(aid, uid);
            if (appraiseLike == null) {
                //如果数据库中无记录 或者 记录标志 未点赞
                if ((appraiseLike = appraiseLikeService.getByAidAndUid(aid, uid)) == null ||
                        appraiseLike.getStatus() == 0) {
                    redisService.unlikeFromRedis(aid, uid);
                    appraiseLike = new AppraiseLike(aid,uid,AppraiseLike.UNLIKE);
                } else {
                    //存在
                    redisService.saveLiked2Redis(aid, uid);
                    appraiseLike = new AppraiseLike(aid,uid,AppraiseLike.LIKE);
                }
            }
            status.add(appraiseLike.getStatus());
        }
        pageInfo.setList(appraises);
        return ResponseData.success("查询成功").addData("pageInfo", pageInfo)
                .addData("likeCounts",likeCounts).addData("status",status);
    }


}
