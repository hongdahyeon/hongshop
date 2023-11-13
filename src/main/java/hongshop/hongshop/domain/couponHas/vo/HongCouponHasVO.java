package hongshop.hongshop.domain.couponHas.vo;

import hongshop.hongshop.domain.coupon.vo.HongCouponVO;
import hongshop.hongshop.domain.couponHas.HongCouponHas;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class HongCouponHasVO {

    private Long hongCouponHasId;
    private HongCouponVO hongCouponVO;
    private String useAt;
    private String userId;

    public HongCouponHasVO(HongCouponHas hongCouponHas){
        this.hongCouponHasId = hongCouponHas.getId();
        this.hongCouponVO = new HongCouponVO(hongCouponHas.getHongCoupon());
        this.useAt = hongCouponHas.getUseAt();
        this.userId = hongCouponHas.getHongUser().getUserId();
    }
}