package hongshop.hongshop.domain.order.dto;

import lombok.Getter;
import lombok.Setter;

/**
* @fileName HongOrderFromCartDTO
* @author dahyeon
* @version 1.0.0
* @date 2023-11-08
* @summary  장바구니에서 상품 주문하기 (상세)
**/

@Getter @Setter
public class HongOrderFromCartDetailsDTO {

    private Integer orderCnt;
    private Long hongProductId;         // 상품 번호 id
    private Long hongCartId;
}
