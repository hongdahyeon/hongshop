package hongshop.hongshop.domain.product;

import hongshop.hongshop.domain.product.dto.HongProductDTO;
import hongshop.hongshop.domain.product.vo.HongPrdouctUserVO;
import hongshop.hongshop.domain.product.vo.HongProductVO;

import java.util.List;

/**
* @fileName HongProductService
* @author dahyeon
* @version 1.0.0
* @date 2023-07-18
* @summary
**/

public interface HongProductService {

    Long save(HongProductDTO hongProductDTO);

    List<HongProductVO> list();

    HongProductVO view(Long id);

    HongProduct productInfo(Long id);

    void update(HongProductDTO hongProductDTO, Long id);

    void updateStockCnt(Integer orderCnt, HongProduct hongProduct);

    void delete(Long id);

    HongPrdouctUserVO productUser(Long id);
}
