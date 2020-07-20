package co.yiiu.pymp.starter.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserResponse extends WechatResponse {

    private int total;
    private int count;
    private UserData data;
}
