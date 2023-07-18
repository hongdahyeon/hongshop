package hongshop.hongshop.domain.category;

import hongshop.hongshop.domain.product.HongProductVO;
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

    private String categoryName;
    private String description;
    private List<HongProductVO> productList = new ArrayList<>();

    public HongCategoryVO(HongCategory hongCategory, List<HongProductVO> productVOList) {
        this.categoryName = hongCategory.getCategoryName();
        this.description = hongCategory.getDescription();
        this.productList = productVOList;
    }

    public HongCategoryVO(HongCategory hongCategory) {
        this.categoryName = hongCategory.getCategoryName();
        this.description = hongCategory.getDescription();
    }
}
