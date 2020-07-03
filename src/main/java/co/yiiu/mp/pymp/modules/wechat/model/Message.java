package co.yiiu.mp.pymp.modules.wechat.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Message {

    private String ToUserName;
    private String FromUserName;
    private String CreateTime;
    private String MsgType;
    private String Content;
    private String MsgId;

}
