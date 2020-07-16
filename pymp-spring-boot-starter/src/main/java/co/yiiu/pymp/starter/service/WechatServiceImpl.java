package co.yiiu.pymp.starter.service;

import co.yiiu.pymp.starter.model.*;
import co.yiiu.pymp.starter.util.Consts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class WechatServiceImpl implements WechatService {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private WechatUtil wechatUtil;

    @Override
    public TagResponse createTag(String name) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String postJson = "{\"tag\":{\"name\":\"%s\"}}";
        HttpEntity<String> entity = new HttpEntity<>(String.format(postJson, name), headers);

        return restTemplate.postForObject(String.format(Consts.CREATE_TAG, wechatUtil.getAccessToken(), name), entity, TagResponse.class);
    }

    @Override
    public TagResponse getTag() {
        return restTemplate.postForObject(String.format(Consts.GET_TAG, wechatUtil.getAccessToken()), new HttpEntity<>(null), TagResponse.class);
    }

    @Override
    public WechatResponse updateTag(Long id, String name) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String postJson = "{\"tag\":{\"id\":%d,\"name\":\"%s\"}}";
        HttpEntity<String> entity = new HttpEntity<>(String.format(postJson, id, name), headers);

        return restTemplate.postForObject(String.format(Consts.UPDATE_TAG, wechatUtil.getAccessToken()), entity, WechatResponse.class);
    }

    @Override
    public WechatResponse deleteTag(Long id) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String postJson = "{\"tag\":{\"id\":%d}}";
        HttpEntity<String> entity = new HttpEntity<>(String.format(postJson, id), headers);

        return restTemplate.postForObject(String.format(Consts.DELETE_TAG, wechatUtil.getAccessToken()), entity, WechatResponse.class);
    }

    @Override
    public UserResponse getUser(String next_openid) {
        ResponseEntity<UserResponse> forEntity = restTemplate.getForEntity(String.format(Consts.GET_USERS, wechatUtil.getAccessToken(), next_openid), UserResponse.class);

        return forEntity.getBody();
    }

    @Override
    public UserInfoResponse getUserInfo(String openid) {
        ResponseEntity<UserInfoResponse> forEntity = restTemplate.getForEntity(String.format(Consts.GET_USER_INFO, wechatUtil.getAccessToken(), openid), UserInfoResponse.class);

        return forEntity.getBody();
    }

    @Override
    public BatchUserResponse batchGetUserInfo(List<String> openids) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        StringBuilder sb = new StringBuilder();
        for (String openid : openids) {
            sb.append("{\"openid\":\"").append(openid).append("\",\"lang\":\"zh_CN\"}").append(",");
        }

        if (sb.length() > 0) sb.deleteCharAt(sb.length() - 1);
        String postJson = "{\"user_list\":[" + sb.toString() + "]}";

        HttpEntity<String> entity = new HttpEntity<>(postJson, headers);

        return restTemplate.postForObject(String.format(Consts.BATCH_GET_USER_INFO, wechatUtil.getAccessToken()), entity, BatchUserResponse.class);
    }

    @Override
    public ResponseEntity<byte[]> getMedia(String mediaId) {
        return restTemplate.exchange(String.format(Consts.GET_MEDIA, wechatUtil.getAccessToken(), mediaId), HttpMethod.GET, new HttpEntity<>(new HttpHeaders()), byte[].class);
    }
}
