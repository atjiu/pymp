package co.yiiu.pymp.starter.service;

import co.yiiu.pymp.starter.model.*;
import co.yiiu.pymp.starter.util.Consts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

public class WechatServiceImpl implements WechatService {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private WechatUtil wechatUtil;

    @Override
    public TagResponse createTag(String name) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

        String postJson = "{\"tag\":{\"name\":\"%s\"}}";
        HttpEntity<String> entity = new HttpEntity<>(String.format(postJson, name), headers);

        return restTemplate.postForObject(String.format(Consts.CREATE_TAG, wechatUtil.getAccessToken(), name), entity, TagResponse.class);
    }

    @Override
    public TagResponse getTag() {
        return restTemplate.postForObject(String.format(Consts.GET_TAG, wechatUtil.getAccessToken()), new HttpEntity<>(null), TagResponse.class);
    }

    @Override
    public WechatResponse updateTag(Integer id, String name) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

        String postJson = "{\"tag\":{\"id\":%d,\"name\":\"%s\"}}";
        HttpEntity<String> entity = new HttpEntity<>(String.format(postJson, id, name), headers);

        return restTemplate.postForObject(String.format(Consts.UPDATE_TAG, wechatUtil.getAccessToken()), entity, WechatResponse.class);
    }

    @Override
    public WechatResponse deleteTag(Integer id) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

        String postJson = "{\"tag\":{\"id\":%d}}";
        HttpEntity<String> entity = new HttpEntity<>(String.format(postJson, id), headers);

        return restTemplate.postForObject(String.format(Consts.DELETE_TAG, wechatUtil.getAccessToken()), entity, WechatResponse.class);
    }

    @Override
    public WechatResponse batchTagging(List<String> openids, Integer tagId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

        String jsonPost = "{\n" +
                "    \"openid_list\": [%s],\n" +
                "    \"tagid\": %d\n" +
                "}";

        StringBuilder s = new StringBuilder();
        for (String openid : openids) {
            s.append("\"").append(openid).append("\",");
        }
        s = s.deleteCharAt(s.length() - 1);

        HttpEntity<String> entity = new HttpEntity<>(String.format(jsonPost, s.toString(), tagId), headers);
        return restTemplate.postForObject(String.format(Consts.BATCHTAGGING, wechatUtil.getAccessToken()), entity, WechatResponse.class);
    }

    @Override
    public WechatResponse batchUnTagging(List<String> openids, Integer tagId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

        String jsonPost = "{\n" +
                "    \"openid_list\": [%s],\n" +
                "    \"tagid\": %d\n" +
                "}";

        StringBuilder s = new StringBuilder();
        for (String openid : openids) {
            s.append("\"").append(openid).append("\",");
        }
        s = s.deleteCharAt(s.length() - 1);

        HttpEntity<String> entity = new HttpEntity<>(String.format(jsonPost, s.toString(), tagId), headers);
        return restTemplate.postForObject(String.format(Consts.BATCHUNTAGGING, wechatUtil.getAccessToken()), entity, WechatResponse.class);
    }

    @Override
    public TagIdListResponse getTagIdList(String openid) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

        String jsonPost = "{\n" +
                "    \"openid\": \"%s\"\n" +
                "} ";

        HttpEntity<String> entity = new HttpEntity<>(String.format(jsonPost, openid), headers);
        return restTemplate.postForObject(String.format(Consts.GETTAGIDLIST, wechatUtil.getAccessToken()), entity, TagIdListResponse.class);
    }

    @Override
    public UserResponse getUser(String next_openid) {
        return restTemplate.getForObject(String.format(Consts.GET_USERS, wechatUtil.getAccessToken(), next_openid), UserResponse.class);
    }

    @Override
    public UserInfoResponse getUserInfo(String openid) {
        return restTemplate.getForObject(String.format(Consts.GET_USER_INFO, wechatUtil.getAccessToken(), openid), UserInfoResponse.class);
    }

    @Override
    public WechatResponse updateRemark(String openid, String remark) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

        String jsonPost = "{\n" +
                "    \"openid\": \"%s\",\n" +
                "    \"remark\": \"%s\"\n" +
                "}";

        HttpEntity<String> entity = new HttpEntity<>(String.format(jsonPost, openid, remark), headers);
        return restTemplate.postForObject(String.format(Consts.USER_REMARK, wechatUtil.getAccessToken()), entity, WechatResponse.class);
    }

    @Override
    public BatchUserResponse batchGetUserInfo(List<String> openids) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

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

    @Override
    public WechatResponse kfAccountAdd(String kf_account, String nickname) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

        String postJson = "{\n" +
                "    \"kf_account\": \"%s\",\n" +
                "    \"nickname\": \"%s\"\n" +
                "}";

        HttpEntity<String> entity = new HttpEntity<>(String.format(postJson, kf_account, nickname), headers);

        return restTemplate.postForObject(String.format(Consts.KFACCOUNT_ADD, wechatUtil.getAccessToken()), entity, WechatResponse.class);
    }

    @Override
    public WechatResponse kfAccountInviteWorker(String kf_account, String invite_wx) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

        String postJson = "{\n" +
                "    \"kf_account\": \"%s\",\n" +
                "    \"invite_wx\": \"%s\",\n" +
                "}";

        HttpEntity<String> entity = new HttpEntity<>(String.format(postJson, kf_account, invite_wx), headers);

        return restTemplate.postForObject(String.format(Consts.KFACCOUNT_INVITEWORKER, wechatUtil.getAccessToken()), entity, WechatResponse.class);
    }

    @Override
    public WechatResponse kfAccountUpdate(String kf_account, String nickname) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

        String postJson = "{\n" +
                "    \"kf_account\": \"%s\",\n" +
                "    \"nickname\": \"%s\",\n" +
                "}";

        HttpEntity<String> entity = new HttpEntity<>(String.format(postJson, kf_account, nickname), headers);

        return restTemplate.postForObject(String.format(Consts.KFACCOUNT_UPDATE, wechatUtil.getAccessToken()), entity, WechatResponse.class);
    }

    @Override
    public WechatResponse kfAccountDelete(String kf_account) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

        String postJson = "{\n" +
                "    \"kf_account\": \"%s\"" +
                "}";

        HttpEntity<String> entity = new HttpEntity<>(String.format(postJson, kf_account), headers);

        return restTemplate.postForObject(String.format(Consts.KFACCOUNT_DELETE, wechatUtil.getAccessToken()), entity, WechatResponse.class);
    }

    @Override
    public WechatResponse kfAccountUploadHeadImg(String kf_account, byte[] headimg) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        String tmpfilename = UUID.randomUUID().toString();
        String tmpfilepath = System.getProperty("java.io.tmpdir") + "/" + tmpfilename + ".jpg";
        try (FileOutputStream fos = new FileOutputStream(new File(tmpfilepath))) {
            body.add("file", new FileSystemResource(tmpfilepath));

            HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(body, headers);

            return restTemplate.postForObject(String.format(Consts.KFACCOUNT_UPLOADHEADIMG, wechatUtil.getAccessToken(), kf_account), entity, WechatResponse.class);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public KFAccountResponse kfAccountList() {
        return restTemplate.getForObject(String.format(Consts.KFACCOUNT_LIST, wechatUtil.getAccessToken()), KFAccountResponse.class);
    }

    @Override
    public WechatResponse kfAccountSendMsg(String openid, String msgtype, String txt) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

        String postJson = "{\n" +
                "    \"touser\": \"%s\",\n" +
                "    \"msgtype\": \"%s\",\n" +
                "    \"text\": {\n" +
                "        \"content\": \"%s\"\n" +
                "    }\n" +
                "}";

        HttpEntity<String> entity = new HttpEntity<>(String.format(postJson, openid, msgtype, txt), headers);

        return restTemplate.postForObject(String.format(Consts.KFACCOUNT_SEND, wechatUtil.getAccessToken()), entity, WechatResponse.class);
    }

    @Override
    public String menuInfo() {
        try {
            String forObject = restTemplate.getForObject(String.format(Consts.MENU_GET_CURRENT_SELFMENU_INFO, wechatUtil.getAccessToken()), String.class);
            return forObject == null ? null : new String(forObject.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public WechatResponse menuCreate(String menuJson) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

        HttpEntity<String> entity = new HttpEntity<>(menuJson, headers);

        return restTemplate.postForObject(String.format(Consts.MENU_CREATE, wechatUtil.getAccessToken()), entity, WechatResponse.class);
    }

    @Override
    public WechatResponse menuDelete() {
        return restTemplate.getForObject(String.format(Consts.MENU_DELETE, wechatUtil.getAccessToken()), WechatResponse.class);
    }

    @Override
    public MenuAddConditionalResponse menuAddConditional(String menuJson) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

        HttpEntity<String> entity = new HttpEntity<>(menuJson, headers);

        return restTemplate.postForObject(String.format(Consts.MENU_ADDCONDITIONAL, wechatUtil.getAccessToken()), entity, MenuAddConditionalResponse.class);
    }

    @Override
    public WechatResponse menuDelConditional(String menuid) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

        String postJson = "{\"menuid\":\"%s\"}";
        HttpEntity<String> entity = new HttpEntity<>(String.format(postJson, menuid), headers);

        return restTemplate.postForObject(String.format(Consts.MENU_DELCONDITIONAL, wechatUtil.getAccessToken()), entity, WechatResponse.class);
    }

    @Override
    public String menuTryMatch(String userid) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

        String postJson = "{\"user_id\":\"%s\"}";
        HttpEntity<String> entity = new HttpEntity<>(String.format(postJson, userid), headers);

        return restTemplate.postForObject(String.format(Consts.MENU_DELCONDITIONAL, wechatUtil.getAccessToken()), entity, String.class);

    }
}
