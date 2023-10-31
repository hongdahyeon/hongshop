package hongshop.hongshop.domain.fileLog;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
* @fileName HongFileLogRepository
* @author dahyeon
* @version 1.0.0
* @date 2023-07-18
* @summary
**/
public interface HongFileLogRepository extends JpaRepository<HongFileLog, Long> {

    List<HongFileLog> findAllByHongFileId(Long fileId);
}
