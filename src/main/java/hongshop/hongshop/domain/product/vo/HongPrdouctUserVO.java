package hongshop.hongshop.domain.product.vo;

import hongshop.hongshop.domain.orderDetail.vo.HongOrderDetailUserVO;
import hongshop.hongshop.domain.product.HongProduct;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


/**
 * @fileName HongPrdouctUserVO
 * @author dahyeon
 * @version 1.0.0
 * @date 2023-10-30
 * @summary 각 상품에 대해 주문 사용자 정보 list를 위한 VO
 **/

@Getter
@Setter
public class HongPrdouctUserVO {

    private Long productId;
    private String categoryName;
    private String productName;
    private Integer productCnt;
    private Integer productPrice;
    private Integer productStock;
    private List<HongOrderDetailUserVO> orderDetails;

    public HongPrdouctUserVO(HongProduct hongProduct, List<HongOrderDetailUserVO> orderDetails) {
        this.productId = hongProduct.getId();
        this.categoryName = hongProduct.getHongCategory().getCategoryName();
        this.productName = hongProduct.getProductName();
        this.productCnt = hongProduct.getProductCnt();
        this.productPrice = hongProduct.getProductPrice();
        this.productStock = hongProduct.getProductStock();
        this.orderDetails = orderDetails;
    }
}