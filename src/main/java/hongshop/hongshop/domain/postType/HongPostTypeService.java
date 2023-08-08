package hongshop.hongshop.domain.postType;

import java.util.List;

/**
* @fileName HongPostTypeService
* @author dahyeon
* @version 1.0.0
* @date 2023-08-08
* @summary
**/

public interface HongPostTypeService {

    List<HongPostTypeVO> list();

    List<HongPostTypeVO> listWithPost();

    Long join(HongPostTypeDTO hongPostTypeDTO);

    HongPostTypeVO view(Long id);

}
