package hongshop.hongshop.domain.product;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class HongProductDTO {
    private Long hongCategoryId;
    private String productName;
    private Integer productCnt;
    private Integer productPrice;
}
