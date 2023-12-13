package hongshop.hongshop.domain.verfiyCode;

import hongshop.hongshop.domain.verfiyCode.dto.HongUserVerifyCodeDTO;

public interface HongUserVerifyCodeService {

    Long join(HongUserVerifyCodeDTO hongUserVerifyCodeDTO);

    int checkVerify(String verifyCode, String userEmail);
}