package co.yiiu.mp.pymp.modules.wechat.model;

import lombok.Data;
import lombok.ToString;

import javax.xml.bind.annotation.XmlRootElement;

@Data
@ToString
@XmlRootElement(name = "xml")
public class ResponseMessage {

    private Message xml;
}
