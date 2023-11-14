package hongshop.hongshop.domain.couponRequest;


import hongshop.hongshop.domain.coupon.HongCoupon;
import hongshop.hongshop.domain.user.HongUser;
import hongshop.hongshop.global.util.TimeUtil;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
* @fileName HongCouponRequest
* @author dahyeon
* @version 1.0.0
* @date 2023-11-14
* @summary 사용자의 쿠폰 요청 등록 테이블
 *          -> requestApproveYn 값에 따라 요청 여부 결정
 *              -> 'Y'일 경우 요청 승인 -> 'hong_coupon_has' 테이블에 저장
**/

@Entity
@Table(name = "hong_coupon_request")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HongCouponRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hong_coupon_request_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hong_user_id")
    private HongUser hongUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hong_coupon_id")
    private HongCoupon hongCoupon;

    @Column(name = "request_date")
    private String requestDate;

    @Column(name = "request_approve_yn")
    private String requestApproveYn;

    @Column(name = "delete_yn")
    private String deleteYn;

    @Builder(builderMethodName = "hongCouponRequestInsert")
    public HongCouponRequest(HongUser hongUser, HongCoupon hongCoupon) {
        this.hongUser = hongUser;
        this.hongCoupon = hongCoupon;
        this.requestDate = TimeUtil.nowDate();
        this.requestApproveYn = "N";
        this.deleteYn = "N";
    }

    public void deleteRequest(){
        this.deleteYn = "Y";
    }

    public void approveRequest(){
        this.requestApproveYn = "Y";
    }
}
