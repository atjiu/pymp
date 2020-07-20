package co.yiiu.pymp.modules.wechat.controller;

import co.yiiu.pymp.modules.wechat.model.User;
import co.yiiu.pymp.modules.wechat.model.enumeration.UserStatus;
import co.yiiu.pymp.modules.wechat.service.UserService;
import co.yiiu.pymp.starter.model.BatchUserResponse;
import co.yiiu.pymp.starter.model.UserInfoResponse;
import co.yiiu.pymp.starter.model.WechatResponse;
import co.yiiu.pymp.starter.service.WechatService;
import co.yiiu.pymp.util.DefaultResponseEntityBody;
import co.yiiu.pymp.util.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/wechat/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private WechatService wechatService;

    @GetMapping("/list")
    public String list(@RequestParam(defaultValue = "1") Integer pageNo, Model model) {
        model.addAttribute("page", userService.findPage(pageNo));
        return "wechat/user/list";
    }

    @PostMapping("/remark")
    @ResponseBody
    public Object remark(String openid, String remark) throws Exception {
        WechatResponse wechatResponse = wechatService.updateRemark(openid, remark);
        if (wechatResponse.getErrcode() == 0) {
            return ResponseEntity.ok(DefaultResponseEntityBody.NO_CONTENT);
        } else {
            throw new Exception(wechatResponse.getErrmsg());
        }
    }

    @PostMapping("/tags")
    @ResponseBody
    public Object tags(@RequestParam("openids[]") List<String> openids, Integer tagId) throws Exception {
        WechatResponse wechatResponse = wechatService.batchTagging(openids, tagId);
        if (wechatResponse.getErrcode() == 0) {
            //添加标签成功的话，更新用户的个人信息
            for (String openid : openids) {
                User byFromUserName = userService.findByFromUserName(openid);
                String userinfo = byFromUserName.getUserInfo();
                if (!StringUtils.isEmpty(userinfo)) {
                    UserInfoResponse userInfoResponse = JSONUtil.parseObject(userinfo, UserInfoResponse.class);
                    if (userInfoResponse != null) {
                        if (userInfoResponse.getTagid_list() == null) userInfoResponse.setTagid_list(new ArrayList<>());
                        userInfoResponse.getTagid_list().add(tagId);
                        String s = JSONUtil.toJson(userInfoResponse);
                        byFromUserName.setUserInfo(s);
                        userService.save(byFromUserName);
                    }
                }
            }
            return ResponseEntity.ok(DefaultResponseEntityBody.NO_CONTENT);
        } else {
            throw new Exception(wechatResponse.getErrmsg());
        }
    }

    @PostMapping("/untags")
    @ResponseBody
    public Object untags(@RequestParam("openids[]") List<String> openids) throws Exception {
        //添加标签成功的话，更新用户的个人信息
        for (String openid : openids) {
            User byFromUserName = userService.findByFromUserName(openid);
            String userinfo = byFromUserName.getUserInfo();
            if (!StringUtils.isEmpty(userinfo)) {
                UserInfoResponse userInfoResponse = JSONUtil.parseObject(userinfo, UserInfoResponse.class);
                if (userInfoResponse != null) {
                    if (userInfoResponse.getTagid_list() == null) userInfoResponse.setTagid_list(new ArrayList<>());

                    Integer tagId = userInfoResponse.getTagid_list().get(0);
                    WechatResponse wechatResponse = wechatService.batchUnTagging(openids, tagId);
                    if (wechatResponse.getErrcode() == 0) {
                        userInfoResponse.getTagid_list().remove(tagId);
                        String s = JSONUtil.toJson(userInfoResponse);
                        byFromUserName.setUserInfo(s);
                        userService.save(byFromUserName);

                        return ResponseEntity.ok(DefaultResponseEntityBody.NO_CONTENT);
                    } else {
                        throw new Exception(wechatResponse.getErrmsg());
                    }
                }
            }
        }
        throw new Exception("至少要有一个openid");
    }

    @PostMapping("/updateremark")
    @ResponseBody
    public Object updateremark(String openid, String remark) throws Exception {
        WechatResponse wechatResponse = wechatService.updateRemark(openid, remark);
        if (wechatResponse.getErrcode() == 0) {
            User byFromUserName = userService.findByFromUserName(openid);
            String userinfo = byFromUserName.getUserInfo();
            if (!StringUtils.isEmpty(userinfo)) {
                UserInfoResponse userInfoResponse = JSONUtil.parseObject(userinfo, UserInfoResponse.class);
                if (userInfoResponse != null) {
                    userInfoResponse.setRemark(remark);
                    String s = JSONUtil.toJson(userInfoResponse);
                    byFromUserName.setUserInfo(s);
                    userService.save(byFromUserName);
                }
            }
            return ResponseEntity.ok(DefaultResponseEntityBody.NO_CONTENT);
        } else {
            throw new Exception(wechatResponse.getErrmsg());
        }
    }

    @PostMapping("/update")
    @ResponseBody
    public Object update(@RequestParam("openids[]") List<String> openids) {
        List<User> users = new ArrayList<>();
        if (openids.size() == 1) {
            UserInfoResponse userInfo = wechatService.getUserInfo(openids.get(0));
            User byFromUserName = userService.findByFromUserName(openids.get(0));
            if (userInfo.getSubscribe() == 0) {
                byFromUserName.setStatus(UserStatus.unsubscribe.name());
            } else {
                byFromUserName.setUserInfo(JSONUtil.toJson(userInfo));
                byFromUserName.setCreateTime(new Date(userInfo.getSubscribe_time() * 1000));
            }
            byFromUserName.setLastUpdateTime(new Date());
            userService.save(byFromUserName);

            users.add(byFromUserName);
        } else if (openids.size() > 1) {
            BatchUserResponse batchUserResponse = wechatService.batchGetUserInfo(openids);
            for (UserInfoResponse userInfoResponse : batchUserResponse.getUser_info_list()) {
                User byFromUserName = userService.findByFromUserName(userInfoResponse.getOpenid());
                if (userInfoResponse.getSubscribe() == 0) {
                    byFromUserName.setStatus(UserStatus.unsubscribe.name());
                } else {
                    byFromUserName.setUserInfo(JSONUtil.toJson(userInfoResponse));
                    byFromUserName.setCreateTime(new Date(userInfoResponse.getSubscribe_time() * 1000));
                }
                byFromUserName.setLastUpdateTime(new Date());
                userService.save(byFromUserName);

                users.add(byFromUserName);
            }
        }

        return ResponseEntity.ok(users);
    }
}
