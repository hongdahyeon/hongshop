package hongshop.hongshop.domain.order.vo;

import hongshop.hongshop.domain.order.HongOrder;
import hongshop.hongshop.domain.orderDetail.vo.HongOrderDetailVO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
* @fileName HongManagerOrderReviewVO
* @author dahyeon
* @version 1.0.0
* @date 2023-12-28
* @summary (관리자) 주문건들에 대해 ,,
 *          -> 상세 주문 정보 리스트 조회
 *          => 상세 주문 정보들 중에 1개라도 리뷰가 달렸는지 체크
 *              => (1개라도 리뷰가 달렸다면 writeReviewEmpty == false) => 상태값 변경 불가능
 *              => (리뷰가 아예 안달렸다면 writeReviewEmpty == true) => 상태값 변경 가능
**/

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
