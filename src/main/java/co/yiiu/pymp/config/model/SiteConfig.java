package co.yiiu.pymp.config.model;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Data
@Configuration
@PropertySource(value = {"classpath:pymp.properties"})
@ConfigurationProperties(prefix = "site")
public class SiteConfig {

    private Integer pageSize;
    private Integer updateIdle;
    private String ffmpegHome;
    private String mapkey;

}
