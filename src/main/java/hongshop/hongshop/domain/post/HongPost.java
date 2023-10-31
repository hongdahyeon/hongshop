package hongshop.hongshop.domain.post;

import hongshop.hongshop.domain.post.dto.HongPostDTO;
import hongshop.hongshop.domain.postType.HongPostType;
import hongshop.hongshop.global.base.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @fileName HongPost
 * @author dahyeon
 * @version 1.0.0
 * @date 2023-07-17
 * @summary
 **/

@Entity
@Table(name = "hong_post")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 파라미터가 없는 기본 생성자의 경우 -> protected로 접근을 제한
public class HongPost extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hong_post_id")
    private Long id;

    private String title;
    private String content;

    @Column(name = "file_group_id")
    private Long fileGroupId;

    @Column(name = "delete_yn")
    private String deleteYn;

    @Column(name = "read_cnt")
    private Integer readCnt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hong_post_type_id")
    private HongPostType hongPostType;

    @Builder(builderMethodName = "hongPostInsertBuilder")
    public HongPost(String title, String content, Long fileGroupId, HongPostType hongPostType) {
        this.title = title;
        this.content = content;
        this.fileGroupId = fileGroupId;
        this.deleteYn = "N";
        this.readCnt = 0;
        this.hongPostType = hongPostType;
    }

    public void updatePost(HongPostDTO hongPostDTO){
        if(hongPostDTO.getTitle() != null) this.title = hongPostDTO.getTitle();
        if(hongPostDTO.getContent() != null) this.content = hongPostDTO.getContent();
        if(hongPostDTO.getFileGroupId() != null) this.fileGroupId = hongPostDTO.getFileGroupId();
    }

    public void deletePost(){
        this.deleteYn = "Y";
    }

    public void updateReadCnt(){
        this.readCnt = this.readCnt + 1;
    }
}
