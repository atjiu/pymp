package co.yiiu.pymp.config;

import co.yiiu.pymp.config.model.SiteConfig;
import freemarker.template.TemplateModelException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class FtlCusVars {

    @Autowired
    private freemarker.template.Configuration configuration;

    @Autowired
    SiteConfig siteConfig;

    @PostConstruct
    public void vars() throws TemplateModelException {
        configuration.setSharedVariable("_mapKey", siteConfig.getMapkey());
    }

}
