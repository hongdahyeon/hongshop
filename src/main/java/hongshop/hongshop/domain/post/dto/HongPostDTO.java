package hongshop.hongshop.domain.post.dto;


import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * @fileName HongPostDTO
 * @author dahyeon
 * @version 1.0.0
 * @date 2023-07-17
 * @summary
 **/

@Getter @Setter
public class HongPostDTO {

    @NotNull
    private String title;
    @NotNull
    private String content;
    private Long fileGroupId;

    private Long hongPostTypeId;

    private List<Long> deleteFile = new ArrayList<>();
}
