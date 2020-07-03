package co.yiiu.mp.pymp.modules.wechat.controller;

import co.yiiu.mp.pymp.config.model.SiteConfig;
import co.yiiu.mp.pymp.modules.wechat.model.Message;
import co.yiiu.mp.pymp.util.SignUtil;
import co.yiiu.mp.pymp.util.XML2BeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Slf4j
@RestController
@RequestMapping("/wechat")
public class WechatController {

    @Autowired
    private SiteConfig siteConfig;

    @GetMapping("/msg")
    public Object callback(String signature, String timestamp, String nonce, String echostr) {
        log.info("signature: {}, timestamp: {}, nonce: {}, echostr: {}", signature, timestamp, nonce, echostr);
        if (SignUtil.checkSignature(siteConfig.getWechat().getToken(), signature, timestamp, nonce)) {
            return ResponseEntity.ok(echostr);
        } else {
            return ResponseEntity.noContent();
        }
    }

    @PostMapping("/msg")
    public Object msg(HttpServletRequest request) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        String line;
        StringBuilder sb = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        System.out.println(sb.toString());
        Message message = XML2BeanUtils.convertToJavaBean(sb.toString(), Message.class);
        System.out.println(message.toString());
        return ResponseEntity.ok();
    }
}
