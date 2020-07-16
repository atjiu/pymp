package co.yiiu.pymp.modules.wechat.repository;

import co.yiiu.pymp.modules.wechat.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByFromUserName(String fromUserName);
}
