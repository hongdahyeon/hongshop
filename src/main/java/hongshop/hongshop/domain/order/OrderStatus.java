package hongshop.hongshop.domain.order;

/**
* @fileName OrderStatus
* @author dahyeon
* @version 1.0.0
* @date 2023-07-18
* @summary  (1) READY : 배송 준비 중
 *          (2) DELIVERY : 배송중
 *          (3) COMPLETE : 배송 완료
 *          (4) CANCEL : 배송 취소
**/

public enum OrderStatus {
    READY, DELIVERY, COMPLETE, CANCEL
}
