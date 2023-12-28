package hongshop.hongshop.domain.category.vo;

import hongshop.hongshop.domain.category.HongCategory;
import hongshop.hongshop.domain.product.vo.HongProductVO;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
* @fileName HongCategoryWithProductVO
* @author dahyeon
* @version 1.0.0
* @date 2023-12-28
* @summary  카테고리 정보 & 카테고리에 해당하는 상품 리스트 정보 조회 VO
**/

@Getter @Setter
public class HongCategoryWithProductVO {

    private Long categoryId;
    private Integer orderNum;
    private String categoryName;
    private String description;
    private List<HongProductVO> productList = new ArrayList<>();

    public HongCategoryWithProductVO(HongCategory hongCategory, List<HongProductVO> productVOList) {
        this.categoryId = hongCategory.getId();
        this.orderNum = hongCategory.getOrderNum();
        this.categoryName = hongCategory.getCategoryName();
        this.description = hongCategory.getDescription();
        this.productList = productVOList;
    }
}