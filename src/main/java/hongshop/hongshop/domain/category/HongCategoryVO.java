package hongshop.hongshop.domain.category;

import lombok.Getter;
import lombok.Setter;

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

    public HongCategoryVO(HongCategory hongCategory) {
        this.categoryName = hongCategory.getCategoryName();
        this.description = hongCategory.getDescription();
    }
}
