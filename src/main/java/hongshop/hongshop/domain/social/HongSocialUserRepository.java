package hongshop.hongshop.domain.social;

import org.springframework.data.jpa.repository.JpaRepository;

public interface HongSocialUserRepository extends JpaRepository<HongSocialUser, Long> {

    HongSocialUser findByUserId(String userId);
}