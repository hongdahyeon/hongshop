package hongshop.hongshop.domain.couponHas;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HongCouponHasRepository extends JpaRepository<HongCouponHas, Long> {

    List<HongCouponHas> findAllByDeleteYnIs(String deleteYn);

    List<HongCouponHas> findAllByHongUserIdAndDeleteYnIs(Long userId, String deleteYn);
}
