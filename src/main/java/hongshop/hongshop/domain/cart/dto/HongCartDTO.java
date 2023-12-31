package hongshop.hongshop.domain.cart.dto;

import lombok.Getter;
import lombok.Setter;

/**
* @fileName HongCartDTO
* @author dahyeon
* @version 1.0.0
* @date 2023-07-18
* @summary 장바구니 DTO (POST, PUT용)
**/

@Getter @Setter
public class HongCartDTO {

    private Long hongProductId;
    private Integer cartCnt;
}
