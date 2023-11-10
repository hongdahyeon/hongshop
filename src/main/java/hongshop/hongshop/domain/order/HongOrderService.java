package hongshop.hongshop.domain.order;

import hongshop.hongshop.domain.order.dto.HongOrderDTO;
import hongshop.hongshop.domain.order.dto.HongOrderFromCartDTO;
import hongshop.hongshop.domain.order.dto.HongOrderFromShopDTO;
import hongshop.hongshop.domain.order.dto.HongOrderStatusDTO;
import hongshop.hongshop.domain.order.vo.HongOrderDeliverVO;
import hongshop.hongshop.domain.order.vo.HongOrderVO;
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

    Long save(List<HongOrderDTO> hongOrderDTO, HongUser hongUser);

    Long saveFromCart(HongOrderFromCartDTO hongOrderDTO);

    Long saveFromShop(HongOrderFromShopDTO hongOrderFromShopDTO);

    List<HongOrderVO> list();

    HongOrderVO view(Long id);

    HongOrder getHongOrder(Long id);

    List<HongOrderVO> listOfUserOrder(Long id);

    void updateStatus(Long id, HongOrderStatusDTO hongOrderStatusDTO);

    List<HongOrderDeliverVO> getOrderAndDeliverByUserId(Long id);
}
