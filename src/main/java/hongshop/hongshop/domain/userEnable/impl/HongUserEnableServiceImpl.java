package hongshop.hongshop.domain.userEnable.impl;

import hongshop.hongshop.domain.user.HongUser;
import hongshop.hongshop.domain.user.HongUserService;
import hongshop.hongshop.domain.userEnable.HongUserEnable;
import hongshop.hongshop.domain.userEnable.HongUserEnableRepository;
import hongshop.hongshop.domain.userEnable.HongUserEnableService;
import hongshop.hongshop.domain.userEnable.dto.HongUserEnableToDisableDTO;
import hongshop.hongshop.domain.userEnable.vo.HongUserEnableVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* @fileName HongUserEnableServiceImpl
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
public class HongUserEnableServiceImpl implements HongUserEnableService {

    private final HongUserService hongUserService;
    private final HongUserEnableRepository hongUserEnableRepository;

    @Override
    @Transactional(readOnly = false)
    public Long join(HongUserEnableToDisableDTO hongUserEnableToDisableDTO) {
        HongUser hongUser = hongUserService.getUserAndChangeEnable(hongUserEnableToDisableDTO.getUserId());            // get user entity and change enable to false
        HongUserEnable hongUserEnable = HongUserEnable.hongUserEnableInsert()
                                                        .hongUser(hongUser)
                                                        .enableMsg(hongUserEnableToDisableDTO.getEnableMsg())
                                                        .build();
        HongUserEnable save = hongUserEnableRepository.save(hongUserEnable);
        return save.getId();
    }

    @Override
    public HongUserEnableVO view(String userId) {
        HongUserEnable byHongUserId = hongUserEnableRepository.findByHongUserId(userId);
        return new HongUserEnableVO(byHongUserId);
    }

    @Override
    public HongUserEnableVO getInfo(Long id) {
        HongUserEnable hongUserEnable = hongUserEnableRepository.findByHongUserIdLong(id);
        return new HongUserEnableVO(hongUserEnable);
    }

    @Override
    @Transactional(readOnly = false)
    public void updateDisableToEnable(Long id) {
        HongUserEnable hongUserEnable = hongUserEnableRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("there is no info"));
        hongUserEnable.deleteUserEnable();
        hongUserService.changeDisableToEnable(hongUserEnable.getHongUser().getId());
    }
}