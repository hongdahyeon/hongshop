package hongshop.hongshop.domain.file;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
* @fileName HongFileRepository
* @author dahyeon
* @version 1.0.0
* @date 2023-07-18
* @summary
 *  (1) findAllByHongFileGroupIdAndDeleteYn : find file list by file-group-id and delete-yn is ?
 *          -> for update file state
 *  (2) findAllByHongFileGroupIdAndDeleteYnAndFileState : find file list by file-group-id and delete-yn is ? and file-state is ?
 *          -> for list of files by file-group-id
 *  (3) findAllByDeleteYnAndFileState : find file list by delete-yn and file-state
**/

public interface HongFileRepository extends JpaRepository<HongFile, Long> {

    List<HongFile> findAllByHongFileGroupIdAndDeleteYn(Long id, String deleteYn);
    List<HongFile> findAllByHongFileGroupIdAndDeleteYnAndFileState(Long id, String deleteYn, FileState fileState);
    List<HongFile> findAllByDeleteYnAndFileState(String deleteYn, FileState fileState);
}
