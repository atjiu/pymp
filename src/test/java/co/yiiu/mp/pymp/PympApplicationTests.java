package co.yiiu.mp.pymp;

import co.yiiu.mp.pymp.modules.wechat.model.ResponseMessage;
import co.yiiu.mp.pymp.util.XML2BeanUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

//@SpringBootTest
class PympApplicationTests {

    @Test
    void contextLoads() throws IOException {
        String xml = new String(Files.readAllBytes(Paths.get("./aa.xml")));
        System.out.println(xml);
        ResponseMessage message = XML2BeanUtils.convertToJavaBean(xml, ResponseMessage.class);
        System.out.println(message);
    }

}
