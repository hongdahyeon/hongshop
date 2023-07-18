package hongshop.hongshop.domain.order;

import hongshop.hongshop.domain.orderDetail.HongOrderDetailVO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
* @fileName HongOrderVO
* @author dahyeon
* @version 1.0.0
* @date 2023-07-18
* @summary
**/

@Getter @Setter
public class HongOrderVO {

    private OrderStatus orderStatus;
    private String orderDate;
    private String userId;
    private List<HongOrderDetailVO> orderDetails;

    public HongOrderVO(HongOrder hongOrder, List<HongOrderDetailVO> orderDetails) {
        this.orderStatus = hongOrder.getOrderStatus();
        this.orderDate = hongOrder.getOrderDate();
        this.userId = hongOrder.getHongUser().getUserId();
        this.orderDetails = orderDetails;
    }
}