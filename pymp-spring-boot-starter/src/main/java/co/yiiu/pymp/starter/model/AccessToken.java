package co.yiiu.pymp.starter.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class AccessToken implements Serializable {

    private static final long serialVersionUID = -4674128716619222599L;

    private String access_token;
    private String errmsg;
    private Integer errcode;
    private Integer expires_in;
    private Date inTime;
}
