package hongshop.hongshop.domain.post.vo;

import hongshop.hongshop.domain.answer.vo.HongAnswerVO;
import hongshop.hongshop.domain.fileGroup.vo.HongFileGroupVO;
import hongshop.hongshop.domain.post.HongPost;
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

    private Long id;
    private String type;
    private Long typeId;
    private String title;
    private String content;
    private String deleteYn;
    private Integer readCnt;
    private List<HongAnswerVO> answerList = new ArrayList<>();
    private HongFileGroupVO file;
    private Long regId;

    public HongPostVO(HongPost hongPost){
        this.id = hongPost.getId();
        this.type = hongPost.getHongPostType().getPostType().toString();
        this.typeId = hongPost.getHongPostType().getId();
        this.title = hongPost.getTitle();
        this.content = hongPost.getContent();
        this.deleteYn = hongPost.getDeleteYn();
        this.readCnt = hongPost.getReadCnt();
        this.regId = hongPost.getRegId();
    }

    public HongPostVO(HongPost hongPost, List<HongAnswerVO> answerVOList){
        this.id = hongPost.getId();
        this.type = hongPost.getHongPostType().getPostType().toString();
        this.typeId = hongPost.getHongPostType().getId();
        this.title = hongPost.getTitle();
        this.content = hongPost.getContent();
        this.deleteYn = hongPost.getDeleteYn();
        this.readCnt = hongPost.getReadCnt();
        this.answerList = answerVOList;
        this.regId = hongPost.getRegId();
    }

    public HongPostVO(HongPost hongPost, HongFileGroupVO file){
        this.id = hongPost.getId();
        this.type = hongPost.getHongPostType().getPostType().toString();
        this.typeId = hongPost.getHongPostType().getId();
        this.title = hongPost.getTitle();
        this.content = hongPost.getContent();
        this.deleteYn = hongPost.getDeleteYn();
        this.readCnt = hongPost.getReadCnt();
        this.file = file;
        this.regId = hongPost.getRegId();
    }

    public HongPostVO(HongPost hongPost, HongFileGroupVO file, List<HongAnswerVO> answerVOList){
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
