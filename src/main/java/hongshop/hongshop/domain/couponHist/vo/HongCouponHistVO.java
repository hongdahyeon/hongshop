package hongshop.hongshop.domain.couponHist.vo;

import hongshop.hongshop.domain.couponHas.vo.HongCouponHasVO;
import hongshop.hongshop.domain.couponHist.HongCouponHist;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class HongCouponHistVO {

    private Long hongCouponHistId;
    private HongCouponHasVO hongCouponHasVO;

    public HongCouponHistVO(HongCouponHist hongCouponHist){
        this.hongCouponHistId = hongCouponHist.getId();
        this.hongCouponHasVO = new HongCouponHasVO(hongCouponHist.getHongCouponHas());
    }
}
