package hongshop.hongshop.domain.order;

import hongshop.hongshop.domain.couponHas.HongCouponHas;
import hongshop.hongshop.domain.user.HongUser;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
* @fileName HongOrder
* @author dahyeon
* @version 1.0.0
* @date 2023-07-18
* @summary
**/

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "hong_order")
@Getter
public class HongOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hong_order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hong_user_id")
    private HongUser hongUser;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Column(name = "order_date")
    private String orderDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hong_coupon_has_id")
    private HongCouponHas hongCouponHas;

    @Builder(builderMethodName = "hongOrderInsertBuilder")
    public HongOrder(HongUser hongUser, OrderStatus orderStatus, String orderDate, HongCouponHas hongCouponHas) {
        this.hongUser = hongUser;
        this.orderStatus = orderStatus;
        this.orderDate = orderDate;
        this.hongCouponHas = hongCouponHas;
    }

    public void updateStatus(OrderStatus orderStatus){
        this.orderStatus = orderStatus;
    }
}
