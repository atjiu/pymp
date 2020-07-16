package co.yiiu.pymp.modules.wechat.controller;

import co.yiiu.pymp.starter.model.WechatResponse;
import co.yiiu.pymp.starter.service.WechatService;
import co.yiiu.pymp.util.DefaultResponseEntityBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/wechat/tag")
public class TagController {

    @Autowired
    private WechatService wechatService;

    @GetMapping("/list")
    public Object list(Model model) {
        model.addAttribute("tags", wechatService.getTag().getTags());
        return "wechat/tag/list";
    }


    @PostMapping("/update")
    @ResponseBody
    public Object update(Long id, String name) {
        WechatResponse wechatResponse = wechatService.updateTag(id, name);
        if (wechatResponse.getErrcode() == 0) {
            return ResponseEntity.ok(DefaultResponseEntityBody.OK);
        } else {
            return ResponseEntity.status(500).body(wechatResponse.getErrmsg());
        }
    }

    @PostMapping("/create")
    @ResponseBody
    public Object create(String name) {
        wechatService.createTag(name);
        return ResponseEntity.ok(DefaultResponseEntityBody.OK);
    }

    @PostMapping("/delete")
    @ResponseBody
    public Object delete(Long id) {
        WechatResponse wechatResponse = wechatService.deleteTag(id);
        if (wechatResponse.getErrcode() == 0) {
            return ResponseEntity.ok(DefaultResponseEntityBody.OK);
        } else {
            return ResponseEntity.status(500).body(wechatResponse.getErrmsg());
        }
    }
}
