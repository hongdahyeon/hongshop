package hongshop.hongshop.domain.coupon;

import hongshop.hongshop.domain.coupon.dto.HongCouponDTO;
import hongshop.hongshop.domain.coupon.vo.HongCouponVO;

import java.util.List;

public interface HongCouponService {

    Long join(HongCouponDTO hongCouponDTO);

    List<HongCouponVO> list();

    List<HongCouponVO> listWithChkUser();

    HongCouponVO view(Long id);

    void update(Long id, HongCouponDTO hongCouponDTO);

    void delete(Long id);

    HongCoupon getHongCoupon(Long id);
}
