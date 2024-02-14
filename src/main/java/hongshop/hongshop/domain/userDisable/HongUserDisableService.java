package hongshop.hongshop.domain.userDisable;

import hongshop.hongshop.domain.userDisable.dto.HongUserEnableToDisableDTO;
import hongshop.hongshop.domain.userDisable.vo.HongUserDisableVO;

public interface HongUserDisableService {

    Long join(HongUserEnableToDisableDTO hongUserEnableToDisableDTO);

    HongUserDisableVO view(String userId);

    HongUserDisableVO getInfo(Long id);

    void updateDisableToEnable(Long id);
}