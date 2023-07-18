package hongshop.hongshop.domain.orderDetail;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class HongOrderDetailVO {

    private String productName;
    private Integer orderCnt;
    private Integer orderPrice;

    public HongOrderDetailVO(HongOrderDetail hongOrderDetail) {
        this.productName = hongOrderDetail.getHongProduct().getProductName();
        this.orderCnt = hongOrderDetail.getOrderCnt();
        this.orderPrice = hongOrderDetail.getOrderPrice();
    }
}
