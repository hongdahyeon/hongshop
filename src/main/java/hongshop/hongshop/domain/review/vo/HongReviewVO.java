package hongshop.hongshop.domain.review.vo;

import hongshop.hongshop.domain.fileGroup.vo.HongFileGroupVO;
import hongshop.hongshop.domain.orderDetail.HongOrderDetail;
import hongshop.hongshop.domain.orderDetail.vo.HongOrderDetailVO;
import hongshop.hongshop.domain.product.HongProduct;
import hongshop.hongshop.domain.review.HongReview;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class HongReviewVO {

    private Long reviewId;
    private String reviewContet;
    private Integer reviewStar;
    private List<HongOrderDetailVO> hongOrderDetailVO = new ArrayList<>();
    private HongFileGroupVO file;

    public HongReviewVO(HongReview hongReivew, List<HongOrderDetailVO> hongOrderDetailVO, HongFileGroupVO file) {
        this.reviewId = hongReivew.getId();
        this.reviewContet = hongReivew.getReviewContent();
        this.reviewStar = hongReivew.getReviewStar();
        this.hongOrderDetailVO = hongOrderDetailVO;
        this.file = file;
    }
}
