package hongshop.hongshop.domain.deliver.vo;

import hongshop.hongshop.domain.base.Address;
import hongshop.hongshop.domain.deliver.HongDeliver;
import hongshop.hongshop.domain.order.OrderStatus;
import lombok.Getter;
import lombok.Setter;

/**
* @fileName HongDeliverVO
* @author dahyeon
* @version 1.0.0
* @date 2023-07-19
* @summary 배송 정보 조회 VO
**/

@Getter @Setter
public class HongDeliverVO {
    private Long deliverId;
    private String orderUser;
    private OrderStatus orderStatus;
    private String orderDate;
    private String deliverStatus;
    private Address address;

    public HongDeliverVO(HongDeliver hongDeliver) {
        this.deliverId = hongDeliver.getId();
        this.orderUser = hongDeliver.getHongOrder().getHongUser().getUserId();
        this.orderStatus = hongDeliver.getHongOrder().getOrderStatus();
        this.orderDate = hongDeliver.getHongOrder().getOrderDate();
        this.deliverStatus = hongDeliver.getDeliverStatus().toString();
        this.address = hongDeliver.getAddress();
    }
}
