package co.yiiu.pymp.starter.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class TagResponse extends WechatResponse {

    private Tag tag;
    private List<Tag> tags;

}
