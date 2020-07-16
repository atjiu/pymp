package co.yiiu.pymp.config;

import co.yiiu.pymp.config.model.SiteConfig;
import co.yiiu.pymp.modules.wechat.model.CommonSetting;
import co.yiiu.pymp.modules.wechat.model.User;
import co.yiiu.pymp.modules.wechat.model.enumeration.CommonSettingType;
import co.yiiu.pymp.modules.wechat.model.enumeration.UserStatus;
import co.yiiu.pymp.modules.wechat.service.CommonSettingService;
import co.yiiu.pymp.modules.wechat.service.UserService;
import co.yiiu.pymp.starter.model.UserResponse;
import co.yiiu.pymp.starter.service.WechatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.ArrayList;
import java.util.List;

@Configuration
@DependsOn("wechatService")
public class ServerStartup implements ApplicationListener<ApplicationStartedEvent> {

    @Autowired
    private UserService userService;
    @Autowired
    private CommonSettingService commonSettingService;
    @Autowired
    private SiteConfig siteConfig;
    @Autowired
    private WechatService wechatService;

    private List<UserResponse> getUsers(List<UserResponse> userResponses, String next_openid) {
        UserResponse user = wechatService.getUser(next_openid);
        userResponses.add(user);
        if (user.getCount() < user.getTotal()) {
            return getUsers(userResponses, user.getData().getNext_openid());
        } else {
            return userResponses;
        }
    }

    @Override
    public void onApplicationEvent(ApplicationStartedEvent applicationStartedEvent) {
        // 写ffmpeg.home到运行环境中
        System.setProperty("ffmpeg.home", siteConfig.getFfmpegHome());

        // 更新用户
        CommonSetting byType = commonSettingService.findByType(CommonSettingType.UPDATE_USER.name());
        if (byType == null || byType.getLastUpdateTime().getTime() + siteConfig.getUpdateIdle() < System.currentTimeMillis()) {
            List<UserResponse> userResponses = getUsers(new ArrayList<>(), "");
            List<User> users = new ArrayList<>();
            if (userResponses.size() > 0) {
                for (UserResponse userRespons : userResponses) {
                    for (String openid : userRespons.getData().getOpenid()) {
                        User user = userService.findByFromUserName(openid);
                        if (user == null) {
                            user = new User();
                            user.setFromUserName(openid);
                            user.setStatus(UserStatus.subscribe.name());
                            users.add(user);
                        }
                    }
                }
            }
            if (users.size() > 0) userService.saveAll(users);
        }
    }
}
