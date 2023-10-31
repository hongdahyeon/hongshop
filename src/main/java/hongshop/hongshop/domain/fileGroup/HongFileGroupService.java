package hongshop.hongshop.domain.fileGroup;

import hongshop.hongshop.domain.fileGroup.vo.HongFileGroupVO;

/**
* @fileName HongFileGroupService
* @author dahyeon
* @version 1.0.0
* @date 2023-07-18
* @summary
**/

public interface HongFileGroupService {
    HongFileGroupVO list(Long id);

    HongFileGroup saveFileGroup();

    HongFileGroup findFileGroup(Long id);
}
