package hongshop.hongshop.domain.coupon;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
* @fileName HongCouponRepository
* @author dahyeon
* @version 1.0.0
* @date 2023-12-28
* @summary     (1) findAllByDeleteYnIs : 쿠폰 리스트 조회 (삭제여부 N)
 *             (2) findAllByDeleteYnAndUseAt : 쿠폰 리스트 조회 (삭제여부 N, 사용여부 Y)
**/


public interface HongCouponRepository extends JpaRepository<HongCoupon, Long> {

    List<HongCoupon> findAllByDeleteYnIs(String deleteYn);

    List<HongCoupon> findAllByDeleteYnAndUseAt(String deleteYn, String useAt);
}
