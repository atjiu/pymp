package co.yiiu.pymp.starter.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "pymp")
public class WechatProperties {

    private String appId;
    private String appSecret;
    private String token;

}
