package hongshop.hongshop.domain.category;

import hongshop.hongshop.domain.category.dto.HongCategoryDTO;
import hongshop.hongshop.domain.category.vo.HongCategoryVO;
import hongshop.hongshop.domain.category.vo.HongCategoryWithProductVO;

import java.util.List;

/**
 * @fileName HongCategoryService
 * @author dahyeon
 * @version 1.0.0
 * @date 2023-07-17
 * @summary
 **/

public interface HongCategoryService {

    Long join(HongCategoryDTO hongCategoryDTO);

    List<HongCategoryVO> list();

    List<HongCategoryWithProductVO> listWithProduct();

    HongCategoryVO show(Long id);

    HongCategoryWithProductVO showWithProduct(Long id);

    void update(HongCategoryDTO hongCategoryDTO, Long id);

    void delete(Long id);

}
