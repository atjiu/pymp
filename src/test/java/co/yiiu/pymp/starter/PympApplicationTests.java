package co.yiiu.pymp.starter;

import co.yiiu.pymp.starter.model.KFAccount;
import co.yiiu.pymp.starter.model.KFAccountResponse;
import co.yiiu.pymp.starter.service.WechatService;
import co.yiiu.pymp.starter.service.WechatUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@SpringBootTest
class PympApplicationTests {

    @Autowired
    RestTemplate restTemplate;
    @Autowired
    WechatUtil wechatUtil;
    @Autowired
    WechatService wechatService;

    @Test
    void contextLoads() throws IOException {
        KFAccountResponse kfAccountResponse = wechatService.kfAccountList();
        if (!CollectionUtils.isEmpty(kfAccountResponse.getKf_list())) {
            for (KFAccount kfAccount : kfAccountResponse.getKf_list()) {
                System.out.println(kfAccount.toString());
            }
        }
//        wechatService.kfAccountAdd("kf1@qq.com", "客服1号");
    }

    @Test
    void test() {
    }

}
