package hongshop.hongshop.domain.post;

import lombok.Getter;
import lombok.Setter;

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

    public HongPostVO(HongPost hongPost){
        this.title = hongPost.getTitle();
        this.content = hongPost.getContent();
        this.deleteYn = hongPost.getDeleteYn();
    }
}
