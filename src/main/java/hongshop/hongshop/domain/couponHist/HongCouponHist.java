package hongshop.hongshop.domain.couponHist;

import hongshop.hongshop.domain.couponHas.HongCouponHas;
import hongshop.hongshop.global.util.TimeUtil;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "hong_coupon_hist")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HongCouponHist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hong_coupon_hist_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hong_coupon_has_id")
    private HongCouponHas hongCouponHas;

    @Column(name = "use_date")
    private String useDate;


    @Builder(builderMethodName = "hongCouponHistInsert")
    public HongCouponHist(HongCouponHas hongCouponHas) {
        this.hongCouponHas = hongCouponHas;
        this.useDate = TimeUtil.nowDate();
    }
}
