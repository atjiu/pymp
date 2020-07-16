package co.yiiu.pymp.modules.wechat.service;

import co.yiiu.pymp.config.model.SiteConfig;
import co.yiiu.pymp.modules.wechat.model.Message;
import co.yiiu.pymp.modules.wechat.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    @Autowired
    MessageRepository messageRepository;
    @Autowired
    SiteConfig siteConfig;

    public void save(Message message) {
        messageRepository.save(message);
    }

    public Page<Message> findPage(Integer pageNo) {
        Pageable pageable = PageRequest.of(pageNo - 1, siteConfig.getPageSize(), Sort.by(Sort.Order.desc("id")));
        return messageRepository.findAll(pageable);
    }

    public Message findById(Long id) {
        return messageRepository.findById(id).orElse(null);
    }
}
