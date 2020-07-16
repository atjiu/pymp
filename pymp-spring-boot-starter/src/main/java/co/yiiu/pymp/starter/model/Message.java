package co.yiiu.pymp.starter.model;

import lombok.Data;
import lombok.ToString;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@Data
@ToString
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement(name = "ToUserName")
    private String ToUserName;
    @XmlElement(name = "FromUserName")
    private String FromUserName;
    @XmlElement(name = "CreateTime")
    private Long CreateTime;
    @XmlElement(name = "MsgType")
    private String MsgType;
    @XmlElement(name = "Content")
    private String Content;
    @XmlElement(name = "MsgId")
    private String MsgId;
    @XmlElement(name = "Event")
    private String Event;
    @XmlElement(name = "EventKey")
    private String EventKey;
    @XmlElement(name = "PicUrl")
    private String PicUrl;
    @XmlElement(name = "MediaId")
    private String MediaId;
    @XmlElement(name = "ThumbMediaId")
    private String ThumbMediaId;
    @XmlElement(name = "Location_X")
    private String Location_X;
    @XmlElement(name = "Location_Y")
    private String Location_Y;
    @XmlElement(name = "Scale")
    private String Scale;
    @XmlElement(name = "Label")
    private String Label;
    @XmlElement(name = "Format")
    private String Format;
    @XmlElement(name = "Recognition")
    private String Recognition;

}
