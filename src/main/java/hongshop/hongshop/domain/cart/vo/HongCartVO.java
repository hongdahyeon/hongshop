package hongshop.hongshop.domain.cart.vo;

import hongshop.hongshop.domain.cart.HongCart;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class HongCartVO {

    private Long id;
    private String productName;
    private Integer cartCnt;
    private Integer cartPrice;

    public HongCartVO(HongCart hongCart) {
        this.id = hongCart.getId();
        this.productName = hongCart.getHongProduct().getProductName();
        this.cartCnt = hongCart.getCartCnt();
        this.cartPrice = hongCart.getCartPrice();
    }
}
