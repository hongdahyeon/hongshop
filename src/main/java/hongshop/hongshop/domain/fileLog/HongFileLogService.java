package hongshop.hongshop.domain.fileLog;

import hongshop.hongshop.domain.fileLog.vo.HongFileLogVO;

import java.util.List;

/**
* @fileName HongFileLogService
* @author dahyeon
* @version 1.0.0
* @date 2023-07-18
* @summary
**/

public interface HongFileLogService {
    Long save(Long fileId);

    List<HongFileLogVO> list(Long id);

}
