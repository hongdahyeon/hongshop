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
    CHARGED, CANCEL, DELIVER_SUCCESS, DELIVER_ING;
}
