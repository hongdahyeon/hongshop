package hongshop.hongshop.domain.couponRequest;

import hongshop.hongshop.domain.couponRequest.dto.HongCouponRequestApproveLstDTO;
import hongshop.hongshop.domain.couponRequest.dto.HongCouponRequestLstDTO;
import hongshop.hongshop.domain.couponRequest.vo.HongCouponRequestVO;
import hongshop.hongshop.domain.user.HongUser;

import java.util.List;

public interface HongCouponRequestService {

    Integer joinAll(HongCouponRequestLstDTO hongCouponRequestLstDTO, HongUser hongUser);

    List<HongCouponRequestVO> listOfUser(HongUser hongUser);

    void delete(Long id);

    Integer deleteSeveral(List<Long> ids);

    List<HongCouponRequestVO> listByCoupon(Long couponId);

    Integer approveRequest(HongCouponRequestApproveLstDTO hongCouponRequestApproveLstDTO);

    void insertCouponHas(Long couponId, HongUser hongUser);
}