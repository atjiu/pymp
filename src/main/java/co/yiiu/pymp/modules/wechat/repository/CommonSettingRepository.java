package co.yiiu.pymp.modules.wechat.repository;

import co.yiiu.pymp.modules.wechat.model.CommonSetting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommonSettingRepository extends JpaRepository<CommonSetting, Integer> {

    List<CommonSetting> findByType(String type);
}
