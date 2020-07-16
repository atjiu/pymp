package co.yiiu.pymp.modules.wechat.service;

import co.yiiu.pymp.config.model.SiteConfig;
import co.yiiu.pymp.modules.wechat.model.User;
import co.yiiu.pymp.modules.wechat.repository.UserRepository;
import co.yiiu.pymp.starter.service.WechatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SiteConfig siteConfig;
    @Autowired
    private WechatService wechatService;

    public User findByFromUserName(String fromUserName) {
        return userRepository.findByFromUserName(fromUserName);
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public Page<User> findPage(Integer pageNo) {
        Pageable page = PageRequest.of(pageNo - 1, siteConfig.getPageSize(), Sort.by(Sort.Order.desc("id")));
        return userRepository.findAll(page);
    }

    public void saveAll(List<User> users) {
        userRepository.saveAll(users);
    }

}
