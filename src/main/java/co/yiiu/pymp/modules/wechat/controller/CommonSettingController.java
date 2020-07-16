package co.yiiu.pymp.modules.wechat.controller;

import co.yiiu.pymp.modules.wechat.model.CommonSetting;
import co.yiiu.pymp.modules.wechat.model.enumeration.CommonSettingType;
import co.yiiu.pymp.modules.wechat.service.CommonSettingService;
import co.yiiu.pymp.starter.service.WechatService;
import co.yiiu.pymp.util.DefaultResponseEntityBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.constraints.NotEmpty;

@Validated
@Controller
@RequestMapping("/wechat/common/setting")
public class CommonSettingController {

    @Autowired
    private WechatService wechatService;

    @Autowired
    private CommonSettingService commonSettingService;

    @GetMapping("/sub_reply")
    public Object reply(Model model) {
        CommonSetting commonSetting = commonSettingService.findByType(CommonSettingType.SUB_REPLY.name());
        model.addAttribute("tags", wechatService.getTag().getTags());
        model.addAttribute("subReply", commonSetting == null ? new CommonSetting() : commonSetting);
        return "wechat/msg/sub_reply";
    }

    @PostMapping(value = "/sub_reply")
    @ResponseBody
    public Object saveReply(@NotEmpty(message = "关注回复内容不能为空") String content) {
        CommonSetting commonSetting = commonSettingService.findByType(CommonSettingType.SUB_REPLY.name());
        if (commonSetting == null) commonSetting = new CommonSetting();
        commonSetting.setType(CommonSettingType.SUB_REPLY.name());
        commonSetting.setContent(content);
        commonSettingService.save(commonSetting);
        return ResponseEntity.ok(DefaultResponseEntityBody.NO_CONTENT);
    }

}
