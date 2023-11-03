package hongshop.hongshop.domain.postType.dto;

import hongshop.hongshop.domain.postType.PostType;
import lombok.Getter;
import lombok.Setter;

/**
* @fileName HongPostTypeDTO
* @author dahyeon
* @version 1.0.0
* @date 2023-08-08
* @summary
**/

@Getter @Setter
public class HongPostTypeDTO {
    public PostType postType;
    public String postName;
    public String useAt;
    public Integer orderNum;
}
