package hongshop.hongshop.domain.cart.vo;

import hongshop.hongshop.domain.cart.HongCart;
import hongshop.hongshop.domain.fileGroup.vo.HongFileGroupVO;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class HongCartVO {

    private Long id;
    private Long productId;
    private String productName;
    private Integer productStock;
    private Integer productPrice;
    private Integer cartCnt;
    private Integer cartPrice;
    private HongFileGroupVO fileGroup;

    public HongCartVO(HongCart hongCart) {
        this.id = hongCart.getId();
        this.productId = hongCart.getHongProduct().getId();
        this.productStock = hongCart.getHongProduct().getProductStock();
        this.productName = hongCart.getHongProduct().getProductName();
        this.productPrice = hongCart.getHongProduct().getProductPrice();
        this.cartCnt = hongCart.getCartCnt();
        this.cartPrice = hongCart.getCartPrice();
    }

    public HongCartVO(HongCart hongCart, HongFileGroupVO fileGroupVO) {
        this.id = hongCart.getId();
        this.productId = hongCart.getHongProduct().getId();
        this.productStock = hongCart.getHongProduct().getProductStock();
        this.productName = hongCart.getHongProduct().getProductName();
        this.productPrice = hongCart.getHongProduct().getProductPrice();
        this.cartCnt = hongCart.getCartCnt();
        this.cartPrice = hongCart.getCartPrice();
        this.fileGroup = fileGroupVO;
    }

}
