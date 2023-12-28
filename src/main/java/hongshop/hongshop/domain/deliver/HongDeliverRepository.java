package hongshop.hongshop.domain.deliver;

import org.springframework.data.jpa.repository.JpaRepository;

/**
* @fileName HongDeliverRepository
* @author dahyeon
* @version 1.0.0
* @date 2023-07-19
* @summary  (1) findByHongOrder_Id : 주문-uid로 배송 정보 조회
**/

public interface HongDeliverRepository extends JpaRepository<HongDeliver, Long> {

    HongDeliver findByHongOrder_Id(Long id);
}
