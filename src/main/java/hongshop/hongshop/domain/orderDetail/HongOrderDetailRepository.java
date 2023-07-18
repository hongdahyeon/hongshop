package hongshop.hongshop.domain.orderDetail;

import hongshop.hongshop.domain.order.HongOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
* @fileName HongOrderDetailRepository
* @author dahyeon
* @version 1.0.0
* @date 2023-07-18
* @summary  (1) findAllByHongOrderId : order-id 값을 통해 order detail list를 반환한다.
**/

public interface HongOrderDetailRepository extends JpaRepository<HongOrderDetail, Long> {
    List<HongOrderDetail> findAllByHongOrderId(Long id);
}
