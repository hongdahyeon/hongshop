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

    private Long hongCouponRequestId;           // request id
    private Long userLongId;                    // 사용자 id
    private String userId;                      // 사용자 Id
    private String userName;                    // 사용자 이름
    private Long couponId;                      // 쿠폰 id
    private String couponName;                  // 쿠폰 이름
    private Integer couponRate;                 // 쿠폰 할인 가격
    private String startDate;                   // 쿠폰 시작일
    private String endDate;                     // 쿠폰 종료일
    private String requestDate;                 // 요청 일자
    private String requestApproveYn;            // 승인여부

    public HongCouponRequestVO(HongCouponRequest hongCouponRequest) {
        this.hongCouponRequestId = hongCouponRequest.getId();
        this.userLongId = hongCouponRequest.getHongUser().getId();
        this.userId = hongCouponRequest.getHongUser().getUserId();
        this.userName = hongCouponRequest.getHongUser().getUserName();
        this.couponId = hongCouponRequest.getHongCoupon().getId();
        this.couponName = hongCouponRequest.getHongCoupon().getCouponName();
        this.couponRate = hongCouponRequest.getHongCoupon().getCouponRate();
        this.startDate = hongCouponRequest.getHongCoupon().getStartDate().toString();
        this.endDate = hongCouponRequest.getHongCoupon().getEndDate().toString();
        this.requestDate = hongCouponRequest.getRequestDate();
        this.requestApproveYn = hongCouponRequest.getRequestApproveYn();
    }

}
