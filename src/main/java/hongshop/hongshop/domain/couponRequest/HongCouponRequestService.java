package hongshop.hongshop.domain.couponRequest;

import hongshop.hongshop.domain.couponRequest.dto.HongCouponRequestDTO;
import hongshop.hongshop.domain.couponRequest.dto.HongCouponRequestApproveLstDTO;
import hongshop.hongshop.domain.couponRequest.dto.HongCouponRequestLstDTO;
import hongshop.hongshop.domain.couponRequest.vo.HongCouponRequestVO;
import hongshop.hongshop.domain.user.HongUser;

import java.util.List;

public interface HongCouponRequestService {

    Long join(HongCouponRequestDTO hongCouponRequestDTO, HongUser hongUser);

    Integer joinAll(HongCouponRequestLstDTO hongCouponRequestLstDTO, HongUser hongUser);

    List<HongCouponRequestVO> list();

    List<HongCouponRequestVO> listOfUser(HongUser hongUser);

    void delete(Long id);

    List<HongCouponRequestVO> listByCoupon(Long couponId);

    Integer approveRequest(HongCouponRequestApproveLstDTO hongCouponRequestApproveLstDTO);

    void insertCouponHas(Long couponId, HongUser hongUser);
}