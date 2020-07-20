package co.yiiu.pymp.starter.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class TagIdListResponse extends WechatResponse {

    private List<Integer> tagid_list;
}
