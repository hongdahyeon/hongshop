package hongshop.hongshop.domain.couponHist;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
* @fileName HongCouponHistRepository
* @author dahyeon
* @version 1.0.0
* @date 2023-11-14
* @summary  HIST 리스트를 가져오는데, 엮여있는 CouponHas의 CouponId값으로 가져온다.
**/

public interface HongCouponHistRepository extends JpaRepository<HongCouponHist, Long> {

    List<HongCouponHist> findAllByHongCouponHas_HongCoupon_Id(Long hongCouponId);
}
