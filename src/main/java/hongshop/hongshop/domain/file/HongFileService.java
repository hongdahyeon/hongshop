package hongshop.hongshop.domain.file;

import hongshop.hongshop.domain.file.vo.HongFileVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
* @fileName HongFileService
* @author dahyeon
* @version 1.0.0
* @date 2023-07-18
* @summary
**/

public interface HongFileService {

    Map<String, Object> saveFile(MultipartFile multipartFile, Long fileGroupId);

    void updateDownCnt(Long id);

    HongFileVO view(Long id);

    HongFileVO download(Long id);

    void updateFileState(Long fileGroupId);

    Map<String, Object> uploadCKImageFile(MultipartFile multipartFile);

    void deleteFiles(List<Long> deleteFile);

    List<HongFileVO> all();
}
