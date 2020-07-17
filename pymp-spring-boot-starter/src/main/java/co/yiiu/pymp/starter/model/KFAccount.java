package co.yiiu.pymp.starter.model;

import lombok.Data;

@Data
public class KFAccount {

    String kf_account;
    String kf_nick;
    String kf_wx;
    String kf_id;
    String kf_headimgurl;
    Long invite_expire_time;
    String invite_status;

}
