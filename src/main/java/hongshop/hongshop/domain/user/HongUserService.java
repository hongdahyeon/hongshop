package hongshop.hongshop.domain.user;

import hongshop.hongshop.domain.base.Address;
import hongshop.hongshop.domain.user.dto.HongUserPwdDateDTO;
import hongshop.hongshop.domain.user.dto.HongUserDTO;
import hongshop.hongshop.domain.user.dto.HongUserRoleDTO;
import hongshop.hongshop.domain.user.vo.HongUserCouponVO;
import hongshop.hongshop.domain.user.vo.HongUserMessageVO;
import hongshop.hongshop.domain.user.vo.HongUserVO;

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

    // front
    Long joinUser(HongUserDTO hongUserDTO);

    Boolean checkUserId(String userId);

    int checkUserEmail(String userEmail);

    boolean initialPassword(String userEmail, String userName);

    boolean findUserId(String userEmail, String userName);

    void changePwdEndDate(HongUserPwdDateDTO hongUserPwdDateDTO);

    int sendEmail(String userId, String userEmail);


    // not front
    void updateHongUser(HongUserDTO hongUserDTO);

    List<HongUserVO> list();

    void updateUserRole(Long id, HongUserRoleDTO hongUserRoleDTO);

    void updateUserNonLocked(String userId);

    List<HongUserCouponVO> getUserListForCoupon();


    // together
    Optional<HongUser> getHongUser(String userId);

    HongUserVO getHongUserByUserId(String userId);

    HongUserVO getHongUserById(Long id);

    Address getAddress(Long id);

    List<HongUserMessageVO> getMessageCanUser(HongUser itsMe);

    HongUser getUserAndChangeEnable(Long id);

    void changeDisableToEnable(Long id);

}
