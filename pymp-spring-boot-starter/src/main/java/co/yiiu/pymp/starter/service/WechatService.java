package co.yiiu.pymp.starter.service;

import co.yiiu.pymp.starter.model.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface WechatService {
    //创建标签
    TagResponse createTag(String name);

    //获取标签列表
    TagResponse getTag();

    //更新标签
    WechatResponse updateTag(Long id, String name);

    //删除标签
    WechatResponse deleteTag(Long id);

    //获取关注的用户列表
    UserResponse getUser(String next_openid);

    //获取用户信息
    UserInfoResponse getUserInfo(String openid);

    //批量获取用户信息
    BatchUserResponse batchGetUserInfo(List<String> openids);

    /**
     * 获取素材
     *
     * @param mediaId
     * @return
     */
    ResponseEntity<byte[]> getMedia(String mediaId);

    // -----------------------客服相关的接口没法测，测试帐号没法测.详见下面链接--------------------------------
    //https://developers.weixin.qq.com/community/develop/doc/000c663439ce607174c835eeb5b000?_at=1558815645519

    WechatResponse kfAccountAdd(String kf_account, String nickname);

    WechatResponse kfAccountInviteWorker(String kf_account, String invite_wx);

    WechatResponse kfAccountUpdate(String kf_account, String nickname);

    WechatResponse kfAccountDelete(String kf_account);

    WechatResponse kfAccountUploadHeadImg(String kf_account, byte[] headimg);

    KFAccountResponse kfAccountList();
    // -----------------------客服相关的接口没法测，测试帐号没法测--------------------------------


    /**
     * 客服发消息
     * 话说这接口也太水了，明明是客服发消息，为啥没设置客服，都能发？
     *
     * @param openid  发送对象的openid
     * @param msgtype 消息类型
     * @param txt     消息内容
     */
    WechatResponse kfAccountSendMsg(String openid, String msgtype, String txt);

    WechatResponse kfAccountTyping(String openid);
}
