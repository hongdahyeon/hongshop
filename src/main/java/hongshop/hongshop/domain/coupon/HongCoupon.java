package hongshop.hongshop.domain.coupon;

import hongshop.hongshop.domain.coupon.dto.HongCouponDTO;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

/**
* @fileName HongCoupon
* @author dahyeon
* @version 1.0.0
* @date 2023-11-13
* @summary  쿠폰 테이블
**/

@Entity
@Table(name = "hong_coupon")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HongCoupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hong_coupon_id")
    private Long id;

    @Column(name = "coupon_name")
    private String couponName;

    @Column(name = "coupon_rate")
    private Integer couponRate;

    @Column(name = "use_at")
    private String useAt;

    @Column(name = "delete_yn")
    private String deleteYn;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Builder(builderMethodName = "hongCouponInsert")
    public HongCoupon(String couponName, Integer couponRate, String useAt, LocalDate startDate, LocalDate endDate) {
        this.couponName = couponName;
        this.couponRate = couponRate;
        this.useAt = useAt;
        this.startDate = startDate;
        this.endDate = endDate;
        this.deleteYn =  "N";
    }

    public void updateCoupon(HongCouponDTO hongCouponDTO) {
        if(hongCouponDTO.getCouponName() != null) this.couponName = hongCouponDTO.getCouponName();
        if(hongCouponDTO.getCouponRate() != null) this.couponRate = hongCouponDTO.getCouponRate();
        if(hongCouponDTO.getUseAt() != null) this.useAt = hongCouponDTO.getUseAt();
        if(hongCouponDTO.getStartDate() != null) this.startDate = hongCouponDTO.getStartDate();
        if(hongCouponDTO.getEndDate() != null) this.endDate = hongCouponDTO.getEndDate();
    }

    public void deleteCoupon(){
        this.deleteYn = "Y";
    }
}
