package hongshop.hongshop.domain.answer.vo;

import hongshop.hongshop.domain.answer.HongAnswer;
import lombok.Getter;
import lombok.Setter;

/**
* @fileName HongAnswerUserVO
* @author dahyeon
* @version 1.0.0
* @date 2023-12-28
* @summary  답변/댓글 with 사용자-Id, 답변-UID VO
**/

@Getter
@Setter
public class HongAnswerUserVO {

    private Long id;
    private String content;
    private String deleteYn;
    private String userId;

    public HongAnswerUserVO(HongAnswer hongAnswer, String userId){
        this.id = hongAnswer.getId();
        this.content = hongAnswer.getContent();
        this.deleteYn = hongAnswer.getDeleteYn();
        this.userId = userId;
    }
}