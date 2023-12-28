package hongshop.hongshop.domain.couponHist.vo;

import hongshop.hongshop.domain.couponHas.vo.HongCouponHasVO;
import hongshop.hongshop.domain.couponHist.HongCouponHist;
import lombok.Getter;
import lombok.Setter;

/**
* @fileName HongCouponHistVO
* @author dahyeon
* @version 1.0.0
* @date 2023-12-28
* @summary 쿠폰 사용 이력 조회 VO
**/

@Getter @Setter
public class HongCouponHistVO {

    private Long hongCouponHistId;
    private HongCouponHasVO hongCouponHasVO;

    public HongCouponHistVO(HongCouponHist hongCouponHist){
        this.hongCouponHistId = hongCouponHist.getId();
        this.hongCouponHasVO = new HongCouponHasVO(hongCouponHist.getHongCouponHas());
    }
}
