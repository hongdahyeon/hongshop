package hongshop.hongshop.domain.postType.vo;


import hongshop.hongshop.domain.post.vo.HongPostVO;
import hongshop.hongshop.domain.postType.HongPostType;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
* @fileName HongPostTypeWithHongPost
* @author dahyeon
* @version 1.0.0
* @date 2023-12-28
* @summary  게시글 타입 with 해당 게시글 타입 리스트 vo
**/

@Getter
@Setter
public class HongPostTypeWithHongPost {

    public Long postTypeId;
    public String postType;
    public String postName;
    public Integer orderNum;
    public String useAt;
    public String postUrl;
    public List<HongPostVO> hongPost = new ArrayList<>();

    public HongPostTypeWithHongPost(HongPostType hongPostType, List<HongPostVO> hongPost){
        this.postTypeId = hongPostType.getId();
        this.postType = hongPostType.getPostType().toString();
        this.postName = hongPostType.getPostName();
        this.orderNum = hongPostType.getOrderNum();
        this.useAt= hongPostType.getUseAt();
        this.hongPost = hongPost;
        this.postUrl = hongPostType.getPostUrl();
    }
}
