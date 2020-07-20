package co.yiiu.pymp.starter.util;

public class Consts {

    public static final String URL_ACCESS_TOKEN = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";
    public static final String URL_USER_INFO = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=%s&openid=%s&lang=zh_CN";
    // 标签的CRUD
    public static final String CREATE_TAG = "https://api.weixin.qq.com/cgi-bin/tags/create?access_token=%s&name=%s";
    public static final String GET_TAG = "https://api.weixin.qq.com/cgi-bin/tags/get?access_token=%s";
    public static final String UPDATE_TAG = "https://api.weixin.qq.com/cgi-bin/tags/update?access_token=%s";
    public static final String DELETE_TAG = "https://api.weixin.qq.com/cgi-bin/tags/delete?access_token=%s";
    public static final String BATCHTAGGING = "https://api.weixin.qq.com/cgi-bin/tags/members/batchtagging?access_token=%s";
    public static final String BATCHUNTAGGING = "https://api.weixin.qq.com/cgi-bin/tags/members/batchuntagging?access_token=%s";
    public static final String GETTAGIDLIST = "https://api.weixin.qq.com/cgi-bin/tags/getidlist?access_token=%s";

    // 获取标签下的所有粉丝
    public static final String GET_TAG_USERS = "https://api.weixin.qq.com/cgi-bin/user/tag/get?access_token=%s";
    public static final String USER_REMARK = "https://api.weixin.qq.com/cgi-bin/user/info/updateremark?access_token=%s";

    // 用户
    public static final String GET_USERS = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=%s&next_openid=%s";
    public static final String GET_USER_INFO = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=%s&openid=%s&lang=zh_CN";
    public static final String BATCH_GET_USER_INFO = "https://api.weixin.qq.com/cgi-bin/user/info/batchget?access_token=%s";
    // 获取Media文件
    public static final String GET_MEDIA = "https://api.weixin.qq.com/cgi-bin/media/get?access_token=%s&media_id=%s";
    // 客服帐号
    public static final String KFACCOUNT_ADD = "https://api.weixin.qq.com/customservice/kfaccount/add?access_token=%s";
    public static final String KFACCOUNT_INVITEWORKER = "https://api.weixin.qq.com/customservice/kfaccount/inviteworker?access_token=%s";
    public static final String KFACCOUNT_UPDATE = "https://api.weixin.qq.com/customservice/kfaccount/update?access_token=%s";
    public static final String KFACCOUNT_DELETE = "https://api.weixin.qq.com/customservice/kfaccount/del?access_token=%s";
    public static final String KFACCOUNT_UPLOADHEADIMG = "https://api.weixin.qq.com/customservice/kfaccount/uploadheadimg?access_token=%s&kf_account=%s";
    public static final String KFACCOUNT_LIST = "https://api.weixin.qq.com/cgi-bin/customservice/getkflist?access_token=%s";
    public static final String KFACCOUNT_SEND = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=%s";
    public static final String KFACCOUNT_TYPING = "https://api.weixin.qq.com/cgi-bin/message/custom/typing?access_token=%s";

}
