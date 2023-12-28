package hongshop.hongshop.domain.file;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
* @fileName HongFileRepository
* @author dahyeon
* @version 1.0.0
* @date 2023-07-18
* @summary
 *  (1) findAllByHongFileGroupIdAndDeleteYn : 파일 리스트 조회
 *          -> 파일 그룹 Id, 파일 삭제여부
 *  (2) findAllByHongFileGroupIdAndDeleteYnAndFileState : 파일 리스트 조회
 *          -> 파일 그룹 Id , 파일 상태값,  파일 삭제여부
 *  (3) findAllByDeleteYnAndFileState : 파일 리스트 조회
 *          -> 삭제여부 , 파일 상태값
**/

public interface HongFileRepository extends JpaRepository<HongFile, Long> {

    List<HongFile> findAllByHongFileGroupIdAndDeleteYn(Long id, String deleteYn);
    List<HongFile> findAllByHongFileGroupIdAndDeleteYnAndFileState(Long id, String deleteYn, FileState fileState);
    List<HongFile> findAllByDeleteYnAndFileState(String deleteYn, FileState fileState);
}
