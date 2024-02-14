package hongshop.hongshop.domain.userDisable.impl;

import hongshop.hongshop.domain.user.HongUser;
import hongshop.hongshop.domain.user.HongUserService;
import hongshop.hongshop.domain.userDisable.HongUserDisable;
import hongshop.hongshop.domain.userDisable.HongUserDisableRepository;
import hongshop.hongshop.domain.userDisable.HongUserDisableService;
import hongshop.hongshop.domain.userDisable.dto.HongUserEnableToDisableDTO;
import hongshop.hongshop.domain.userDisable.vo.HongUserDisableVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* @fileName HongUserDisableServiceImpl
* @author dahyeon
* @version 1.0.0
* @date 2023-12-12
* @summary     (1) join : 사용자 계정 비활성화
 *             (2) view : userId를 통한 사용자 계정 비활성화 정보 가져오기 (삭제여부 N)
 *             (3) getInfo : user-id를 통한 사용자 계정 비활성화 정보 가져오기 (삭제여부 N)
 *             (4) updateDisableToEnable
 *                  - 1. 사용자 계정 비활성화 정보 삭제
 *                  - 2. 사용자 계정 비활성화 풀기 (disable -> enable)
**/

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HongUserDisableServiceImpl implements HongUserDisableService {

    private final HongUserService hongUserService;
    private final HongUserDisableRepository hongUserDisableRepository;

    @Override
    @Transactional(readOnly = false)
    public Long join(HongUserEnableToDisableDTO hongUserEnableToDisableDTO) {
        HongUser hongUser = hongUserService.getUserAndChangeEnable(hongUserEnableToDisableDTO.getUserId());            // get user entity and change enable to false
        HongUserDisable hongUserEnable = HongUserDisable.hongUserDisableInsert()
                                                        .hongUser(hongUser)
                                                        .disableMsg(hongUserEnableToDisableDTO.getDisableMsg())
                                                        .build();
        HongUserDisable save = hongUserDisableRepository.save(hongUserEnable);
        return save.getId();
    }

    @Override
    public HongUserDisableVO view(String userId) {
        HongUserDisable byHongUserId = hongUserDisableRepository.findByHongUserId(userId);
        return new HongUserDisableVO(byHongUserId);
    }

    @Override
    public HongUserDisableVO getInfo(Long id) {
        HongUserDisable hongUserEnable = hongUserDisableRepository.findByHongUserIdLong(id);
        return new HongUserDisableVO(hongUserEnable);
    }

    @Override
    @Transactional(readOnly = false)
    public void updateDisableToEnable(Long id) {
        HongUserDisable hongUserEnable = hongUserDisableRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("there is no info"));
        hongUserEnable.deleteUserDisable();
        hongUserService.changeDisableToEnable(hongUserEnable.getHongUser().getId());
    }
}