package co.yiiu.pymp.starter.model;

import lombok.Data;

import java.util.List;

@Data
public class UserInfoResponse {

    //用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息
    private Integer subscribe;
    private String openid;
    private String nickname;
    //用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
    private Integer sex;
    private String language;
    private String city;
    private String province;
    private String country;
    private String headimgurl;
    private Long subscribe_time;
    private String unionid;
    private String remark;
    //用户所在的分组ID（兼容旧的用户分组接口）
    private Integer groupid;

    private List<Integer> tagid_list;

    //返回用户关注的渠道来源，
    // ADD_SCENE_SEARCH 公众号搜索，
    // ADD_SCENE_ACCOUNT_MIGRATION 公众号迁移，
    // ADD_SCENE_PROFILE_CARD 名片分享，
    // ADD_SCENE_QR_CODE 扫描二维码，
    // ADD_SCENE_PROFILE_LINK 图文页内名称点击，
    // ADD_SCENE_PROFILE_ITEM 图文页右上角菜单，
    // ADD_SCENE_PAID 支付后关注，
    // ADD_SCENE_WECHAT_ADVERTISEMENT 微信广告，
    // ADD_SCENE_OTHERS 其他
    private String subscribe_scene;
    private Integer qr_scene;
    private String qr_scene_str;
}
