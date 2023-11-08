package hongshop.hongshop.domain.order;

import hongshop.hongshop.domain.order.dto.HongOrderDTO;
import hongshop.hongshop.domain.order.dto.HongOrderFromCartDTO;
import hongshop.hongshop.domain.order.dto.HongOrderStatusDTO;
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

    Long saveFromCart(List<HongOrderFromCartDTO> hongOrderDTO, HongUser hongUser);

    List<HongOrderVO> list();

    HongOrderVO view(Long id);

    List<HongOrderVO> listOfUserOrder(Long id);

    void updateStatus(Long id, HongOrderStatusDTO hongOrderStatusDTO);
}
