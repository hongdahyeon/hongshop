package hongshop.hongshop.domain.user;

import hongshop.hongshop.domain.user.dto.HongUserDTO;
import hongshop.hongshop.domain.user.dto.HongUserPwdDateDTO;
import hongshop.hongshop.global.response.ApiDocumentResponse;
import hongshop.hongshop.global.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
* @fileName HongUserFrontRestController
* @author dahyeon
* @version 1.0.0
* @date 2023-10-31
* @summary  (로그인전) 사용자 관련 rest controller
**/
@RestController
@RequestMapping("/front/api")
@Slf4j
@Tag(name = "hong user front rest controller", description = "유저 Front Rest 컨트롤러")
@RequiredArgsConstructor
public class HongUserFrontRestController {

    private final HongUserService hongUserService;

    @PostMapping("/user")
    @Operation(summary = "insert user", description = "회원가입")
    @ApiDocumentResponse
    public Response insert(@Valid @RequestBody HongUserDTO hongUserDTO){
        Long userUid = hongUserService.joinUser(hongUserDTO);
        return Response.ok(userUid);
    }

    @GetMapping("/checkId")
    @Operation(summary = "check userId if duplicated", description = "회원가입시, 회원아이디 중복 여부 체크")
    @ApiDocumentResponse
    public Response checkUserId(String userId){
        Boolean bool = hongUserService.checkUserId(userId);
        return Response.ok(bool);
    }

    @GetMapping("/checkEmail")
    @Operation(summary = "check userEmail if duplicated", description = "회원가입시, 회원 이메일 중복 여부 체크")
    @ApiDocumentResponse
    public Response checkEmail(String userEmail){
        Boolean bool = hongUserService.checkUserEmail(userEmail);
        return Response.ok(bool);
    }

    @GetMapping("/initialPassword")
    @Operation(summary = "initial password", description = "회원 비밀번호 초기화")
    @ApiDocumentResponse
    public Response initialPassword(String userName, String userEmail){
        boolean bool = hongUserService.initialPassword(userEmail, userName);
        String message = (bool) ? "초기화된 비밀번호가 " + userEmail + " 이메일로 전송되었습니다." : "해당 사용자를 찾을 수 없습니다. 이메일과 이름을 확인해주세요.";

        if(bool) return Response.ok(message);
        else return Response.badRequest(message);
    }

    @GetMapping("/findUserId")
    @Operation(summary = "find userId", description = "회원 아이디 찾기")
    @ApiDocumentResponse
    public Response findUserId(String userName, String userEmail){
        boolean bool = hongUserService.findUserId(userEmail, userName);
        String message = (bool) ? "아이디가 " + userEmail + " 이메일로 전송되었습니다." : "해당 사용자를 찾을 수 없습니다. 이메일과 이름을 확인해주세요.";

        if(bool) return Response.ok(message);
        else return Response.badRequest(message);
    }

    @PutMapping("/changePwdEndDate")
    public Response changePwdEndDate(@RequestBody HongUserPwdDateDTO hongUserPwdDateDTO) {
        hongUserService.changePwdEndDate(hongUserPwdDateDTO);
        return Response.ok("비밀번호 만료일을 변경하였습니다.");
    }
}
