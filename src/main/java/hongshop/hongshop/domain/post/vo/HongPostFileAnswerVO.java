package hongshop.hongshop.domain.post.vo;

import hongshop.hongshop.domain.answer.vo.HongAnswerUserVO;
import hongshop.hongshop.domain.fileGroup.vo.HongFileGroupVO;
import hongshop.hongshop.domain.post.HongPost;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class HongPostFileAnswerVO {

    private Long id;
    private String type;
    private Long typeId;
    private String title;
    private String content;
    private String deleteYn;
    private Integer readCnt;
    private List<HongAnswerUserVO> answerList = new ArrayList<>();
    private HongFileGroupVO file;
    private Long regId;

    public HongPostFileAnswerVO(HongPost hongPost, HongFileGroupVO file, List<HongAnswerUserVO> answerVOList){
        this.id = hongPost.getId();
        this.type = hongPost.getHongPostType().getPostType().toString();
        this.typeId = hongPost.getHongPostType().getId();
        this.title = hongPost.getTitle();
        this.content = hongPost.getContent();
        this.deleteYn = hongPost.getDeleteYn();
        this.readCnt = hongPost.getReadCnt();
        this.file = file;
        this.answerList = answerVOList;
        this.regId = hongPost.getRegId();
    }
}