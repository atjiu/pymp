package co.yiiu.pymp.starter.service;

import co.yiiu.pymp.starter.model.AccessToken;
import co.yiiu.pymp.starter.util.Consts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@Slf4j
public class WechatUtil {

    private String appId;
    private String appSecret;

    private ThreadLocal<AccessToken> threadLocal = new ThreadLocal<>();

    public WechatUtil(String appId, String appSecret) {
        this.appId = appId;
        this.appSecret = appSecret;
    }

    public String getAccessToken() {
        if (threadLocal.get() == null || threadLocal.get().getInTime() == null) {
            fetchToken();
        } else {
            // 过期
            if (threadLocal.get().getInTime().getTime() + threadLocal.get().getExpires_in() * 1000 < System.currentTimeMillis()) {
                threadLocal.remove();
                fetchToken();
            }
        }
        log.info("当前的access_token: {}", threadLocal.get().getAccess_token());
        return threadLocal.get().getAccess_token();
    }

    private void fetchToken() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<AccessToken> forEntity = restTemplate.getForEntity(String.format(Consts.URL_ACCESS_TOKEN, appId, appSecret), AccessToken.class);
        if (forEntity.getStatusCode() == HttpStatus.OK) {
            AccessToken accessToken = forEntity.getBody();
            if (accessToken != null) {
                if (accessToken.getAccess_token() != null) {
                    threadLocal.set(accessToken);
                } else {
                    log.error("获取access_token失败，error_code: {}, error_msg: {}", accessToken.getErrcode(), accessToken.getErrmsg());
                }
            }
        }
    }
}
