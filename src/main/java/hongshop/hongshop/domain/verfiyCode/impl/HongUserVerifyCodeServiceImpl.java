package hongshop.hongshop.domain.verfiyCode.impl;

import hongshop.hongshop.domain.user.HongUser;
import hongshop.hongshop.domain.user.HongUserRepository;
import hongshop.hongshop.domain.verfiyCode.HongUserVerifyCode;
import hongshop.hongshop.domain.verfiyCode.HongUserVerifyCodeRepository;
import hongshop.hongshop.domain.verfiyCode.HongUserVerifyCodeService;
import hongshop.hongshop.domain.verfiyCode.dto.HongUserVerifyCodeDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


/**
* @fileName HongUserVerifyCodeServiceImpl
* @author dahyeon
* @version 1.0.0
* @date 2023-12-13
* @summary  (1) join : 회원 인증번호 코드 저장
 *          (2) checkVerify : 인증번호 유효기간에 포함되며, 해당 이메일 사용자로 인증번호를 찾는다.
 *                            이때 가장 최근 인증번호와 일치하는지 확인하고,
 *                             -> 일치한다면 유효기간에 포함되는 이메일 사용자의 인증번호를 모두 verifyCheck true로 만들어 사용했음을 알린다.
 *                                그리고 해당 사용자의 최근 로그인 일자를 오늘로 변경한다.
**/

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HongUserVerifyCodeServiceImpl implements HongUserVerifyCodeService {

    private final HongUserVerifyCodeRepository hongUserVerifyCodeRepository;
    private final HongUserRepository hongUserRepository;

    @Override
    @Transactional(readOnly = false)
    public Long join(HongUserVerifyCodeDTO hongUserVerifyCodeDTO) {
        HongUserVerifyCode hongUserVerifyCode = HongUserVerifyCode.hongUserVerifyCodeInsert()
                                                        .userEmail(hongUserVerifyCodeDTO.getUserEmail())
                                                        .verifyCode(hongUserVerifyCodeDTO.getVerifyCode())
                                                        .build();

        HongUserVerifyCode save = hongUserVerifyCodeRepository.save(hongUserVerifyCode);
        return save.getId();
    }

    @Override
    @Transactional(readOnly = false)
    public int checkVerify(String verifyCode, String userEmail) {
        int checkVerify = 0;
        List<HongUserVerifyCode> checkIt = hongUserVerifyCodeRepository.findVerification(userEmail, LocalDateTime.now());
        if(!checkIt.isEmpty()) {
            HongUserVerifyCode lastOne = checkIt.get(0);
            if(verifyCode.equals(lastOne.getVerifyCode())) {
                checkVerify = 1;
                HongUser hongUser = hongUserRepository.findByUserEmail(userEmail).orElseThrow(() -> new IllegalArgumentException("there is no user"));
                hongUser.updateLastLoginDate();                             // update last login date
                checkIt.forEach(HongUserVerifyCode::changeVerifyCheck);     // verify-check to true
            }
        }

        return checkVerify;
    }
}
