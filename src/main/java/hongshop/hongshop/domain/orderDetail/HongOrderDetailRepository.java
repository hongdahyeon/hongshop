package hongshop.hongshop.domain.orderDetail;

import hongshop.hongshop.domain.order.HongOrder;
import hongshop.hongshop.domain.order.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
* @fileName HongOrderDetailRepository
* @author dahyeon
* @version 1.0.0
* @date 2023-07-18
* @summary  (1) findAllByHongOrderId : order-id 값을 통해 order detail list를 반환한다.
 *          (2) findAllByHongProduct_Id : product-id를 통해 order detail list를 반환한다.
 *          (3) findAllByHongProduct_IdAndHongOrder_OrderStatusIn : product-id & order-status를 통한 order detail list를 반환한다.
**/

public interface HongOrderDetailRepository extends JpaRepository<HongOrderDetail, Long> {
    List<HongOrderDetail> findAllByHongOrderId(Long id);

    List<HongOrderDetail> findAllByHongProduct_Id(Long productId);

    List<HongOrderDetail> findAllByHongProduct_IdAndHongOrder_OrderStatusIn(Long productId, List<OrderStatus> orderStatusList);
}
