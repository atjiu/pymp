package co.yiiu.pymp.modules.wechat.service;

import co.yiiu.pymp.config.model.SiteConfig;
import co.yiiu.pymp.modules.wechat.model.AutoReply;
import co.yiiu.pymp.modules.wechat.model.enumeration.AutoReplyType;
import co.yiiu.pymp.modules.wechat.repository.AutoReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AutoReplyService {

    @Autowired
    private AutoReplyRepository autoReplyRepository;
    @Autowired
    private SiteConfig siteConfig;


    public void save(AutoReply autoReply) {
        autoReplyRepository.save(autoReply);
    }

    // 查询类型为 all
    public AutoReply findByKeywordAll(String keyword) {
        AutoReply autoReply = new AutoReply();
        autoReply.setKeyword(keyword);
        autoReply.setType(AutoReplyType.all.name());
        Example<AutoReply> example = Example.of(autoReply);
        List<AutoReply> all = autoReplyRepository.findAll(example);
        if (all.size() > 0) return all.get(0);
        return null;
    }

    // 查询类型为 include
    public AutoReply findByKeywordInclude(String keyword) {
        List<AutoReply> all = autoReplyRepository.findByKeywordLikeAndType("%" + keyword + "%", AutoReplyType.include.name());
        if (all.size() > 0) return all.get(0);
        return null;
    }

    public Page<AutoReply> findPage(Integer pageNo) {
        Pageable pageable = PageRequest.of(pageNo - 1, siteConfig.getPageSize(), Sort.by(Sort.Order.desc("id")));
        return autoReplyRepository.findAll(pageable);
    }
}
