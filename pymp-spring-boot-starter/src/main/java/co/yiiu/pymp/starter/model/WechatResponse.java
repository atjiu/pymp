package co.yiiu.pymp.starter.model;

import lombok.Data;

@Data
public class WechatResponse {

    private String errmsg;
    // 0表示成功
    private int errcode;
}
