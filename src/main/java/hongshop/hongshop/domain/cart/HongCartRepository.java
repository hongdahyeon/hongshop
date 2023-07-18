package hongshop.hongshop.domain.cart;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
* @fileName HongCartRepository
* @author dahyeon
* @version 1.0.0
* @date 2023-07-18
* @summary  (1) findAllByHongUserId : 로그인한 사용자의 장바구니 정보 리스트로 불러오기
**/

public interface HongCartRepository extends JpaRepository<HongCart, Long> {

    List<HongCart> findAllByHongUserId(Long id);
}
