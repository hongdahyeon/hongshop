package hongshop.hongshop.domain.fileGroup;

import hongshop.hongshop.domain.file.FileState;
import hongshop.hongshop.domain.fileGroup.vo.HongFileGroupVO;

/**
* @fileName HongFileGroupService
* @author dahyeon
* @version 1.0.0
* @date 2023-07-18
* @summary
**/

public interface HongFileGroupService {

    HongFileGroupVO listWithDeleteYnAndFileState(Long id, String deleteYn, FileState fileState);

    HongFileGroup saveFileGroup();

    HongFileGroup findFileGroup(Long id);
}
