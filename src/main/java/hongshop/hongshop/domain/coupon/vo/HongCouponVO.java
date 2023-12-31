package hongshop.hongshop.domain.coupon.vo;

import hongshop.hongshop.domain.coupon.HongCoupon;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
* @fileName HongCouponVO
* @author dahyeon
* @version 1.0.0
* @date 2023-12-28
* @summary 기본적인 쿠폰 조회 VO
**/

@Getter @Setter
public class HongCouponVO {

    private Long couponId;
    private String couponName;
    private Integer couponRate;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean canUse;
    private String useAt;

    public HongCouponVO(HongCoupon hongCoupon) {
        this.couponId = hongCoupon.getId();
        this.couponName = hongCoupon.getCouponName();
        this.couponRate = hongCoupon.getCouponRate();
        this.startDate = hongCoupon.getStartDate();
        this.endDate = hongCoupon.getEndDate();
        this.canUse = this.isDateWithinRange();
        this.useAt = hongCoupon.getUseAt();
    }

    private boolean isDateWithinRange(){
        LocalDate today = LocalDate.now();
        return !(today.isBefore(startDate) || today.isAfter(endDate));
    }
}
