package co.yiiu.pymp.starter.service;

import co.yiiu.pymp.starter.model.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface WechatService {

    TagResponse createTag(String name);

    TagResponse getTag();

    WechatResponse updateTag(Long id, String name);

    WechatResponse deleteTag(Long id);

    UserResponse getUser(String next_openid);

    UserInfoResponse getUserInfo(String openid);

    BatchUserResponse batchGetUserInfo(List<String> openids);

    ResponseEntity<byte[]> getMedia(String mediaId);

}
