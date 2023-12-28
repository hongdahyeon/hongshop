package hongshop.hongshop.domain.deliver.vo;

import hongshop.hongshop.domain.base.Address;
import hongshop.hongshop.domain.deliver.HongDeliver;
import hongshop.hongshop.domain.order.OrderStatus;
import lombok.Getter;
import lombok.Setter;

/**
* @fileName HongDeliverReviewChkVO
* @author dahyeon
* @version 1.0.0
* @date 2023-12-28
* @summary (관리자) 배송 관리 : 배송 정보 조회 
 *              -> 배송/주문에 관해서 상세 주문건들에 대해 리뷰가 달려있는지 체크
 *                 1개라도 리뷰가 달려있다면 해당 주문/베송건은 상태값 변경 불가능
**/

@Getter
@Setter
public class HongDeliverReviewChkVO {
    private Long deliverId;
    private String orderUser;
    private OrderStatus orderStatus;
    private String orderDate;
    private String deliverStatus;
    private Address address;
    private boolean writeReviewEmpty;

    public HongDeliverReviewChkVO(HongDeliver hongDeliver, boolean writeReviewEmpty) {
        this.deliverId = hongDeliver.getId();
        this.orderUser = hongDeliver.getHongOrder().getHongUser().getUserId();
        this.orderStatus = hongDeliver.getHongOrder().getOrderStatus();
        this.orderDate = hongDeliver.getHongOrder().getOrderDate();
        this.deliverStatus = hongDeliver.getDeliverStatus().toString();
        this.address = hongDeliver.getAddress();
        this.writeReviewEmpty = writeReviewEmpty;
    }
}