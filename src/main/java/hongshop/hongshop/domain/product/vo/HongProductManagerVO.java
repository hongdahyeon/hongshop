package hongshop.hongshop.domain.product.vo;

import hongshop.hongshop.domain.fileGroup.vo.HongFileGroupVO;
import hongshop.hongshop.domain.product.HongProduct;
import lombok.Getter;
import lombok.Setter;

/**
* @fileName HongProductManagerVO
* @author dahyeon
* @version 1.0.0
* @date 2023-11-10
* @summary 상품 조회시, file과 함께 해당 상품을 구매한 사용자가 있는지 여부 체크를 위한 VO
 *          -> 주문 상태 : CHARGED, DELIVER_ING인 경우를 찾아서 해당 주문건이 있다면 orderUserEmpty IS false
 *              => if(!orderUserEmpty) delete button is disabled
**/

@Getter @Setter
public class HongProductManagerVO {

    private Long productId;
    private String categoryName;
    private String productName;
    private Integer productCnt;
    private Integer productPrice;
    private Integer productStock;
    private String newProductYn;
    private HongFileGroupVO file;
    private boolean orderUserEmpty;

    public HongProductManagerVO(HongProduct hongProduct, boolean orderUserEmpty) {
        this.productId = hongProduct.getId();
        this.categoryName = hongProduct.getHongCategory().getCategoryName();
        this.productName = hongProduct.getProductName();
        this.productCnt = hongProduct.getProductCnt();
        this.productPrice = hongProduct.getProductPrice();
        this.productStock = hongProduct.getProductStock();
        this.newProductYn = hongProduct.getNewProductYn();
        this.orderUserEmpty = orderUserEmpty;
    }

    public HongProductManagerVO(HongProduct hongProduct, HongFileGroupVO hongFileGroupVO, boolean orderUserEmpty) {
        this.productId = hongProduct.getId();
        this.categoryName = hongProduct.getHongCategory().getCategoryName();
        this.productName = hongProduct.getProductName();
        this.productCnt = hongProduct.getProductCnt();
        this.productPrice = hongProduct.getProductPrice();
        this.productStock = hongProduct.getProductStock();
        this.newProductYn = hongProduct.getNewProductYn();
        this.file = hongFileGroupVO;
        this.orderUserEmpty = orderUserEmpty;
    }
}
