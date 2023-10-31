package hongshop.hongshop.domain.product;

import hongshop.hongshop.domain.category.HongCategory;
import hongshop.hongshop.domain.product.dto.HongProductDTO;
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

    @Column(name = "product_stock")
    private Integer productStock;

    @Column(name = "delete_yn")
    private String deleteYn;

    @Builder(builderMethodName = "hongProductInsertBuilder")
    public HongProduct(HongCategory hongCategory, String productName, Integer productCnt, Integer productPrice, Integer productStock) {
        this.hongCategory = hongCategory;
        this.productName = productName;
        this.productCnt = productCnt;
        this.productPrice = productPrice;
        this.productStock = productStock;
        this.deleteYn = "N";
    }

    public void updateProduct(HongProductDTO hongProductDTO){
        if(hongProductDTO.getProductName() != null) this.productName = hongProductDTO.getProductName();
        if(hongProductDTO.getProductCnt() != null) {
            this.productCnt = hongProductDTO.getProductCnt();
            this.productStock = this.productStock + (hongProductDTO.getProductCnt() - hongProductDTO.getOriginProductCnt());
        }
        if(hongProductDTO.getProductPrice() != null) this.productPrice = hongProductDTO.getProductPrice();
    }

    public void updateStockCnt(Integer orderCnt){
        this.productStock = this.productStock - orderCnt;
    }

    public void deleteProduct(){
        this.deleteYn = "Y";
    }
}
