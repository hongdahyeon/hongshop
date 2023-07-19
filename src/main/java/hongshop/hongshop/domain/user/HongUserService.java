package hongshop.hongshop.domain.user;

import java.util.Optional;

/**
 * @fileName HongUserService
 * @author dahyeon
 * @version 1.0.0
 * @date 2023-07-17
 * @summary HongUserService에 대한 구현체는 HongUserServiceImpl에서 구현한다.
 **/

public interface HongUserService {

    Long joinUser(HongUserDTO hongUserDTO);

    Optional<HongUser> getHongUser(String userId);

    HongUserVO getHongUserByUserId(String userId);

    Boolean checkUserId(String userId);
}
