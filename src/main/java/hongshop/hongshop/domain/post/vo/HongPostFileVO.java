package hongshop.hongshop.domain.post.vo;

import hongshop.hongshop.domain.fileGroup.vo.HongFileGroupVO;
import hongshop.hongshop.domain.post.HongPost;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HongPostFileVO {

    private Long id;
    private String type;
    private Long typeId;
    private String title;
    private String content;
    private String deleteYn;
    private Integer readCnt;
    private HongFileGroupVO file;
    private Long regId;

    public HongPostFileVO(HongPost hongPost, HongFileGroupVO file){
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
}