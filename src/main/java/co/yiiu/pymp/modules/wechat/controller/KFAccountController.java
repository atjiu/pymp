package co.yiiu.pymp.modules.wechat.controller;

import co.yiiu.pymp.starter.model.KFAccountResponse;
import co.yiiu.pymp.starter.model.WechatResponse;
import co.yiiu.pymp.starter.service.WechatService;
import co.yiiu.pymp.util.DefaultResponseEntityBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.constraints.NotEmpty;
import java.util.Collections;

@Controller
@Validated
@RequestMapping("/wechat/kfaccount")
public class KFAccountController {

    @Autowired
    WechatService wechatService;

    @GetMapping("/list")
    public String list(Model model) {
        KFAccountResponse kfAccountResponse = wechatService.kfAccountList();
        model.addAttribute("list", !CollectionUtils.isEmpty(kfAccountResponse.getKf_list())
                ? kfAccountResponse.getKf_list() : Collections.emptyList());
        return "wechat/kfaccount/list";
    }

    @GetMapping("/add")
    public String toAdd() {
        return "wechat/kfaccount/kfaccount_add";
    }

    @PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Object save(@NotEmpty(message = "客服名不能为空") String kf_account,
                       @NotEmpty(message = "客服昵称不能为空") String kf_nick) {
        WechatResponse wechatResponse = wechatService.kfAccountAdd(kf_account, kf_nick);
        if (wechatResponse.getErrcode() == 0) {
            return ResponseEntity.ok(DefaultResponseEntityBody.NO_CONTENT);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(wechatResponse.getErrmsg());
        }
    }

    @GetMapping("/update")
    public String toUpdate(String kf_account, String nickname, Model model) {
        model.addAttribute("kf_account", kf_account);
        model.addAttribute("nickname", nickname);
        return "wechat/kfaccount/kfaccount_update";
    }

    @PostMapping(value = "/update", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Object update(@NotEmpty(message = "客服名不能为空") String kf_account,
                         @NotEmpty(message = "客服昵称不能为空") String kf_nick) {
        WechatResponse wechatResponse = wechatService.kfAccountUpdate(kf_account, kf_nick);
        if (wechatResponse.getErrcode() == 0) {
            return ResponseEntity.ok(DefaultResponseEntityBody.NO_CONTENT);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(wechatResponse.getErrmsg());
        }
    }

    @GetMapping(value = "/delete", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Object delete(@NotEmpty(message = "客服名不能为空") String kf_account) {
        WechatResponse wechatResponse = wechatService.kfAccountDelete(kf_account);
        if (wechatResponse.getErrcode() == 0) {
            return ResponseEntity.ok(DefaultResponseEntityBody.NO_CONTENT);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(wechatResponse.getErrmsg());
        }
    }

    @PostMapping(value = "/inviteworker", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Object inviteworker(@NotEmpty(message = "客服名不能为空") String kf_account, @NotEmpty(message = "微信名不能为空") String invite_wx) {
        WechatResponse wechatResponse = wechatService.kfAccountInviteWorker(kf_account, invite_wx);
        if (wechatResponse.getErrcode() == 0) {
            return ResponseEntity.ok(DefaultResponseEntityBody.NO_CONTENT);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(wechatResponse.getErrmsg());
        }
    }
}
