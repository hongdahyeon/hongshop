package hongshop.hongshop.domain.coupon.vo;

import hongshop.hongshop.domain.coupon.HongCoupon;
import hongshop.hongshop.domain.couponRequest.vo.HongCouponRequestVO;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
* @fileName HongCouponGroupRequestVO
* @author dahyeon
* @version 1.0.0
* @date 2023-11-14
* @summary  쿠폰 -> (하위) 해당 쿠폰 요청 리스트
**/

@Getter @Setter
public class HongCouponGroupRequestVO {

    private Long couponId;
    private String couponName;
    private Integer couponRate;
    private List<HongCouponRequestVO> groupRequestVOS = new ArrayList<>();

    public HongCouponGroupRequestVO(HongCoupon hongCoupon, List<HongCouponRequestVO> groupRequestVOS) {
        this.couponId = hongCoupon.getId();
        this.couponName = hongCoupon.getCouponName();
        this.couponRate = hongCoupon.getCouponRate();
        this.groupRequestVOS = groupRequestVOS;
    }
}
