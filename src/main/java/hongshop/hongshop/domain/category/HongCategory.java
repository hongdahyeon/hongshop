package hongshop.hongshop.domain.category;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @fileName HongCategory
 * @author dahyeon
 * @version 1.0.0
 * @date 2023-07-17
 * @summary
 **/

@Entity
@Getter
@Table(name = "hong_category")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HongCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hong_category_id")
    private Long id;

    @Column(name = "category_name")
    private String categoryName;

    private String description;

    @Column(name = "delete_yn")
    private String deleteYn;

    @Column(name = "order_num")
    private Integer orderNum;

    @Builder(builderMethodName = "hongCategoryInsertBuilder")
    public HongCategory(String categoryName, String description, Integer orderNum) {
        this.categoryName = categoryName;
        this.description = description;
        this.deleteYn = "N";
        this.orderNum = orderNum;
    }

    public void updateCategory(HongCategoryDTO hongCategoryDTO){
        if(hongCategoryDTO.getCategoryName() != null) this.categoryName = hongCategoryDTO.getCategoryName();
        if(hongCategoryDTO.getDescription() != null) this.description = hongCategoryDTO.getDescription();
        if(hongCategoryDTO.getOrderNum() != null) this.orderNum = hongCategoryDTO.getOrderNum();
    }

    public void deleteCategory(){
        this.deleteYn = "Y";
    }
}
