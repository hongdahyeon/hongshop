package hongshop.hongshop.domain.couponHas;

import hongshop.hongshop.domain.couponHas.dto.HongCouponHasDTO;
import hongshop.hongshop.domain.couponHas.dto.HongCouponHasLstDTO;
import hongshop.hongshop.domain.couponHas.vo.HongCouponHasVO;
import hongshop.hongshop.domain.user.HongUser;

import java.util.List;

public interface HongCouponHasService {

    Long join(HongUser hongUser, HongCouponHasDTO hongCouponHasDTO);

    Integer joinAll(HongCouponHasLstDTO hongCouponHasLstDTO);

    List<HongCouponHasVO> list();

    HongCouponHasVO view(Long id);

    List<HongCouponHasVO> listByHongUser(HongUser hongUser);

    List<HongCouponHasVO> listByHongUserWithDeleteYn(HongUser hongUser);

    void delete(Long id);

    Integer useCoupon(Long id);

    HongCouponHas getHongCouponHas(Long id);
}
