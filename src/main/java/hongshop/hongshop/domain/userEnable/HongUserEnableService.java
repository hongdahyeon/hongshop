package hongshop.hongshop.domain.userEnable;

import hongshop.hongshop.domain.userEnable.dto.HongUserEnableToDisableDTO;
import hongshop.hongshop.domain.userEnable.vo.HongUserEnableVO;

public interface HongUserEnableService {

    Long join(HongUserEnableToDisableDTO hongUserEnableToDisableDTO);

    HongUserEnableVO view(String userId);

    HongUserEnableVO getInfo(Long id);

    void updateDisableToEnable(Long id);
}