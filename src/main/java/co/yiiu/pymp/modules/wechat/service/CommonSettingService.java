package co.yiiu.pymp.modules.wechat.service;

import co.yiiu.pymp.modules.wechat.model.CommonSetting;
import co.yiiu.pymp.modules.wechat.repository.CommonSettingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommonSettingService {

    @Autowired
    private CommonSettingRepository commonSettingRepository;

    public CommonSetting findByType(String type) {
        List<CommonSetting> byType = commonSettingRepository.findByType(type);
        if (byType.size() > 0) return byType.get(0);
        return null;
    }

    public void save(CommonSetting commonSetting) {
        commonSettingRepository.save(commonSetting);
    }
}
