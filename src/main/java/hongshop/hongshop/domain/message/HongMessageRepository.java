package hongshop.hongshop.domain.message;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
* @fileName HongMessageRepository
* @author dahyeon
* @version 1.0.0
* @date 2023-11-15
* @summary  (1) findAllByReceiver_IdAndSender_IdOrderByCreatedDateDesc : 받는자Id, 보내는자Id -> 정렬 By. CreatedDate
 *          (2) findAllByReceiver_IdOrderByCreatedDateDesc : 받는자 Id -> 정렬 By. CreatedDate
**/

public interface HongMessageRepository extends JpaRepository<HongMessage, Long> {

    List<HongMessage> findAllByReceiver_IdAndSender_IdOrderByCreatedDateDesc(Long receiverId, Long senderId);

    List<HongMessage> findAllByReceiver_IdOrderByCreatedDateDesc(Long receiverId);
}
