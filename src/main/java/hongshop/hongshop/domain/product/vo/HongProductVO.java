package hongshop.hongshop.domain.product.vo;

import hongshop.hongshop.domain.product.HongProduct;
import lombok.Getter;
import lombok.Setter;

/**
* @fileName HongProductVO
* @author dahyeon
* @version 1.0.0
* @date 2023-07-18
* @summary
**/

@Getter @Setter
public class HongProductVO {

    private Long productId;
    private String categoryName;
    private String productName;
    private Integer productCnt;
    private Integer productPrice;
    private Integer productStock;

    public HongProductVO(HongProduct hongProduct) {
        this.productId = hongProduct.getId();
        this.categoryName = hongProduct.getHongCategory().getCategoryName();
        this.productName = hongProduct.getProductName();
        this.productCnt = hongProduct.getProductCnt();
        this.productPrice = hongProduct.getProductPrice();
        this.productStock = hongProduct.getProductStock();
    }
}