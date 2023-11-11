package hongshop.hongshop.domain.review.vo;

import hongshop.hongshop.domain.fileGroup.vo.HongFileGroupVO;
import hongshop.hongshop.domain.orderDetail.vo.HongOrderDetailVO;
import hongshop.hongshop.domain.review.HongReview;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class HongReviewVO {

    private Long reviewId;
    private String reviewContet;
    private Integer reviewStar;
    private HongOrderDetailVO hongOrderDetailVO;        // 1 order-detail-vo has 1-review-vo
    private HongFileGroupVO file;

    public HongReviewVO(HongReview hongReivew, HongOrderDetailVO hongOrderDetailVO, HongFileGroupVO file) {
        this.reviewId = hongReivew.getId();
        this.reviewContet = hongReivew.getReviewContent();
        this.reviewStar = hongReivew.getReviewStar();
        this.hongOrderDetailVO = hongOrderDetailVO;
        this.file = file;
    }

    public HongReviewVO(HongReview hongReivew, HongOrderDetailVO hongOrderDetailVO) {
        this.reviewId = hongReivew.getId();
        this.reviewContet = hongReivew.getReviewContent();
        this.reviewStar = hongReivew.getReviewStar();
        this.hongOrderDetailVO = hongOrderDetailVO;
    }
}
