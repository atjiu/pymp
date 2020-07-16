package co.yiiu.pymp.starter.config;

import co.yiiu.pymp.starter.controller.WechatController;
import co.yiiu.pymp.starter.service.WechatService;
import co.yiiu.pymp.starter.service.WechatServiceImpl;
import co.yiiu.pymp.starter.service.WechatUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableConfigurationProperties(WechatProperties.class)
public class WechatAutoConfiguration {

    @Bean
    @DependsOn("restTemplate")
    @ConditionalOnProperty(prefix = "pymp", name = {"app-id", "app-secret", "token"})
    public WechatService wechatService(WechatProperties wechatProperties) {
        return new WechatServiceImpl();
    }

    @Bean
    @DependsOn("restTemplate")
    @ConditionalOnProperty(prefix = "pymp", name = {"app-id", "app-secret", "token"})
    public WechatUtil wechatUtil(WechatProperties wechatProperties) {
        return new WechatUtil(wechatProperties.getAppId(), wechatProperties.getAppSecret());
    }

    @Bean
    @ConditionalOnProperty(prefix = "pymp", name = {"token"})
    public WechatController wechatController(WechatProperties wechatProperties) {
        return new WechatController(wechatProperties.getToken());
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
