package co.yiiu.pymp.modules.wechat.controller;

import co.yiiu.pymp.modules.wechat.model.AutoReply;
import co.yiiu.pymp.modules.wechat.model.Message;
import co.yiiu.pymp.modules.wechat.service.AutoReplyService;
import co.yiiu.pymp.modules.wechat.service.MessageService;
import co.yiiu.pymp.util.DefaultResponseEntityBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Validated
@Controller
@RequestMapping("/wechat/msg_setting")
public class MessageController {

    @Autowired
    AutoReplyService autoReplyService;
    @Autowired
    MessageService messageService;

    @GetMapping("/auto_reply")
    public String auto_reply(@RequestParam(defaultValue = "1") Integer pageNo, Model model) {
        Page<AutoReply> page = autoReplyService.findPage(pageNo);
        model.addAttribute("page", page);
        return "wechat/msg/auto_reply";
    }

    @GetMapping("/auto_reply_add")
    public String auto_reply_add() {
        return "wechat/msg/auto_reply_add";
    }

    @PostMapping(value = "/auto_reply_save", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Object auto_reply_save(@NotEmpty(message = "匹配规则不能为空") String type,
                                  @NotEmpty(message = "关键字不能为空") String keyword,
                                  @NotEmpty(message = "回复内容不能为空") String replyContent) {
        AutoReply byKeywordAll = autoReplyService.findByKeywordAll(keyword);
        if (byKeywordAll != null) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("关键字已存在");
        } else {
            byKeywordAll = new AutoReply();
            byKeywordAll.setType(type);
            byKeywordAll.setKeyword(keyword);
            byKeywordAll.setInTime(new Date());
            byKeywordAll.setReplyContent(replyContent);
            autoReplyService.save(byKeywordAll);
            return ResponseEntity.ok(DefaultResponseEntityBody.NO_CONTENT);
        }
    }

    @GetMapping("/list")
    public String list(@RequestParam(defaultValue = "1") Integer pageNo, Model model) {
        Page<Message> page = messageService.findPage(pageNo);
        model.addAttribute("page", page);
        return "wechat/msg/list";
    }
}
