package hongshop.hongshop.domain.order;

import hongshop.hongshop.domain.order.dto.HongOrderFromCartDTO;
import hongshop.hongshop.domain.order.dto.HongOrderFromShopDTO;
import hongshop.hongshop.domain.order.dto.HongOrderStatusDTO;
import hongshop.hongshop.domain.order.vo.HongManagerOrderReviewVO;
import hongshop.hongshop.domain.order.vo.HongOrderDeliverVO;
import hongshop.hongshop.domain.order.vo.HongUserOrderReviewVO;
import hongshop.hongshop.domain.user.HongUser;

import java.util.List;

/**
* @fileName HongOrderService
* @author dahyeon
* @version 1.0.0
* @date 2023-07-18
* @summary
**/

public interface HongOrderService {

    Long saveFromCart(HongOrderFromCartDTO hongOrderDTO);

    Long saveFromShop(HongOrderFromShopDTO hongOrderFromShopDTO);

    List<HongManagerOrderReviewVO> listWithChkReview();

    void updateStatus(Long id, HongOrderStatusDTO hongOrderStatusDTO);

    List<HongOrderDeliverVO> getOrderAndDeliverByUserId(Long id);

    List<HongUserOrderReviewVO> getOrderDetailReviews(Long orderId, HongUser hongUser);
}
