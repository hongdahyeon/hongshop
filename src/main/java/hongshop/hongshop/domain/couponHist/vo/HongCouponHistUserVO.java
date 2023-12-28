package hongshop.hongshop.domain.couponHist.vo;

import hongshop.hongshop.domain.couponHist.HongCouponHist;
import lombok.Getter;
import lombok.Setter;

/**
* @fileName HongCouponHistUserVO
* @author dahyeon
* @version 1.0.0
* @date 2023-11-14
* @summary  쿠폰Id를 통해 이력 조회 with 사용자Id VO
**/

@Getter @Setter
public class HongCouponHistUserVO {

    private Long couponHistId;
    private String userId;
    private String useDate;

    public HongCouponHistUserVO(HongCouponHist hongCouponHist){
        this.couponHistId = hongCouponHist.getId();
        this.userId = hongCouponHist.getHongCouponHas().getHongUser().getUserId();
        this.useDate = hongCouponHist.getUseDate();
    }
}
