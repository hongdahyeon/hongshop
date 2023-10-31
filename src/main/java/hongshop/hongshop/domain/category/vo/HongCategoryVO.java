package hongshop.hongshop.domain.category.vo;

import hongshop.hongshop.domain.category.HongCategory;
import hongshop.hongshop.domain.product.vo.HongProductVO;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @fileName HongCategoryVO
 * @author dahyeon
 * @version 1.0.0
 * @date 2023-07-17
 * @summary
 **/

@Getter @Setter
public class HongCategoryVO {

    private Long categoryId;
    private Integer orderNum;
    private String categoryName;
    private String description;
    private List<HongProductVO> productList = new ArrayList<>();

    public HongCategoryVO(HongCategory hongCategory, List<HongProductVO> productVOList) {
        this.categoryId = hongCategory.getId();
        this.orderNum = hongCategory.getOrderNum();
        this.categoryName = hongCategory.getCategoryName();
        this.description = hongCategory.getDescription();
        this.productList = productVOList;
    }

    public HongCategoryVO(HongCategory hongCategory) {
        this.categoryId = hongCategory.getId();
        this.orderNum = hongCategory.getOrderNum();
        this.categoryName = hongCategory.getCategoryName();
        this.description = hongCategory.getDescription();
    }
}
