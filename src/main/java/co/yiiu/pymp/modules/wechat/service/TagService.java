package co.yiiu.pymp.modules.wechat.service;

import co.yiiu.pymp.starter.model.Tag;
import co.yiiu.pymp.starter.model.TagResponse;
import co.yiiu.pymp.starter.service.WechatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagService {

    private List<Tag> tags = new ArrayList<>();

    @Autowired
    WechatService wechatService;

    public List<Tag> getTags() {
        if (CollectionUtils.isEmpty(tags)) {
            TagResponse tag = wechatService.getTag();
            this.tags.addAll(tag.getTags());
        }
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public void add(Tag tag) {
        tags.add(tag);
    }

    public void delete(Integer id) {
        tags = tags.stream().filter(_tag -> !_tag.getId().equals(id)).collect(Collectors.toList());
    }

    public void update(Integer id, String name) {
        for (Tag tag1 : tags) {
            if (tag1.getId().equals(id)) {
//                tag1.setCount(count);
                tag1.setName(name);
            }
        }
    }
}
