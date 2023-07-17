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

    HongCategoryVO show(Long id);

    void update(HongCategoryDTO hongCategoryDTO, Long id);

}
