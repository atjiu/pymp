package co.yiiu.pymp.modules.wechat.repository;

import co.yiiu.pymp.modules.wechat.model.AutoReply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AutoReplyRepository extends JpaRepository<AutoReply, Long> {

    List<AutoReply> findByKeywordLikeAndType(String keyword, String type);
}
