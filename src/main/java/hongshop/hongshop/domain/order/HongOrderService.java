package hongshop.hongshop.domain.order;

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

    HongOrderVO view(Long id);

    List<HongOrderVO> listOfUserOrder(Long id);
}
