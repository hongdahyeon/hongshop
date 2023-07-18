package hongshop.hongshop.domain.product;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class HongProductVO {

    private String categoryName;
    private String productName;
    private Integer productCnt;
    private Integer productPrice;

    public HongProductVO(HongProduct hongProduct) {
        this.categoryName = hongProduct.getHongCategory().getCategoryName();
        this.productName = hongProduct.getProductName();
        this.productCnt = hongProduct.getProductCnt();
        this.productPrice = hongProduct.getProductPrice();
    }
}
