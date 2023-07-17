package hongshop.hongshop.domain.answer;

import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotNull;

/**
 * @fileName HongAnswerDTO
 * @author dahyeon
 * @version 1.0.0
 * @date 2023-07-17
 * @summary
 **/

@Getter @Setter
public class HongAnswerDTO {

    @NotNull
    private String content;
    @NotNull
    private Long hongPostId;
}
