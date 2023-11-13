package hongshop.hongshop.domain.coupon.vo;

import hongshop.hongshop.domain.coupon.HongCoupon;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class HongCouponVO {

    private Long couponId;
    private String couponName;
    private Integer couponRate;
    private LocalDate startDate;
    private LocalDate endDate;
    private String useAt;

    public HongCouponVO(HongCoupon hongCoupon) {
        this.couponId = hongCoupon.getId();
        this.couponName = hongCoupon.getCouponName();
        this.couponRate = hongCoupon.getCouponRate();
        this.startDate = hongCoupon.getStartDate();
        this.endDate = hongCoupon.getEndDate();
        this.useAt = hongCoupon.getUseAt();
    }
}
