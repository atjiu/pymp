package co.yiiu.pymp.starter.model;

import lombok.Data;

@Data
public class UserResponse {

    private int total;
    private int count;
    private UserData data;
}
