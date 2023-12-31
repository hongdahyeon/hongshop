package hongshop.hongshop.domain.postType.vo;

import hongshop.hongshop.domain.postType.HongPostType;
import lombok.Getter;
import lombok.Setter;

/**
* @fileName HongPostTypeVO
 * @author dahyeon
* @version 1.0.0
* @date 2023-08-08
* @summary 게시글 타입 VO
**/

@Getter @Setter
public class HongPostTypeVO {

    public Long postTypeId;
    public String postType;
    public String postName;
    public Integer orderNum;
    public String useAt;
    public String postUrl;

    public HongPostTypeVO(HongPostType hongPostType){
        this.postTypeId = hongPostType.getId();
        this.postType = hongPostType.getPostType().toString();
        this.postName = hongPostType.getPostName();
        this.orderNum = hongPostType.getOrderNum();
        this.useAt= hongPostType.getUseAt();
        this.postUrl = hongPostType.getPostUrl();
    }
}
