package hongshop.hongshop.domain.couponHas;

import hongshop.hongshop.domain.couponHas.dto.HongCouponHasLstDTO;
import hongshop.hongshop.domain.couponHas.vo.HongCouponHasVO;
import hongshop.hongshop.domain.user.HongUser;

import java.util.List;

public interface HongCouponHasService {

    Integer joinAll(HongCouponHasLstDTO hongCouponHasLstDTO);

    List<HongCouponHasVO> listByHongUser(HongUser hongUser);

    List<HongCouponHasVO> listByHongUserWithDeleteYn(HongUser hongUser);

    void delete(Long id);

    Integer useCoupon(Long id);

    HongCouponHas getHongCouponHas(Long id);
}
