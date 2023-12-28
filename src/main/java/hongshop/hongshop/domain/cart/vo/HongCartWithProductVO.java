package hongshop.hongshop.domain.cart.vo;

import hongshop.hongshop.domain.cart.HongCart;
import hongshop.hongshop.domain.fileGroup.vo.HongFileGroupVO;
import lombok.Getter;
import lombok.Setter;

/**
* @fileName HongCartWithProductVO
* @author dahyeon
* @version 1.0.0
* @date 2023-12-28
* @summary  장바구니 with 상품 정보 VO
 *          -> 상품 정보의 경우 파일 정보도 함께 조회 (파일 정보: 삭제여부 N, 상태 SAVED)
 *          -> 이유: 단독으로 장바구니만 조회해올 일이 없음
**/

@Getter @Setter
public class HongCartWithProductVO {

    private Long id;
    private Integer cartCnt;
    private Integer cartPrice;
    private Long productId;
    private String productName;
    private Integer productStock;
    private Integer productPrice;
    private HongFileGroupVO fileGroup;

    public HongCartWithProductVO(HongCart hongCart, HongFileGroupVO fileGroupVO) {
        this.id = hongCart.getId();
        this.cartCnt = hongCart.getCartCnt();
        this.cartPrice = hongCart.getCartPrice();
        this.productId = hongCart.getHongProduct().getId();
        this.productName = hongCart.getHongProduct().getProductName();
        this.productStock = hongCart.getHongProduct().getProductStock();
        this.productPrice = hongCart.getHongProduct().getProductPrice();
        this.fileGroup = fileGroupVO;
    }
}
