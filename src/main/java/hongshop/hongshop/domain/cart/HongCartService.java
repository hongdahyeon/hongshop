package hongshop.hongshop.domain.cart;

import hongshop.hongshop.domain.cart.dto.HongCartDTO;
import hongshop.hongshop.domain.cart.vo.HongCartVO;
import hongshop.hongshop.domain.user.HongUser;

import java.util.List;

/**
* @fileName HongCartService
* @author dahyeon
* @version 1.0.0
* @date 2023-07-18
* @summary
**/

public interface HongCartService {
    Integer save(List<HongCartDTO> cartDTOList, HongUser hongUser);

    List<HongCartVO> getUsersListOfCartByLoginUser(HongUser hongUser);

    List<HongCartVO> getUsersListOfCartById(Long id);

    void delete(Long id);
}
