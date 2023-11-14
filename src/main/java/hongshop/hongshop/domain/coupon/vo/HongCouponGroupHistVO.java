package hongshop.hongshop.domain.coupon.vo;

import hongshop.hongshop.domain.coupon.HongCoupon;
import hongshop.hongshop.domain.couponHist.vo.HongCouponHistUserVO;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
* @fileName HongCouponGroupHistVO
* @author dahyeon
* @version 1.0.0
* @date 2023-11-14
* @summary (관리자) 쿠폰 사용 이력 보여주기 위한 VO
 *          => 쿠폰 하위에 쿠폰 사용자 이력 리스트로 이루어진 VO
**/

@Getter @Setter
public class HongCouponGroupHistVO {

    private Long couponId;
    private String couponName;
    private Integer couponRate;
    private List<HongCouponHistUserVO> histUserVOList = new ArrayList<>();

    public HongCouponGroupHistVO(HongCoupon hongCoupon, List<HongCouponHistUserVO> histUserVOList){
        this.couponId = hongCoupon.getId();
        this.couponName = hongCoupon.getCouponName();
        this.couponRate = hongCoupon.getCouponRate();
        this.histUserVOList = histUserVOList;
    }
}
