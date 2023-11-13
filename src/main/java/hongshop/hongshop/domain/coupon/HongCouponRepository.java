package hongshop.hongshop.domain.coupon;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HongCouponRepository extends JpaRepository<HongCoupon, Long> {

    List<HongCoupon> findAllByDeleteYnIs(String deleteYn);
}
