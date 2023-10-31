package hongshop.hongshop.domain.user;

import hongshop.hongshop.domain.user.dto.HongUserDTO;
import hongshop.hongshop.domain.user.dto.HongUserRoleDTO;
import hongshop.hongshop.domain.user.vo.HongUserVO;
import hongshop.hongshop.global.auth.PrincipalDetails;
import hongshop.hongshop.global.response.ApiDocumentResponse;
import hongshop.hongshop.global.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


/**
 * @fileName HongUserRestController
 * @author dahyeon
 * @version 1.0.0
 * @date 2023-07-17
 * @summary
 **/

@RestController
@RequestMapping("/api")
@Slf4j
@Tag(name = "hong user rest controller", description = "유저 Rest 컨트롤러")
@RequiredArgsConstructor
public class HongUserRestController {

    private final HongUserService hongUserService;

    @PostMapping("/user")
    @Operation(summary = "insert user", description = "회원가입")
    @ApiDocumentResponse
    public Response insert(@Valid @RequestBody HongUserDTO hongUserDTO){
        Long userUid = hongUserService.joinUser(hongUserDTO);
        return Response.ok(userUid);
    }

    @GetMapping("/user")
    @Operation(summary = "get login user", description = "로그인한 회원 정보 가져오기")
    @ApiDocumentResponse
    public Response loginUser(@AuthenticationPrincipal PrincipalDetails principalDetails){
        HongUser user = principalDetails.getUser();
        return Response.ok(user);
    }

    @GetMapping("/userById")
    @Operation(summary = "get user info by userId", description = "회원 아이디로 정보 가져오기")
    @ApiDocumentResponse
    public Response getUserById(String userId){
        HongUserVO hongUserByUserId = hongUserService.getHongUserByUserId(userId);
        return Response.ok(hongUserByUserId);
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

    @PutMapping("/user")
    @Operation(summary = "update user", description = "로그인한 회원 정보 수정하기")
    @ApiDocumentResponse
    public Response updateUser(@RequestBody HongUserDTO hongUserDTO){
        hongUserService.updateHongUser(hongUserDTO);
        return Response.ok("updated");
    }

    @GetMapping("/users")
    @Operation(summary = "get user list", description = "회원 정보 리스트 가져오기")
    @ApiDocumentResponse
    public Response list(){
        List<HongUserVO> list = hongUserService.list();
        return Response.ok(list);
    }

    @PutMapping("/user/{id}/role")
    @Operation(summary = "update user role", description = "회원 권한 변경")
    @ApiDocumentResponse
    public Response updateUserRole(@PathVariable Long id, @RequestBody HongUserRoleDTO hongUserRoleDTO){
        hongUserService.updateUserRole(id, hongUserRoleDTO);
        return Response.ok("해당 사용자의 권한 정보가 변경되었습니다.");
    }

    @GetMapping("/initialPassword")
    @Operation(summary = "initial password", description = "회원 비밀번호 초기화")
    @ApiDocumentResponse
    public Response findPassword(String userName, String userEmail){
//        Boolean bool = hongUserService.checkUserEmail(userEmail);
//        return Response.ok(bool);
        return null;
    }
}
