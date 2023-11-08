package hongshop.hongshop.domain.order;

/**
* @fileName OrderStatus
* @author dahyeon
* @version 1.0.0
* @date 2023-07-18
* @summary  (1) CHARGED : 결제 완료
 *          (2) CANCEL : 주문 취소
 *          (3) DELIVER_SUCCESS : 배송 완료
 *          (4) DELIVER_ING : 배송 중
**/

public enum OrderStatus {
    CHARGED("계산 완료"), CANCEL("주문 취소"), DELIVER_SUCCESS("배송 성공"), DELIVER_ING("배송중");
    private String text;

    OrderStatus(String text) {
        this.text = text;
    }

    public static String getText(String status) {
        for(OrderStatus orderStatus : OrderStatus.values()) {
            if(orderStatus.name().equals(status)) {
                return orderStatus.text;
            }
        }
        throw new IllegalArgumentException("error");
    }
}
