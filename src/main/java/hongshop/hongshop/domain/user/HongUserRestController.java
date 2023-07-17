package hongshop.hongshop.domain.user;

import hongshop.hongshop.global.auth.PrincipalDetails;
import hongshop.hongshop.global.response.ApiDocumentResponse;
import hongshop.hongshop.global.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


/**
 * @fileName HongUserRestController
 * @author dahyeon
 * @version 1.0.0
 * @date 2023-07-17
 * @summary
 **/

@RestController
@RequestMapping("/api")
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
}
