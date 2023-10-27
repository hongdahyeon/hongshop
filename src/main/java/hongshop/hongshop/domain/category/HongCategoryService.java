package hongshop.hongshop.domain.category;

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

    List<HongCategoryVO> listWithProduct();

    HongCategoryVO show(Long id);

    HongCategoryVO showWithProduct(Long id);

    void update(HongCategoryDTO hongCategoryDTO, Long id);

    void delete(Long id);

}
