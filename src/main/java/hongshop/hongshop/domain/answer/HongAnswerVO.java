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

    private Long id;
    private String content;
    private String deleteYn;
    private String userId;

    public HongAnswerVO(HongAnswer hongAnswer){
        this.content = hongAnswer.getContent();
        this.deleteYn = hongAnswer.getDeleteYn();
    }

    public HongAnswerVO(HongAnswer hongAnswer, String userId){
        this.id = hongAnswer.getId();
        this.content = hongAnswer.getContent();
        this.deleteYn = hongAnswer.getDeleteYn();
        this.userId = userId;
    }
}
