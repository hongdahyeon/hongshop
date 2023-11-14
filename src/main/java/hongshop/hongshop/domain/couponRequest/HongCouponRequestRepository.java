package hongshop.hongshop.domain.couponRequest;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
* @fileName HongCouponRequestRepository
* @author dahyeon
* @version 1.0.0
* @date 2023-11-14
* @summary      (1) findAllByDeleteYnIs : 전체 조회 -> 삭제여부 N
 *              (2) findAllByDeleteYnAndHongUser_Id : 전체 조회 -> 삭제여부 N, 사용자-ID
 *              (3) findAllByDeleteYnAndHongCoupon_Id : 전체 조회 -> 삭제여부 N, 쿠폰-ID
**/

public interface HongCouponRequestRepository extends JpaRepository<HongCouponRequest, Long> {

    List<HongCouponRequest> findAllByDeleteYnIs(String deleteYn);

    List<HongCouponRequest> findAllByDeleteYnAndHongUser_IdAndRequestApproveYn(String deleteYn, Long userId, String requestApproveYn);

    List<HongCouponRequest> findAllByDeleteYnAndHongCoupon_IdAndRequestApproveYn(String deleteYn, Long couponId, String requestApproveYn);
}
