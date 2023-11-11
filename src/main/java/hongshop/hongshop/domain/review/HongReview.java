package hongshop.hongshop.domain.review;

import hongshop.hongshop.domain.order.HongOrder;
import hongshop.hongshop.domain.orderDetail.HongOrderDetail;
import hongshop.hongshop.domain.user.HongUser;
import hongshop.hongshop.global.base.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "hong_review")
@Getter
public class HongReview extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hong_review_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hong_order_detail_id")
    private HongOrderDetail hongOrderDetail;

    @Column(name = "review_content")
    private String reviewContent;

    @Column(name = "review_star")
    private Integer reviewStar;

    @Column(name = "file_group_id")
    private Long fileGroupId;

    @Column(name = "delete_yn")
    private String deleteYn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hong_user_id")
    private HongUser hongUser;

    @Builder(builderMethodName = "hongReviewInsertBuilder")
    public HongReview(HongOrderDetail hongOrderDetail, HongUser hongUser, String reviewContent, Integer reviewStar, Long fileGroupId) {
        this.hongOrderDetail = hongOrderDetail;
        this.hongUser = hongUser;
        this.reviewContent = reviewContent;
        this.reviewStar = reviewStar;
        this.fileGroupId = fileGroupId;
        this.deleteYn = "N";
    }
}
