package hongshop.hongshop.domain.cart;

import hongshop.hongshop.domain.cart.dto.HongCartDTO;
import hongshop.hongshop.domain.cart.vo.HongCartWithProductVO;
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

    Long save(HongCartDTO cartDTO, HongUser hongUser);

    List<HongCartWithProductVO> getUsersListOfCartById(Long id);

    void delete(Long id);

    void deleteSeveral(Long[] ids);

    List<HongCartWithProductVO> listOfChoose(List<Long> ids);

    void updateCnt(Long id, HongCartDTO hongCartDTO);

    boolean findIfEmpty(Long userId, Long productId);
}
