package hongshop.hongshop.domain.post;

import hongshop.hongshop.domain.answer.HongAnswerVO;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @fileName HongPostVO
 * @author dahyeon
 * @version 1.0.0
 * @date 2023-07-17
 * @summary
 **/

@Getter @Setter
public class HongPostVO {

    private String title;
    private String content;
    private String deleteYn;
    private List<HongAnswerVO> answerVOList = new ArrayList<>();

    public HongPostVO(HongPost hongPost){
        this.title = hongPost.getTitle();
        this.content = hongPost.getContent();
        this.deleteYn = hongPost.getDeleteYn();
    }

    public HongPostVO(HongPost hongPost, List<HongAnswerVO> answerVOList){
        this.title = hongPost.getTitle();
        this.content = hongPost.getContent();
        this.deleteYn = hongPost.getDeleteYn();
        this.answerVOList = answerVOList;
    }
}
