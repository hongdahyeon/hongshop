package hongshop.hongshop.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @fileName HongUserRepository
 * @author dahyeon
 * @version 1.0.0
 * @date 2023-07-17
 * @summary
 **/

public interface HongUserRepository extends JpaRepository<HongUser, Long> {

    Optional<HongUser> findByUserId(String userId);

    Optional<HongUser> findByUserEmail(String userEmail);

    Optional<HongUser> findByUserEmailAndUserName(String userEmail, String userName);
}
