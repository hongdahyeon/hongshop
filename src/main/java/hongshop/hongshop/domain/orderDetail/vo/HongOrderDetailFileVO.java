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

/**
* @fileName HongOrderDetailFileVO
* @author dahyeon
* @version 1.0.0
* @date 2023-12-28
* @summary  주문 상세 정보 조회 with 해당 상품 정보 조회 VO
**/

@Getter
@Setter
public class HongOrderDetailFileVO {

    private Long orderDetailId;
    private Long productId;
    private Long orderId;
    private String productName;
    private Integer orderCnt;
    private Integer orderPrice;
    private HongFileGroupVO file;

    public HongOrderDetailFileVO(HongOrderDetail hongOrderDetail, HongFileGroupVO file) {
        this.orderId = hongOrderDetail.getHongOrder().getId();
        this.orderDetailId = hongOrderDetail.getId();
        this.productId = hongOrderDetail.getHongProduct().getId();
        this.productName = hongOrderDetail.getHongProduct().getProductName();
        this.orderCnt = hongOrderDetail.getOrderCnt();
        this.orderPrice = hongOrderDetail.getOrderPrice();
        this.file = file;
    }

    public HongOrderDetailFileVO(HongOrderDetail hongOrderDetail) {
        this.orderId = hongOrderDetail.getHongOrder().getId();
        this.orderDetailId = hongOrderDetail.getId();
        this.productId = hongOrderDetail.getHongProduct().getId();
        this.productName = hongOrderDetail.getHongProduct().getProductName();
        this.orderCnt = hongOrderDetail.getOrderCnt();
        this.orderPrice = hongOrderDetail.getOrderPrice();
    }
}
