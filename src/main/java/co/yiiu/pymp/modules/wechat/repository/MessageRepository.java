package co.yiiu.pymp.modules.wechat.repository;

import co.yiiu.pymp.modules.wechat.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
