package hongshop.hongshop.domain.postType.vo;

import hongshop.hongshop.domain.post.vo.HongPostVO;
import hongshop.hongshop.domain.postType.HongPostType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
* @fileName HongPostTypeVO
* @author dahyeon
* @version 1.0.0
* @date 2023-08-08
* @summary
**/

@Getter @Setter
@ToString
public class HongPostTypeVO {

    public Long postTypeId;
    public String postType;
    public String postName;
    public Integer orderNum;
    public String useAt;
    public String postUrl;
    public List<HongPostVO> hongPost = new ArrayList<>();

    public HongPostTypeVO(HongPostType hongPostType){
        this.postTypeId = hongPostType.getId();
        this.postType = hongPostType.getPostType().toString();
        this.postName = hongPostType.getPostName();
        this.orderNum = hongPostType.getOrderNum();
        this.useAt= hongPostType.getUseAt();
        this.postUrl = hongPostType.getPostUrl();
    }

    public HongPostTypeVO(HongPostType hongPostType, List<HongPostVO> hongPost){
        this.postTypeId = hongPostType.getId();
        this.postType = hongPostType.getPostType().toString();
        this.postName = hongPostType.getPostName();
        this.orderNum = hongPostType.getOrderNum();
        this.useAt= hongPostType.getUseAt();
        this.hongPost = hongPost;
        this.postUrl = hongPostType.getPostUrl();
    }
}
