package hongshop.hongshop.domain.answer;

import lombok.Getter;
import lombok.Setter;

/**
 * @fileName HongAnswerVO
 * @author dahyeon
 * @version 1.0.0
 * @date 2023-07-17
 * @summary
 **/

@Getter @Setter
public class HongAnswerVO {

    private String content;
    private String deleteYn;

    public HongAnswerVO(HongAnswer hongAnswer){
        this.content = hongAnswer.getContent();
        this.deleteYn = hongAnswer.getDeleteYn();
    }
}
