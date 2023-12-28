package hongshop.hongshop.domain.answer.vo;

import hongshop.hongshop.domain.answer.HongAnswer;
import lombok.Getter;
import lombok.Setter;

/**
 * @fileName HongAnswerVO
 * @author dahyeon
 * @version 1.0.0
 * @date 2023-07-17
 * @summary  답변/댓글 기본적인 VO
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
