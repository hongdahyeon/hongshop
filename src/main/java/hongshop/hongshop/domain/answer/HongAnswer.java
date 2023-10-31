package hongshop.hongshop.domain.answer;

import hongshop.hongshop.domain.answer.dto.HongAnswerDTO;
import hongshop.hongshop.domain.post.HongPost;
import hongshop.hongshop.global.base.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @fileName HongAnswer
 * @author dahyeon
 * @version 1.0.0
 * @date 2023-07-17
 * @summary
 **/

@Entity
@Table(name = "hong_answer")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HongAnswer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hong_answer_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hong_post_id")
    private HongPost hongPost;

    private String content;

    @Column(name = "delete_yn")
    private String deleteYn;

    @Builder(builderMethodName = "hongAnswerInsertBuilder")
    public HongAnswer(HongPost hongPost, String content) {
        this.hongPost = hongPost;
        this.content = content;
        this.deleteYn = "N";
    }

    public void updateAnswer(HongAnswerDTO hongAnswerDTO){
        if(hongAnswerDTO.getContent() != null) this.content = hongAnswerDTO.getContent();
    }

    public void deleteAnswer(){
        this.deleteYn = "Y";
    }
}
