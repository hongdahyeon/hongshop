package hongshop.hongshop.domain.review;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
* @fileName HongReviewRepository
* @author dahyeon
* @version 1.0.0
* @date 2023-11-10
* @summary      (1) findAllByHongUserIdAndDeleteYnIs : USER-id로 리뷰 리스트 조회 (삭제여부)
 *              (2) findByHongUserIdAndHongOrderDetailIdAndDeleteYnIs : USER-id, ORDER-DETAIL-id로 리뷰 조회
 *                      -> 1사람은 1주문 속, 상세 주문건들에 대해 각각 1개의 리뷰만 남길 수 있다.
**/

public interface HongReviewRepository extends JpaRepository<HongReview, Long> {

    List<HongReview> findAllByHongUserIdAndDeleteYnIs(Long id, String deleteYn);
    HongReview findByHongUserIdAndHongOrderDetailIdAndDeleteYnIs(Long userId, Long orderDetailId, String deleteYn);
}
