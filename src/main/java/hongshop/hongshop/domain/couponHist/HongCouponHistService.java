package hongshop.hongshop.domain.couponHist;

import hongshop.hongshop.domain.couponHist.dto.HongCouponHistDTO;
import hongshop.hongshop.domain.couponHist.vo.HongCouponHistUserVO;
import hongshop.hongshop.domain.couponHist.vo.HongCouponHistVO;

import java.util.List;

public interface HongCouponHistService {

    Long join(HongCouponHistDTO hongCouponHistDTO);

    List<HongCouponHistVO> list();

    List<HongCouponHistUserVO> listByCouponId(Long id);
}
