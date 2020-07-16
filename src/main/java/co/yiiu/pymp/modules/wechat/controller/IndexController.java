package co.yiiu.pymp.modules.wechat.controller;

import co.yiiu.pymp.modules.wechat.model.Message;
import co.yiiu.pymp.modules.wechat.service.MessageService;
import co.yiiu.pymp.starter.model.MessageType;
import co.yiiu.pymp.starter.service.WechatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@Controller
public class IndexController {

    @Autowired
    MessageService messageService;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    WechatService wechatService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping(value = "/loadImage")
    public void loadImage(Long id, HttpServletResponse response) {
        Message byId = messageService.findById(id);
        if (byId != null && byId.getMsgType().equals(MessageType.image.name())) {
            ResponseEntity<byte[]> exchange = restTemplate.exchange(byId.getPicUrl(), HttpMethod.GET, new HttpEntity<>(new HttpHeaders()), byte[].class);
            byte[] body = exchange.getBody();
            if (body != null) {
                try (OutputStream os = response.getOutputStream()) {
                    response.setContentType("image/jpeg");
                    os.write(body);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @GetMapping(value = "/loadMedia")
    public void loadMedia(Long id, HttpServletResponse response) {
        Message byId = messageService.findById(id);
        if (byId != null &&
                (byId.getMsgType().equals(MessageType.video.name())
                        || byId.getMsgType().equals(MessageType.shortvideo.name())
                        || byId.getMsgType().equals(MessageType.voice.name()))
        ) {
            ResponseEntity<byte[]> entity = wechatService.getMedia(byId.getMediaId());
            if (entity.getBody() != null) {
                try (OutputStream os = response.getOutputStream()) {
                    response.setContentType(entity.getHeaders().getContentType().toString());
                    os.write(entity.getBody());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
