package hongshop.hongshop.domain.review;

import hongshop.hongshop.domain.review.dto.HongReviewDTO;
import hongshop.hongshop.domain.review.vo.HongReviewVO;
import hongshop.hongshop.domain.user.HongUser;

import java.util.List;

public interface HongReviewService {

    Long join(HongReviewDTO hongReviewDTO, HongUser hongUser);

    List<HongReviewVO> userReview(HongUser hongUser);
}
