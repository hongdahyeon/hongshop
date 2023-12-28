package hongshop.hongshop.domain.category.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * @fileName HongCategoryDTO
 * @author dahyeon
 * @version 1.0.0
 * @date 2023-07-17
 * @summary 카테고리 DTO (POST, PUT용)
 **/

@Getter @Setter
public class HongCategoryDTO {

    @NotNull
    private String categoryName;
    private String description;

    private Integer orderNum;
}
