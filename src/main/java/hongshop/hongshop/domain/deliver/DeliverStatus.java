package hongshop.hongshop.domain.deliver;

/**
* @fileName DeliverStatus
* @author dahyeon
* @version 1.0.0
* @date 2023-07-19
* @summary  (1) DELIVERING : 배송 중
 *          (2) DELIVERED : 배송 완료
 *          (3) AWAIT : 배송 대기
**/

public enum DeliverStatus {
    DELIVERING("배송중"), DELIVERED("배송완료"), AWAIT("대기");

    private String text;
    DeliverStatus(String text){
        this.text = text;
    }

    public static String getText(String status) {
        for(DeliverStatus deliverStatus : DeliverStatus.values()){
            if(deliverStatus.name().equals(status)){
                return deliverStatus.text;
            }
        }
        throw new IllegalArgumentException("error");
    }
}
