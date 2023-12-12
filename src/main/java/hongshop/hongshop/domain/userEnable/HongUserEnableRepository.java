package hongshop.hongshop.domain.userEnable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface HongUserEnableRepository extends JpaRepository<HongUserEnable, Long> {

    @Query("SELECT m FROM HongUserEnable m WHERE m.hongUser.userId = :userId and m.deleteYn = 'N'")
    HongUserEnable findByHongUserId(@Param("userId") String userId);

    @Query("SELECT m FROM HongUserEnable m WHERE m.hongUser.id = :userId and m.deleteYn = 'N'")
    HongUserEnable findByHongUserIdLong(@Param("userId") Long userId);
}