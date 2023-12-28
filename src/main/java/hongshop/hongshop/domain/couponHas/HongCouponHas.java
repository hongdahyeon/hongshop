package hongshop.hongshop.domain.couponHas;


import hongshop.hongshop.domain.coupon.HongCoupon;
import hongshop.hongshop.domain.user.HongUser;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
* @fileName HongCouponHas
* @author dahyeon
* @version 1.0.0
* @date 2023-11-13
* @summary  사용자가 등록한 쿠폰 등록 테이블
**/


@Entity
@Table(name = "hong_coupon_has")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HongCouponHas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hong_coupon_has_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hong_coupon_id")
    private HongCoupon hongCoupon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hong_user_id")
    private HongUser hongUser;

    @Column(name = "delete_yn")
    private String deleteYn;

    @Column(name = "use_at")    // if use-at is 'Y' it needs to be at hist table
    private String useAt;


    @Builder(builderMethodName = "hongCouponHasInsert")
    public HongCouponHas(HongCoupon hongCoupon, HongUser hongUser) {
        this.hongCoupon = hongCoupon;
        this.hongUser = hongUser;
        this.deleteYn = "N";
        this.useAt = "N";
    }

    public void deleteHongCouponHas(){
        this.deleteYn = "Y";
    }

    public void useHongCoupon(){
        this.useAt = "Y";
    }
}
