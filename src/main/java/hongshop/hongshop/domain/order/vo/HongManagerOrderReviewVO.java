package hongshop.hongshop.domain.order.vo;

import hongshop.hongshop.domain.order.HongOrder;
import hongshop.hongshop.domain.orderDetail.vo.HongOrderDetailVO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class HongManagerOrderReviewVO {

    private Long orderId;
    private String orderStatus;
    private String orderDate;
    private String userId;
    private List<HongOrderDetailVO> orderDetails;
    private boolean writeReviewEmpty;

    public HongManagerOrderReviewVO(HongOrder hongOrder, List<HongOrderDetailVO> orderDetails, boolean writeReviewEmpty) {
        this.orderId = hongOrder.getId();
        this.orderStatus = hongOrder.getOrderStatus().toString();
        this.orderDate = hongOrder.getOrderDate();
        this.userId = hongOrder.getHongUser().getUserId();
        this.orderDetails = orderDetails;
        this.writeReviewEmpty = writeReviewEmpty;
    }
}
