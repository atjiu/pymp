package co.yiiu.pymp.modules.wechat.controller;

import co.yiiu.pymp.modules.wechat.service.TagService;
import co.yiiu.pymp.starter.model.Tag;
import co.yiiu.pymp.starter.model.TagResponse;
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

import java.util.List;

@Controller
@RequestMapping("/wechat/tag")
public class TagController {

    @Autowired
    private WechatService wechatService;
    @Autowired
    TagService tagService;

    @GetMapping("/list")
    public Object list(Model model) {
        List<Tag> tags = wechatService.getTag().getTags();
        tagService.setTags(tags);
        model.addAttribute("tags", tags);
        return "wechat/tag/list";
    }


    @PostMapping("/update")
    @ResponseBody
    public Object update(Integer id, String name) throws Exception {
        WechatResponse wechatResponse = wechatService.updateTag(id, name);
        if (wechatResponse.getErrcode() == 0) {
            tagService.update(id, name);
            return ResponseEntity.ok(DefaultResponseEntityBody.OK);
        } else {
            throw new Exception(wechatResponse.getErrmsg());
        }
    }

    @PostMapping("/create")
    @ResponseBody
    public Object create(String name) throws Exception {
        TagResponse tag = wechatService.createTag(name);
        if (tag.getTag() != null) {
            tagService.add(tag.getTag());
            return ResponseEntity.ok(DefaultResponseEntityBody.OK);
        } else {
            throw new Exception(tag.getErrmsg());
        }
    }

    @PostMapping("/delete")
    @ResponseBody
    public Object delete(Integer id) throws Exception {
        WechatResponse wechatResponse = wechatService.deleteTag(id);
        if (wechatResponse.getErrcode() == 0) {
            tagService.delete(id);
            return ResponseEntity.ok(DefaultResponseEntityBody.OK);
        } else {
            throw new Exception(wechatResponse.getErrmsg());
        }
    }
}
