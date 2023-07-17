package hongshop.hongshop.domain.category;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * @fileName HongCategoryDTO
 * @author dahyeon
 * @version 1.0.0
 * @date 2023-07-17
 * @summary
 **/

@Getter @Setter
public class HongCategoryDTO {

    @NotNull
    private String categoryName;
    private String description;
}