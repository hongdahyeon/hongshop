package hongshop.hongshop.domain.answer.dto;

import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotNull;

/**
 * @fileName HongAnswerDTO
 * @author dahyeon
 * @version 1.0.0
 * @date 2023-07-17
 * @summary 답변 DTO (POST, PUT용)
 **/

@Getter @Setter
public class HongAnswerDTO {

    @NotNull
    private String content;
    @NotNull
    private Long hongPostId;
}
