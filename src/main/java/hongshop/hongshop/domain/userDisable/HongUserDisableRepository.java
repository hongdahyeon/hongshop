package hongshop.hongshop.domain.userDisable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
* @fileName HongUserDisableRepository
* @author dahyeon
* @version 1.0.0
* @date 2023-12-29
* @summary  (1) findByHongUserId: 사용자의 Id를 통해 비활성화 사유 정보 조회 (삭제여부 N)
 *          (2) findByHongUserIdLong: 사용자의 uid를 통해 비활성화 정보 조회 (삭제여부 N)
 *          -> 사용자를 통해 비활성화를 활성화로 변경할떄 삭제여부를 N에서 Y로 변경한다.
**/


public interface HongUserDisableRepository extends JpaRepository<HongUserDisable, Long> {

    @Query("SELECT m FROM HongUserDisable m WHERE m.hongUser.userId = :userId and m.deleteYn = 'N'")
    HongUserDisable findByHongUserId(@Param("userId") String userId);

    @Query("SELECT m FROM HongUserDisable m WHERE m.hongUser.id = :userId and m.deleteYn = 'N'")
    HongUserDisable findByHongUserIdLong(@Param("userId") Long userId);
}