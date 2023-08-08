package hongshop.hongshop.domain.postType;

import hongshop.hongshop.domain.post.HongPostVO;
import lombok.Getter;
import lombok.Setter;

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
public class HongPostTypeVO {

    public String postType;
    public String postName;
    public List<HongPostVO> hongPost = new ArrayList<>();

    public HongPostTypeVO(HongPostType hongPostType){
        this.postType = hongPostType.getPostType().toString();
        this.postName = hongPostType.getPostName();
    }

    public HongPostTypeVO(HongPostType hongPostType, List<HongPostVO> hongPost){
        this.postType = hongPostType.getPostType().toString();
        this.postName = hongPostType.getPostName();
        this.hongPost = hongPost;
    }
}