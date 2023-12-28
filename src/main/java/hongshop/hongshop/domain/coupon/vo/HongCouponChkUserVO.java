package hongshop.hongshop.domain.coupon.vo;

import hongshop.hongshop.domain.coupon.HongCoupon;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
* @fileName HongCouponChkUserVO
* @author dahyeon
* @version 1.0.0
* @date 2023-12-28
* @summary (관리자) 쿠폰 리스트 조회를 위한 VO
 *          -> 해당 쿠폰 사용여부 N : 삭제 가능 (== userIsEmpty is true)
 *          -> 해당 쿠폰 사용여부 Y
 *               => * 해당 쿠폰을 가지고 있는 사용자 중에..
 *                          아직 쿠폰 사용여부가 N이고    (HONG-COUPON-HAS의 useAt == N)
 *                          갖고 있는 쿠폰 삭제여부가 N인 (HONG-COUPON-HAS의 deleteYn == N)
 *                   - 사용자가 없다면 '삭제 가능' (=== userEmpty is true)
 *                   - 사용자가 있다면 '삭제 불가능' (=== userEmpty is false)
**/

@Getter @Setter
public class HongCouponChkUserVO {

    private Long couponId;
    private String couponName;
    private Integer couponRate;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean canUse;
    private String useAt;
    private boolean userIsEmpty;

    public HongCouponChkUserVO(HongCoupon hongCoupon, boolean userIsEmpty) {
        this.couponId = hongCoupon.getId();
        this.couponName = hongCoupon.getCouponName();
        this.couponRate = hongCoupon.getCouponRate();
        this.startDate = hongCoupon.getStartDate();
        this.endDate = hongCoupon.getEndDate();
        this.canUse = this.isDateWithinRange();
        this.useAt = hongCoupon.getUseAt();
        this.userIsEmpty = userIsEmpty;
    }

    private boolean isDateWithinRange(){
        LocalDate today = LocalDate.now();
        return !(today.isBefore(startDate) || today.isAfter(endDate));
    }
}
