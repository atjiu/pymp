package co.yiiu.pymp.starter;

import co.yiiu.pymp.starter.service.WechatUtil;
import co.yiiu.pymp.starter.util.Consts;
import it.sauronsoftware.jave.AudioUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

//@SpringBootTest
class PympApplicationTests {

    @Autowired
    RestTemplate restTemplate;
    @Autowired
    WechatUtil wechatUtil;

    @Test
    void contextLoads() throws IOException {
        String mediaId = "HMPALw1iwfp2dJtCAnxNK0UzvT3CzYivNrd_vDNPIRjABG7hCnpM5TMSbk3u-P8V";
        ResponseEntity<byte[]> exchange = restTemplate.exchange(String.format(Consts.GET_MEDIA, wechatUtil.getAccessToken(), mediaId), HttpMethod.GET, new HttpEntity<>(new HttpHeaders()), byte[].class);
//        FileOutputStream fos = new FileOutputStream(new File("D:\\dev\\test\\pymp\\static\\a.mp4"));
//        fos.write(exchange.getBody());
//        fos.flush();
//        fos.close();
        MediaType contentType = exchange.getHeaders().getContentType();
        System.out.println(contentType);
    }

    @Test
    void test() {
        System.setProperty("ffmpeg.home", "D:\\dev\\test\\pymp\\lib\\ffmpeg");
        AudioUtils.amrToMp3("D:\\dev\\test\\pymp\\static\\media\\vclDN3lYrrc1OrTBsY5XxqK5luCxugvyfYcyzhvZ-uc_iI00QVi7NlgYv6vYypuX.amr",
                "D:\\dev\\test\\pymp\\static\\media\\vclDN3lYrrc1OrTBsY5XxqK5luCxugvyfYcyzhvZ-uc_iI00QVi7NlgYv6vYypuX.mp3");
    }

}
