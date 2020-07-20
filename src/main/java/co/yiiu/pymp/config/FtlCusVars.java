package co.yiiu.pymp.config;

import co.yiiu.pymp.config.model.SiteConfig;
import co.yiiu.pymp.modules.wechat.service.TagService;
import freemarker.template.TemplateModelException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * freemarker自定义变量
 */
@Configuration
public class FtlCusVars {

    @Autowired
    private freemarker.template.Configuration configuration;
    @Autowired
    SiteConfig siteConfig;
    @Autowired
    TagService tagService;

    @PostConstruct
    public void vars() throws TemplateModelException {
        configuration.setSharedVariable("_mapKey", siteConfig.getMapkey());
        configuration.setSharedVariable("_tags", tagService.getTags());
    }

}
