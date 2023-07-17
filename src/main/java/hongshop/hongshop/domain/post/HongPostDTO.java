package hongshop.hongshop.domain.post;


import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotNull;

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
}
