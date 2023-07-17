package hongshop.hongshop.domain.answer;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class HongAnswerVO {

    private String content;
    private String deleteYn;

    public HongAnswerVO(HongAnswer hongAnswer){
        this.content = hongAnswer.getContent();
        this.deleteYn = hongAnswer.getDeleteYn();
    }
}
