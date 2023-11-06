package hongshop.hongshop.domain.fileGroup;

import hongshop.hongshop.domain.fileGroup.vo.HongFileGroupVO;

import java.util.List;

/**
* @fileName HongFileGroupService
* @author dahyeon
* @version 1.0.0
* @date 2023-07-18
* @summary
**/

public interface HongFileGroupService {

    List<HongFileGroupVO> all();
    HongFileGroupVO listwithDeleteYnAndFileState(Long id);

    HongFileGroup saveFileGroup();

    HongFileGroup findFileGroup(Long id);
}
