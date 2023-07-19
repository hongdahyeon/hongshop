package hongshop.hongshop.domain.deliver;

import hongshop.hongshop.domain.base.Address;
import hongshop.hongshop.domain.order.HongOrder;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
* @fileName HongDeliver
* @author dahyeon
* @version 1.0.0
* @date 2023-07-19
* @summary  1개의 주문에 대해 1개의 배송정보만 입력이 가능하다.
**/

@Entity
@Table(name = "hong_deliver")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HongDeliver {

    @Id
    @GeneratedValue
    @Column(name = "hong_deliver_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hong_order_id")
    private HongOrder hongOrder;

    @Enumerated(EnumType.STRING)
    private DeliverStatus deliverStatus;

    @Embedded
    private Address address;

    @Builder(builderMethodName = "hongDeliverInsertBuilder")
    public HongDeliver(HongOrder hongOrder, DeliverStatus deliverStatus, Address address) {
        this.hongOrder = hongOrder;
        this.deliverStatus = deliverStatus;
        this.address = address;
    }
}
