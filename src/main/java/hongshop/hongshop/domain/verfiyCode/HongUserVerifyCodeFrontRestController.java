package hongshop.hongshop.domain.verfiyCode;

import hongshop.hongshop.global.response.ApiDocumentResponse;
import hongshop.hongshop.global.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/front/api")
@Slf4j
@Tag(name = "hong user verify code front rest controller", description = "유저 인증번호 발송 관련 테이블 (로그인전)")
@RequiredArgsConstructor
public class HongUserVerifyCodeFrontRestController {

    private final HongUserVerifyCodeService hongUserVerifyCodeService;

    @GetMapping("/check-verify")
    @Operation(summary = "verifyCode, userEmail로 인증번호 체크", description = "verifyCode, userEmail로 인증번호 체크")
    @ApiDocumentResponse
    public Response checkVerify(String verifyCode, String userEmail) {
        Map<String, Object> rtnMp = new HashMap<>();
        int checkIt = hongUserVerifyCodeService.checkVerify(verifyCode, userEmail);
        String message = (checkIt == 0) ? "가장 최근 받은 인증번호로 인증해주세요. \n 인증번호는 최대 하루까지 사용 가능합니다." : "휴먼 계정이 풀렸습니다.";
        rtnMp.put("result", checkIt); rtnMp.put("message", message);
        return Response.ok(rtnMp);
    }
}
