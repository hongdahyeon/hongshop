package hongshop.hongshop.domain.couponRequest.vo;


import hongshop.hongshop.domain.couponRequest.HongCouponRequest;
import lombok.Getter;
import lombok.Setter;

/**
* @fileName HongCouponRequestVO
* @author dahyeon
* @version 1.0.0
* @date 2023-11-14
* @summary
**/


@Getter @Setter
public class HongCouponRequestVO {

    private Long hongCouponRequestId;
    private String userId;
    private String userName;
    private Long couponId;
    private String couponName;
    private String requestDate;
    private String requestApproveYn;

    public HongCouponRequestVO(HongCouponRequest hongCouponRequest) {
        this.hongCouponRequestId = hongCouponRequest.getId();
        this.userId = hongCouponRequest.getHongUser().getUserId();
        this.userName = hongCouponRequest.getHongUser().getUserName();
        this.couponId = hongCouponRequest.getHongCoupon().getId();
        this.couponName = hongCouponRequest.getHongCoupon().getCouponName();
        this.requestDate = hongCouponRequest.getRequestDate();
        this.requestApproveYn = hongCouponRequest.getRequestApproveYn();
    }

}
