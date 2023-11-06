package hongshop.hongshop.domain.product.vo;

import hongshop.hongshop.domain.fileGroup.vo.HongFileGroupVO;
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
    private String newProductYn;
    private HongFileGroupVO file;

    public HongProductVO(HongProduct hongProduct) {
        this.productId = hongProduct.getId();
        this.categoryName = hongProduct.getHongCategory().getCategoryName();
        this.productName = hongProduct.getProductName();
        this.productCnt = hongProduct.getProductCnt();
        this.productPrice = hongProduct.getProductPrice();
        this.productStock = hongProduct.getProductStock();
        this.newProductYn = hongProduct.getNewProductYn();
    }


    public HongProductVO(HongProduct hongProduct, HongFileGroupVO hongFileGroupVO) {
        this.productId = hongProduct.getId();
        this.categoryName = hongProduct.getHongCategory().getCategoryName();
        this.productName = hongProduct.getProductName();
        this.productCnt = hongProduct.getProductCnt();
        this.productPrice = hongProduct.getProductPrice();
        this.productStock = hongProduct.getProductStock();
        this.newProductYn = hongProduct.getNewProductYn();
        this.file = hongFileGroupVO;
    }
}
