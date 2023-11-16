package hongshop.hongshop.domain.message;

import hongshop.hongshop.domain.user.HongUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
* @fileName HongMessageRepository
* @author dahyeon
* @version 1.0.0
* @date 2023-11-15
* @summary  (1) findDistinctSendersByReceiver : (ROLE_SUPER입장) 사용자들 중, 자기한테 메시지를 보낸 사용자들 조회
 *              -> ROLE_SUPER가 메시지를 받았기 때문에 receiver입장이다.
 *          (2) findMessagesBetweenSenderAndReceiver : 2명의 사용자에 대해 각각 receiver이자 sender인 대화 내용을 모두 가져온다.
**/

public interface HongMessageRepository extends JpaRepository<HongMessage, Long> {

    @Query("SELECT DISTINCT m.sender FROM HongMessage m WHERE m.receiver.id = :receiverId")
    List<HongUser> findDistinctSendersByReceiver(@Param("receiverId") Long receiverId);

    @Query("SELECT m " +
             "FROM HongMessage m " +
            "WHERE (m.receiver.id = :receiverId AND m.sender.id = :senderId) " +
            "   OR (m.receiver.id = :senderId AND m.sender.id = :receiverId) " +
            "ORDER BY m.id DESC")
    List<HongMessage> findMessagesBetweenSenderAndReceiver(
            @Param("receiverId") Long receiverId,
            @Param("senderId") Long senderId
    );
}
