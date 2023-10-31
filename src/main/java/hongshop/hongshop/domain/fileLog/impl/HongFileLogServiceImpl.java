package hongshop.hongshop.domain.fileLog.impl;

import hongshop.hongshop.domain.file.HongFile;
import hongshop.hongshop.domain.file.HongFileRepository;
import hongshop.hongshop.domain.fileLog.HongFileLog;
import hongshop.hongshop.domain.fileLog.HongFileLogRepository;
import hongshop.hongshop.domain.fileLog.HongFileLogService;
import hongshop.hongshop.domain.fileLog.vo.HongFileLogVO;
import hongshop.hongshop.domain.user.HongUserService;
import hongshop.hongshop.domain.user.vo.HongUserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* @fileName HongFileLogServiceImpl
* @author dahyeon
* @version 1.0.0
* @date 2023-07-18
* @summary
**/

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HongFileLogServiceImpl implements HongFileLogService {

    private final HongFileLogRepository hongFileLogRepository;
    private final HongFileRepository hongFileRepository;
    private final HongUserService hongUserService;
    @Override
    @Transactional(readOnly = false)
    public Long save(Long fileId) {
        HongFile hongFile = hongFileRepository.findById(fileId).orElseThrow(() -> new IllegalArgumentException("there is no file"));
        HongFileLog hongFileLog = HongFileLog.hongFileLogInsertBuilder()
                .hongFile(hongFile)
                .build();
        HongFileLog save = hongFileLogRepository.save(hongFileLog);
        return save.getId();
    }

    @Override
    public List<HongFileLogVO> list(Long id) {
        List<HongFileLog> fileLog = hongFileLogRepository.findAllByHongFileId(id);
        return fileLog.stream().map(log -> {
            HongUserVO user = hongUserService.getHongUserById(log.getRegId());
            return new HongFileLogVO(log, user.getUserName());
        }).toList();
    }
}
