package hongshop.hongshop.domain.post;

import hongshop.hongshop.domain.answer.HongAnswerVO;
import hongshop.hongshop.domain.fileGroup.HongFileGroupVO;
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
    private Integer readCnt;
    private List<HongAnswerVO> answerList = new ArrayList<>();
    private HongFileGroupVO file;

    public HongPostVO(HongPost hongPost){
        this.title = hongPost.getTitle();
        this.content = hongPost.getContent();
        this.deleteYn = hongPost.getDeleteYn();
        this.readCnt = hongPost.getReadCnt();
    }

    public HongPostVO(HongPost hongPost, List<HongAnswerVO> answerVOList){
        this.title = hongPost.getTitle();
        this.content = hongPost.getContent();
        this.deleteYn = hongPost.getDeleteYn();
        this.readCnt = hongPost.getReadCnt();
        this.answerList = answerVOList;
    }

    public HongPostVO(HongPost hongPost, HongFileGroupVO file){
        this.title = hongPost.getTitle();
        this.content = hongPost.getContent();
        this.deleteYn = hongPost.getDeleteYn();
        this.readCnt = hongPost.getReadCnt();
        this.file = file;
    }
}
