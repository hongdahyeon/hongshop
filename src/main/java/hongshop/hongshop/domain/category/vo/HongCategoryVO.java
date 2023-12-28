package hongshop.hongshop.domain.category.vo;

import hongshop.hongshop.domain.category.HongCategory;
import lombok.Getter;
import lombok.Setter;

/**
 * @fileName HongCategoryVO
 * @author dahyeon
 * @version 1.0.0
 * @date 2023-07-17
 * @summary 카테고리 정보 조회 VO
 **/

@Getter @Setter
public class HongCategoryVO {

    private Long categoryId;
    private Integer orderNum;
    private String categoryName;
    private String description;

    public HongCategoryVO(HongCategory hongCategory) {
        this.categoryId = hongCategory.getId();
        this.orderNum = hongCategory.getOrderNum();
        this.categoryName = hongCategory.getCategoryName();
        this.description = hongCategory.getDescription();
    }
}
