package hongshop.hongshop.domain.cart;

import hongshop.hongshop.domain.product.HongProduct;
import hongshop.hongshop.domain.user.HongUser;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
* @fileName HongCart
* @author dahyeon
* @version 1.0.0
* @date 2023-07-18
* @summary
**/

@Entity
@Table(name = "hong_cart")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HongCart {

    @Id
    @GeneratedValue
    @Column(name = "hong_card_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hong_user_id")
    private HongUser hongUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hong_product_id")
    private HongProduct hongProduct;

    @Column(name = "cart_cnt")
    private Integer cartCnt;

    @Column(name = "cart_price")
    private Integer cartPrice;

    @Column(name = "delete_yn")
    private String deleteYn;

    @Builder(builderMethodName = "hongCartInsertBuilder")
    public HongCart(HongUser hongUser, HongProduct hongProduct, Integer cartCnt, Integer cartPrice) {
        this.hongUser = hongUser;
        this.hongProduct = hongProduct;
        this.cartCnt = cartCnt;
        this.cartPrice = cartPrice;
        this.deleteYn = "N";
    }

    public void deleteCart(){
        this.deleteYn = "Y";
    }

    public void updateCntAndPrice(Integer cartCnt, Integer cartPrice){
        this.cartCnt = cartCnt;
        this.cartPrice = cartPrice;
    }
}
