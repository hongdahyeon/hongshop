package hongshop.hongshop.domain.verfiyCode;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
* @fileName HongUserVerifyCodeRepository
* @author dahyeon
* @version 1.0.0
* @date 2023-12-13
* @summary   (1) findVerification : get list
 *                  - userEmail check
 *                  - now date is smaller than verify-expire-date (인증번호 만료일이 지나지 않은 값들)
 *                  - order by created date
**/

public interface HongUserVerifyCodeRepository extends JpaRepository<HongUserVerifyCode, Long> {

    @Query( "SELECT V FROM HongUserVerifyCode V WHERE V.userEmail = :userEmail " +
            "and V.verifyCheck = false " +
            "and V.verifyExpire > :now " +
            "ORDER BY V.verifyCreated DESC "
    )
    List<HongUserVerifyCode> findVerification(@Param("userEmail") String userEmail, @Param("now") LocalDateTime now);
}