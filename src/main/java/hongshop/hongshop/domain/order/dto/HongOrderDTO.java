package hongshop.hongshop.domain.order.dto;

import lombok.Getter;
import lombok.Setter;

/**
* @fileName HongOrderDTO
* @author dahyeon
* @version 1.0.0
* @date 2023-07-18
* @summary
**/

@Getter @Setter
public class HongOrderDTO {

    private Integer orderCnt;
    private Long hongProductId;         // 상품 번호 id

}
