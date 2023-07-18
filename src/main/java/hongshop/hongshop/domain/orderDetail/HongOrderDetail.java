package hongshop.hongshop.domain.orderDetail;

import hongshop.hongshop.domain.order.HongOrder;
import hongshop.hongshop.domain.product.HongProduct;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
* @fileName HongOrderDetail
* @author dahyeon
* @version 1.0.0
* @date 2023-07-18
* @summary
**/

@Entity
@Table(name = "hong_order_detail")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HongOrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hong_order_detail_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hong_order_id")
    private HongOrder hongOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hong_product_id")
    private HongProduct hongProduct;

    @Column(name = "order_cnt")
    private Integer orderCnt;

    @Column(name = "order_price")
    private Integer orderPrice;

    @Builder(builderMethodName = "hongOrderDetailInsertBuilder")
    public HongOrderDetail(HongOrder hongOrder, HongProduct hongProduct, Integer orderCnt, Integer orderPrice) {
        this.hongOrder = hongOrder;
        this.hongProduct = hongProduct;
        this.orderCnt = orderCnt;
        this.orderPrice = orderPrice;
    }
}
