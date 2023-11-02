package hongshop.hongshop.domain.postType;

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

    @Builder(builderMethodName = "insertPostTypeBuilder")
    public HongPostType(PostType postType, String postName) {
        this.postType = postType;
        this.postName = postName;
        this.useAt = "Y";
        this.deleteAt = "N";
    }
}
