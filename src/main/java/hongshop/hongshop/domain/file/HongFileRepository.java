package hongshop.hongshop.domain.file;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
* @fileName HongFileRepository
* @author dahyeon
* @version 1.0.0
* @date 2023-07-18
* @summary (1) findAllByHongFileGroupId : find file list by file-group-id
**/

public interface HongFileRepository extends JpaRepository<HongFile, Long> {

    List<HongFile> findAllByHongFileGroupId(Long id);
}
