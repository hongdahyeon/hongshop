package hongshop.hongshop.domain.orderDetail.vo;

import hongshop.hongshop.domain.fileGroup.vo.HongFileGroupVO;
import hongshop.hongshop.domain.orderDetail.HongOrderDetail;
import lombok.Getter;
import lombok.Setter;

/**
* @fileName HongOrderDetailFileVO
* @author dahyeon
* @version 1.0.0
* @date 2023-11-11
* @summary 주문 상세건 -> 해당 상품들 file 이미지 vo
**/

@Getter
@Setter
public class HongOrderDetailFileVO {

    private Long orderDetailId;
    private Long productId;
    private String productName;
    private Integer orderCnt;
    private Integer orderPrice;
    private HongFileGroupVO file;

    public HongOrderDetailFileVO(HongOrderDetail hongOrderDetail, HongFileGroupVO file) {
        this.orderDetailId = hongOrderDetail.getId();
        this.productId = hongOrderDetail.getHongProduct().getId();
        this.productName = hongOrderDetail.getHongProduct().getProductName();
        this.orderCnt = hongOrderDetail.getOrderCnt();
        this.orderPrice = hongOrderDetail.getOrderPrice();
        this.file = file;
    }

    public HongOrderDetailFileVO(HongOrderDetail hongOrderDetail) {
        this.orderDetailId = hongOrderDetail.getId();
        this.productId = hongOrderDetail.getHongProduct().getId();
        this.productName = hongOrderDetail.getHongProduct().getProductName();
        this.orderCnt = hongOrderDetail.getOrderCnt();
        this.orderPrice = hongOrderDetail.getOrderPrice();
    }
}
