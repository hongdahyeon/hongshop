package hongshop.hongshop.domain.review;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
* @fileName HongReviewRepository
* @author dahyeon
* @version 1.0.0
* @date 2023-11-10
* @summary      (1) findAllByHongUserIdAndDeleteYnIs : USER-id로 리뷰 리스트 조회 (삭제여부)
 *              (2) findAllByHongUserIdAndAndHongOrderIdAndDeleteYnIs : USER-id, ORDER-id로 리뷰 조회
 *                      -> 1사람은 1주문에 대해 1리뷰만 남길 수 있음
**/

public interface HongReviewRepository extends JpaRepository<HongReview, Long> {

    List<HongReview> findAllByHongUserIdAndDeleteYnIs(Long id, String deleteYn);

    List<HongReview> findAllByHongUserIdAndAndHongOrderIdAndDeleteYnIs(Long userId, Long orderId, String deleteYn);
}
