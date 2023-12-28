package hongshop.hongshop.domain.order.vo;

import hongshop.hongshop.domain.base.Address;
import hongshop.hongshop.domain.deliver.DeliverStatus;
import hongshop.hongshop.domain.deliver.vo.HongDeliverVO;
import hongshop.hongshop.domain.order.HongOrder;
import hongshop.hongshop.domain.order.OrderStatus;
import lombok.Getter;
import lombok.Setter;

/**
* @fileName HongOrderDeliverVO
* @author dahyeon
* @version 1.0.0
* @date 2023-11-14
* @summary  (사용자) 사용자 id를 통해 주문 정보 & 배송 정보 불러오기
**/

@Getter @Setter
public class HongOrderDeliverVO {
    private Long orderId;                           // 주문 Id
    private String orderStatus;                     // 주문 상태값
    private String orderStatStr;                    // 주문 상태값 -> 설명값
    private String orderDate;                       // 주문 날짜
    private String userId;                          // 사용자 ID
    private String deliverStatus;                   // 배달 상태값
    private String deliverStatStr;                  // 배달 상태값 -> 설명값
    private Address address;                        // 주소
    private Long deliverId;                         // 배달 Id
    private boolean useCoupon;                      // 주문시, 쿠폰 사용여부
    private Integer couponRate;                     // 주문시, 쿠폰 사용 금액

    public HongOrderDeliverVO(HongOrder hongOrder, HongDeliverVO hongDeliverVO) {
        this.orderId =  hongOrder.getId();
        this.orderStatStr = OrderStatus.getText(hongOrder.getOrderStatus().toString());
        this.orderStatus = hongOrder.getOrderStatus().toString();
        this.orderDate = hongOrder.getOrderDate();
        this.userId = hongOrder.getHongUser().getUserId();
        this.deliverStatStr = DeliverStatus.getText(hongDeliverVO.getDeliverStatus());
        this.deliverStatus = hongDeliverVO.getDeliverStatus();
        this.address = hongDeliverVO.getAddress();
        this.deliverId = hongDeliverVO.getDeliverId();

        if(hongOrder.getHongCouponHas() != null) {
            this.useCoupon = true;
            this.couponRate = hongOrder.getHongCouponHas().getHongCoupon().getCouponRate();
        } else this.useCoupon = false;
    }
}
