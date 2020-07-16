package co.yiiu.pymp.modules.wechat.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Data
@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ToUserName;
    private String FromUserName;
    private Date CreateTime;
    private String MsgType;
    private String Content;
    private String MsgId;
    private String Event;
    private String EventKey;
    private String PicUrl;
    private String MediaId;
    private String ThumbMediaId;
    private String Location_X;
    private String Location_Y;
    private String Scale;
    private String Label;
    private String Format;
    private String Recognition;
}
