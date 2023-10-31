package hongshop.hongshop.domain.orderDetail.vo;

import hongshop.hongshop.domain.order.HongOrder;
import hongshop.hongshop.domain.order.OrderStatus;
import hongshop.hongshop.domain.orderDetail.HongOrderDetail;
import lombok.Getter;
import lombok.Setter;

/**
 * @fileName HongOrderDetailUserVO
 * @author dahyeon
 * @version 1.0.0
 * @date 2023-10-30
 * @summary 각 상품에 대해 주문 사용자 정보 list를 위한 VO
 **/

@Getter
@Setter
public class HongOrderDetailUserVO {

    private Long orderId;                   // 주문 ID
    private OrderStatus orderStatus;        // 주문 상태 정보
    private String userId;                  // 주문 사용자 ID
    private String orderDate;               // 주문 날짜
    private String productName;             // 상품 이름
    private Integer orderCnt;               // 주문 개수
    private Integer orderPrice;             // 주문 가격

    public HongOrderDetailUserVO(HongOrder hongOrder, HongOrderDetail hongOrderDetail) {
        this.orderId = hongOrderDetail.getId();
        this.orderStatus = hongOrder.getOrderStatus();
        this.userId = hongOrder.getHongUser().getUserId();
        this.orderDate = hongOrder.getOrderDate();
        this.productName = hongOrderDetail.getHongProduct().getProductName();
        this.orderCnt = hongOrderDetail.getOrderCnt();
        this.orderPrice = hongOrderDetail.getOrderPrice();
    }
}