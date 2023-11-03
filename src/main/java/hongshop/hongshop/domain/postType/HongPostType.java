package hongshop.hongshop.domain.postType;

import hongshop.hongshop.domain.postType.dto.HongPostTypeDTO;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
* @fileName HongPostType
* @author dahyeon
* @version 1.0.0
* @date 2023-08-08
* @summary
**/

@Entity
@Table(name = "hong_post_type")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HongPostType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hong_post_type_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "post_type")
    private PostType postType;

    @Column(name = "post_name")
    private String postName;

    @Column(name = "use_at")
    private String useAt;

    @Column(name = "delete_at")
    private String deleteAt;

    @Column(name = "post_url")
    private String postUrl;

    @Column(name = "order_num")
    private Integer orderNum;

    @Builder(builderMethodName = "insertPostTypeBuilder")
    public HongPostType(PostType postType, String postName, String useAt, Integer orderNum) {
        this.postType = postType;
        this.postName = postName;
        this.useAt = useAt;
        this.deleteAt = "N";
        this.orderNum = orderNum;
    }

    public void updatePostUrl(String postUrl){
        this.postUrl = postUrl;
    }

    public void updatePostType(HongPostTypeDTO hongPostTypeDTO, Long id){
        if(hongPostTypeDTO.getPostType() != null) this.postType = hongPostTypeDTO.getPostType();
        if(hongPostTypeDTO.getPostName() != null) this.postName = hongPostTypeDTO.getPostName();
        if(hongPostTypeDTO.getUseAt() != null) {
            this.useAt = hongPostTypeDTO.getUseAt();
            if("Y".equals(hongPostTypeDTO.getUseAt())){         // 사용여부가 'Y'라면 해당 게시판 URL 생성해주기
                this.postUrl = "/bbs/" + id;
            }
        }
        if(hongPostTypeDTO.getOrderNum() != null) this.orderNum  = hongPostTypeDTO.getOrderNum();
    }

    public void deletePostType(){
        this.deleteAt = "Y";
    }
}
