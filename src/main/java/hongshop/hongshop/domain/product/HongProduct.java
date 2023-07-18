package hongshop.hongshop.domain.product;

import hongshop.hongshop.domain.category.HongCategory;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
* @fileName HongProduct
* @author dahyeon
* @version 1.0.0
* @date 2023-07-18
* @summary
**/

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "hong_product")
@Getter
public class HongProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hong_product_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hong_category_id")
    private HongCategory hongCategory;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_cnt")
    private Integer productCnt;

    @Column(name = "product_price")
    private Integer productPrice;

    @Builder(builderMethodName = "hongPostInsertBuilder")
    public HongProduct(HongCategory hongCategory, String productName, Integer productCnt, Integer productPrice) {
        this.hongCategory = hongCategory;
        this.productName = productName;
        this.productCnt = productCnt;
        this.productPrice = productPrice;
    }

    public void updateProduct(HongProductDTO hongProductDTO){
        if(hongProductDTO.getProductName() != null) this.productName = hongProductDTO.getProductName();
        if(hongProductDTO.getProductCnt() != null) this.productCnt = hongProductDTO.getProductCnt();
        if(hongProductDTO.getProductPrice() != null) this.productPrice = hongProductDTO.getProductPrice();
    }
}
