package hongshop.hongshop.domain.orderDetail.vo;

import hongshop.hongshop.domain.orderDetail.HongOrderDetail;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class HongOrderDetailVO {

    private Long productId;
    private String productName;
    private Integer orderCnt;
    private Integer orderPrice;

    public HongOrderDetailVO(HongOrderDetail hongOrderDetail) {
        this.productId = hongOrderDetail.getHongProduct().getId();
        this.productName = hongOrderDetail.getHongProduct().getProductName();
        this.orderCnt = hongOrderDetail.getOrderCnt();
        this.orderPrice = hongOrderDetail.getOrderPrice();
    }
}
