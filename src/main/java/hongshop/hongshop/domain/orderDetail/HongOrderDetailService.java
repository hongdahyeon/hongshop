package hongshop.hongshop.domain.orderDetail;

import hongshop.hongshop.domain.order.HongOrder;
import hongshop.hongshop.domain.orderDetail.vo.HongOrderDetailUserVO;
import hongshop.hongshop.domain.orderDetail.vo.HongOrderDetailVO;
import hongshop.hongshop.domain.product.HongProduct;

import java.util.List;

/**
* @fileName HongOrderDetailService
* @author dahyeon
* @version 1.0.0
* @date 2023-07-18
* @summary
**/

public interface HongOrderDetailService {

    Long saveOrderDetails(HongOrder hongOrder, HongProduct hongProduct, Integer orderCnt, Integer orderPrice);

    List<HongOrderDetailVO> listOfDetailOrders(Long orderId);

    List<HongOrderDetailUserVO> listByProductId(Long productId);

    boolean emptyChkByProductId(Long productId);
}
