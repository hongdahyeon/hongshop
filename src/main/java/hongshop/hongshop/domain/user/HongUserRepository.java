package hongshop.hongshop.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HongUserRepository extends JpaRepository<HongUser, Long> {

    Optional<HongUser> findByUserId(String userId);
}
