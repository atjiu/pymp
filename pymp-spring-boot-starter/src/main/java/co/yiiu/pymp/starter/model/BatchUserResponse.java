package co.yiiu.pymp.starter.model;

import lombok.Data;

import java.util.List;

@Data
public class BatchUserResponse {

    List<UserInfoResponse> user_info_list;
}
