package hongshop.hongshop.domain.orderDetail.vo;

import hongshop.hongshop.domain.orderDetail.HongOrderDetail;
import lombok.Getter;
import lombok.Setter;

/**
* @fileName HongOrderDetailVO
* @author dahyeon
* @version 1.0.0
* @date 2023-12-28
* @summary 주문 상세 정보 조회 VO
**/


@Getter @Setter
public class HongOrderDetailVO {

    private Long orderDetailId;
    private Long productId;
    private String productName;
    private Integer orderCnt;
    private Integer orderPrice;

    public HongOrderDetailVO(HongOrderDetail hongOrderDetail) {
        this.orderDetailId = hongOrderDetail.getId();
        this.productId = hongOrderDetail.getHongProduct().getId();
        this.productName = hongOrderDetail.getHongProduct().getProductName();
        this.orderCnt = hongOrderDetail.getOrderCnt();
        this.orderPrice = hongOrderDetail.getOrderPrice();
    }
}
