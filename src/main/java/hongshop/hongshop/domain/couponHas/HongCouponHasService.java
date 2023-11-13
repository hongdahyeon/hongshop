package hongshop.hongshop.domain.couponHas;

import hongshop.hongshop.domain.couponHas.dto.HongCouponHasDTO;
import hongshop.hongshop.domain.couponHas.vo.HongCouponHasVO;
import hongshop.hongshop.domain.user.HongUser;

import java.util.List;

public interface HongCouponHasService {

    Long join(HongUser hongUser, HongCouponHasDTO hongCouponHasDTO);

    List<HongCouponHasVO> list();

    HongCouponHasVO view(Long id);

    List<HongCouponHasVO> listByHongUser(HongUser hongUser);

    void delete(Long id);

    Integer useCoupon(Long id);
}