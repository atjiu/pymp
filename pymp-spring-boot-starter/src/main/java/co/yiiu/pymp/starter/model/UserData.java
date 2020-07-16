package co.yiiu.pymp.starter.model;

import lombok.Data;

import java.util.List;

@Data
public class UserData {

    List<String> openid;
    String next_openid;
}
