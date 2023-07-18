package hongshop.hongshop.domain.order;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
* @fileName HongOrderRepository
* @author dahyeon
* @version 1.0.0
* @date 2023-07-18
* @summary  (1) findAllByHongUserId : 로그인한 사용자가 주문한 주문 정보 list로 불러오기
**/

public interface HongOrderRepository extends JpaRepository<HongOrder, Long> {

    List<HongOrder> findAllByHongUserId(Long id);
}
