package hongshop.hongshop.domain.order.vo;

import hongshop.hongshop.domain.base.Address;
import hongshop.hongshop.domain.deliver.DeliverStatus;
import hongshop.hongshop.domain.deliver.vo.HongDeliverVO;
import hongshop.hongshop.domain.order.HongOrder;
import hongshop.hongshop.domain.order.OrderStatus;
import hongshop.hongshop.domain.orderDetail.vo.HongOrderDetailVO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class HongOrderDeliverVO {
    private Long orderId;
    private String orderStatus;
    private String orderStatStr;
    private String orderDate;
    private String userId;
    private List<HongOrderDetailVO> orderDetails;
    private String deliverStatus;
    private String deliverStatStr;
    private Address address;
    private Long deliverId;

    public HongOrderDeliverVO(HongOrder hongOrder, List<HongOrderDetailVO> orderDetails, HongDeliverVO hongDeliverVO) {
        this.orderId =  hongOrder.getId();
        this.orderStatStr = OrderStatus.getText(hongOrder.getOrderStatus().toString());
        this.orderStatus = hongOrder.getOrderStatus().toString();
        this.orderDate = hongOrder.getOrderDate();
        this.userId = hongOrder.getHongUser().getUserId();
        this.orderDetails = orderDetails;
        this.deliverStatStr = DeliverStatus.getText(hongDeliverVO.getDeliverStatus());
        this.deliverStatus = hongDeliverVO.getDeliverStatus();
        this.address = hongDeliverVO.getAddress();
        this.deliverId = hongDeliverVO.getDeliverId();
    }
}
