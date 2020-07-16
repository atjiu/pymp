package co.yiiu.pymp.starter.controller;

import co.yiiu.pymp.starter.model.Message;
import co.yiiu.pymp.starter.model.MessageCallback;
import co.yiiu.pymp.starter.util.SignUtil;
import co.yiiu.pymp.starter.util.XML2BeanUtil;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Slf4j
public class WechatController {

    private String token;

    public WechatController(String token) {
        this.token = token;
    }

    public String bind(String signature, String timestamp, String nonce, String echostr) {
        if (SignUtil.checkSignature(token, signature, timestamp, nonce)) {
            return echostr;
        } else {
            return "success";
        }
    }

    public String msg(HttpServletRequest request, MessageCallback callback) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            log.info(sb.toString());
            Message message = XML2BeanUtil.convertToJavaBean(sb.toString(), Message.class);
            return callback.process(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "success";
    }
}
