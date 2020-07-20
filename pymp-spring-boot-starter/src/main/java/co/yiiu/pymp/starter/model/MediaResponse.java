package co.yiiu.pymp.starter.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class MediaResponse extends WechatResponse {

    String video_url;
}
