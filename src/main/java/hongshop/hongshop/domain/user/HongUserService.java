package hongshop.hongshop.domain.user;

import java.util.List;
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

    Boolean checkUserEmail(String userEmail);

    void updateHongUser(HongUserDTO hongUserDTO);

    HongUserVO getHongUserById(Long id);

    List<HongUserVO> list();

    void updateUserRole(Long id, HongUserRoleDTO hongUserRoleDTO);
}
