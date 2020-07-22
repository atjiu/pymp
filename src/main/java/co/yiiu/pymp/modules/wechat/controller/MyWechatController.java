package co.yiiu.pymp.modules.wechat.controller;

import co.yiiu.pymp.modules.wechat.model.AutoReply;
import co.yiiu.pymp.modules.wechat.model.CommonSetting;
import co.yiiu.pymp.modules.wechat.model.User;
import co.yiiu.pymp.modules.wechat.model.enumeration.CommonSettingType;
import co.yiiu.pymp.modules.wechat.model.enumeration.UserStatus;
import co.yiiu.pymp.modules.wechat.repository.UserRepository;
import co.yiiu.pymp.modules.wechat.service.AutoReplyService;
import co.yiiu.pymp.modules.wechat.service.CommonSettingService;
import co.yiiu.pymp.modules.wechat.service.MessageService;
import co.yiiu.pymp.starter.controller.WechatController;
import co.yiiu.pymp.starter.model.Message;
import co.yiiu.pymp.starter.model.MessageEvent;
import co.yiiu.pymp.starter.model.MessageType;
import co.yiiu.pymp.starter.service.WechatService;
import co.yiiu.pymp.starter.util.XML2BeanUtil;
import it.sauronsoftware.jave.AudioUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

@Slf4j
@RequestMapping("/wechat")
@RestController
public class MyWechatController {

    @Autowired
    private WechatController wechatController;
    @Autowired
    private CommonSettingService commonSettingService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    AutoReplyService autoReplyService;
    @Autowired
    MessageService messageService;
    @Autowired
    WechatService wechatService;

    @GetMapping("/msg")
    public Object bind(String signature, String timestamp, String nonce, String echostr) {
        return wechatController.bind(signature, timestamp, nonce, echostr);
    }

    @PostMapping(value = "/msg", produces = "text/xml;charset=UTF-8")
    public Object msg(HttpServletRequest request) {
        return ResponseEntity.ok(wechatController.msg(request, message -> {
            // 存库
            co.yiiu.pymp.modules.wechat.model.Message _m = new co.yiiu.pymp.modules.wechat.model.Message();
            BeanUtils.copyProperties(message, _m);
            _m.setCreateTime(new Date(message.getCreateTime() * 1000));
            messageService.save(_m);

            if (message.getMsgType().equalsIgnoreCase(MessageType.text.name())) {
                Message msg = new Message();
                msg.setToUserName(message.getFromUserName());
                msg.setFromUserName(message.getToUserName());
                msg.setMsgType(MessageType.text.name());
                msg.setCreateTime(System.currentTimeMillis());
                // 处理自动回复
                // 先根据关键字全匹配查询
                AutoReply byKeywordAll = autoReplyService.findByKeywordAll(message.getContent());
                if (byKeywordAll != null) {
                    msg.setContent(byKeywordAll.getReplyContent());
                    return XML2BeanUtil.convertToXml(msg);
                } else {
                    // 全匹配没找到，查部分匹配
                    // TODO 这地方必须要优化。
                    AutoReply byKeywordInclude = autoReplyService.findByKeywordInclude(message.getContent());
                    if (byKeywordInclude != null) {
                        msg.setContent(byKeywordInclude.getReplyContent());
                        return XML2BeanUtil.convertToXml(msg);
                    }
                }
            } else if (message.getMsgType().equalsIgnoreCase(MessageType.voice.name())) {
                ResponseEntity<byte[]> media = wechatService.getMedia(message.getMediaId());
                File file = new File("./static/media/");
                if (!file.exists()) file.mkdirs();
                String audioPath = "./static/media/" + message.getMediaId();
                try (FileOutputStream fos = new FileOutputStream(new File("./static/media/" + message.getMediaId() + ".amr"))) {
                    if (media.getBody() != null) fos.write(media.getBody());
                    // convert amr to mp3
                    AudioUtils.amrToMp3(audioPath + ".amr", audioPath + ".mp3");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (message.getMsgType().equalsIgnoreCase(MessageType.location.name())) {

            } else if (message.getMsgType().equalsIgnoreCase(MessageType.video.name())) {
                ResponseEntity<byte[]> media = wechatService.getMedia(message.getMediaId());
                File file = new File("./static/media/");
                if (!file.exists()) file.mkdirs();
                try (FileOutputStream fos = new FileOutputStream(new File("./static/media/" + message.getMediaId() + ".mp4"))) {
                    if (media.getBody() != null) fos.write(media.getBody());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (message.getMsgType().equalsIgnoreCase(MessageType.shortvideo.name())) {
                ResponseEntity<byte[]> media = wechatService.getMedia(message.getMediaId());
                File file = new File("./static/media/");
                if (!file.exists()) file.mkdirs();
                try (FileOutputStream fos = new FileOutputStream(new File("./static/media/" + message.getMediaId() + ".mp4"))) {
                    if (media.getBody() != null) fos.write(media.getBody());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (message.getMsgType().equalsIgnoreCase(MessageType.image.name())) {

            } else if (message.getMsgType().equalsIgnoreCase(MessageType.link.name())) {

            } else if (message.getMsgType().equalsIgnoreCase(MessageType.event.name())) {
                if (message.getEvent().equalsIgnoreCase(MessageEvent.subscribe.name())) {
                    CommonSetting commonSetting = commonSettingService.findByType(CommonSettingType.SUB_REPLY.name());
                    if (commonSetting != null && !StringUtils.isEmpty(commonSetting.getContent())) {
                        Message msg = new Message();
                        msg.setToUserName(message.getFromUserName());
                        msg.setFromUserName(message.getToUserName());
                        msg.setMsgType(MessageType.text.name());
                        msg.setCreateTime(System.currentTimeMillis());
                        msg.setContent(commonSetting.getContent());
                        return XML2BeanUtil.convertToXml(msg);
                    }
                    //保存关注的用户
                    User user = new User();
                    user.setCreateTime(new Date(message.getCreateTime() * 1000));
                    user.setFromUserName(message.getFromUserName());
                    user.setStatus(UserStatus.subscribe.name());
                    userRepository.save(user);
                } else if (message.getEvent().equalsIgnoreCase(MessageEvent.unsubscribe.name())) {
                    log.info("有人取关了公众号, msg: {}", message.toString());
                } else if (message.getEvent().equalsIgnoreCase(MessageEvent.click.name())) {
                    Message msg = new Message();
                    msg.setToUserName(message.getFromUserName());
                    msg.setFromUserName(message.getToUserName());
                    msg.setMsgType(MessageType.text.name());
                    msg.setCreateTime(System.currentTimeMillis());
                    msg.setContent("这是一条从菜单触发click事件eventKey:" + message.getEventKey() + " 的消息");
                    return XML2BeanUtil.convertToXml(msg);
                }
            } else {
                log.warn("没有找到匹配的消息类型，msg: {}", message.toString());
            }
            return "success";
        }));
    }
}
