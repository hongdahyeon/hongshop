package hongshop.hongshop.domain.product;

import lombok.Getter;
import lombok.Setter;

/**
* @fileName HongProductDTO
* @author dahyeon
* @version 1.0.0
* @date 2023-07-18
* @summary
**/

@Getter @Setter
public class HongProductDTO {
    private Long hongCategoryId;
    private String productName;
    private Integer productCnt;
    private Integer originProductCnt;
    private Integer productPrice;
}
