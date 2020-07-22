package co.yiiu.pymp.modules.wechat.controller;

import co.yiiu.pymp.starter.model.WechatResponse;
import co.yiiu.pymp.starter.service.WechatService;
import co.yiiu.pymp.util.DefaultResponseEntityBody;
import co.yiiu.pymp.util.JSONUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/wechat/menu")
public class MenuController {

    @Autowired
    WechatService wechatService;

    @GetMapping("/config")
    public String config(Model model) throws JsonProcessingException {
        String s = wechatService.menuInfo();
        System.out.println(s);
        Map t = JSONUtil.parseObject(s, Map.class);
        ObjectMapper mapper = new ObjectMapper();
        //格式化/美化/优雅的输出
        String menuinfo = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(t);
        model.addAttribute("menuinfo", menuinfo);
        return "wechat/menu/config";
    }

    @GetMapping("/delete")
    @ResponseBody
    public Object delete() throws Exception {
        WechatResponse wechatResponse = wechatService.menuDelete();
        if (wechatResponse.getErrcode() == 0) {
            return ResponseEntity.ok(DefaultResponseEntityBody.NO_CONTENT);
        } else {
            throw new Exception(wechatResponse.getErrmsg());
        }
    }

    @PostMapping("/pub")
    @ResponseBody
    public Object pub(String menuJson) throws Exception {
        Map map = JSONUtil.parseObject(menuJson, Map.class);
        Map<String, Object> selfmenu_info = (Map) map.get("selfmenu_info");
        List<Map> list = (List) selfmenu_info.get("button");
        for (Map o : list) {
            if (o.containsKey("sub_button")) {
                List list1 = (List) ((Map) (o.get("sub_button"))).get("list");
                o.remove("sub_button");
                o.put("sub_button", list1);
            }
        }
        WechatResponse wechatResponse = wechatService.menuCreate(JSONUtil.toJson(selfmenu_info));
        if (wechatResponse.getErrcode() == 0) {
            return ResponseEntity.ok(DefaultResponseEntityBody.NO_CONTENT);
        } else {
            throw new Exception(wechatResponse.getErrmsg());
        }
    }
}
