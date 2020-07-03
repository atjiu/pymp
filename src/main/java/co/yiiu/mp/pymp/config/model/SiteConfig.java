package co.yiiu.mp.pymp.config.model;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Data
@Configuration
@PropertySource(value = {"classpath:pymp.properties"})
@ConfigurationProperties(prefix = "site")
public class SiteConfig {

    private WeChat wechat;

}
