package hongshop.hongshop.domain.couponRequest;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
* @fileName HongCouponRequestRepository
* @author dahyeon
* @version 1.0.0
* @date 2023-11-14
* @summary      (1) findAllByDeleteYnAndHongUser_IdAndRequestApproveYn : 전체 조회 -> 삭제여부 N, 사용자-ID, 승인여부 N
 *              (2) findAllByDeleteYnAndHongCoupon_IdAndRequestApproveYn : 전체 조회 -> 삭제여부 N, 쿠폰-ID, 승인여부 N
**/

public interface HongCouponRequestRepository extends JpaRepository<HongCouponRequest, Long> {

    List<HongCouponRequest> findAllByDeleteYnAndHongUser_IdAndRequestApproveYn(String deleteYn, Long userId, String requestApproveYn);

    List<HongCouponRequest> findAllByDeleteYnAndHongCoupon_IdAndRequestApproveYn(String deleteYn, Long couponId, String requestApproveYn);
}
