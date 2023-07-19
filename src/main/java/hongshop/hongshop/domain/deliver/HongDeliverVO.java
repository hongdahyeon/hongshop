package hongshop.hongshop.domain.deliver;

import hongshop.hongshop.domain.base.Address;
import hongshop.hongshop.domain.order.HongOrder;
import hongshop.hongshop.domain.order.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class HongDeliverVO {
    private String orderUser;
    private OrderStatus orderStatus;
    private String orderDate;
    private DeliverStatus deliverStatus;
    private Address address;

    public HongDeliverVO(HongDeliver hongDeliver) {
        this.orderUser = hongDeliver.getHongOrder().getHongUser().getUserId();
        this.orderStatus = hongDeliver.getHongOrder().getOrderStatus();
        this.orderDate = hongDeliver.getHongOrder().getOrderDate();
        this.deliverStatus = hongDeliver.getDeliverStatus();
        this.address = hongDeliver.getAddress();
    }
}
