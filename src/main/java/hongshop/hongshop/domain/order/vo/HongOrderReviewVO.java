package hongshop.hongshop.domain.order.vo;

import lombok.Getter;
import lombok.Setter;

/**
* @fileName HongOrderReviewVO
* @author dahyeon
* @version 1.0.0
* @date 2023-11-11
* @summary  주문건에 대해 주문 상세 상품들 리스트 가져온다.
 *          이때 해당 주문 상세 상품들에 대해 리뷰가 작성되었는지 여부도 함께 가져온다.
**/

@Getter
@Setter
public class HongOrderReviewVO {


    private String productName;
    private Long orderDetailId;
    private boolean reviewChkEmpty;

    public HongOrderReviewVO(String productName, Long orderDetailId, boolean reviewChkEmpty) {
        this.productName = productName;
        this.orderDetailId = orderDetailId;
        this.reviewChkEmpty = reviewChkEmpty;
    }
}
