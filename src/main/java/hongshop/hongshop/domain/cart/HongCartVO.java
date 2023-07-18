package hongshop.hongshop.domain.cart;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class HongCartVO {

    private String productName;
    private Integer cartCnt;
    private Integer cartPrice;

    public HongCartVO(HongCart hongCart) {
        this.productName = hongCart.getHongProduct().getProductName();
        this.cartCnt = hongCart.getCartCnt();
        this.cartPrice = hongCart.getCartPrice();
    }
}
