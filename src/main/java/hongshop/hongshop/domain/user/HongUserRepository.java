package hongshop.hongshop.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * @fileName HongUserRepository
 * @author dahyeon
 * @version 1.0.0
 * @date 2023-07-17
 * @summary     (1) findByUserId : userId로 사용자 찾기 -> return Optional
 *              (2) findByUserEmail : userEmail로 사용자 찾기 -> return Optional
 *              (3) findByUserEmailAndUserName : userName & userEmail로 사용자 찾기 -> return Optional
 *              (4) findAllByRoleIn : 권한 리스트 안에 속하는 유저들 리스트 반환
 *              (5) findByUserIdAndUserEmail : userId, userEmail로 사용자 찾기
 **/

public interface HongUserRepository extends JpaRepository<HongUser, Long> {

    Optional<HongUser> findByUserId(String userId);

    Optional<HongUser> findByUserEmail(String userEmail);

    Optional<HongUser> findByUserEmailAndUserName(String userEmail, String userName);

    List<HongUser> findAllByRoleIn(List<HongRoleType> roleLst);

    Optional<HongUser> findByUserIdAndUserEmail(String userId, String userEmail);
}
