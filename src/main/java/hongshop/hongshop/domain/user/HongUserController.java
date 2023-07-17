package hongshop.hongshop.domain.user;

import hongshop.hongshop.global.auth.PrincipalDetails;
import hongshop.hongshop.global.response.ApiDocumentResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


/**
 * @fileName HongUserController
 * @author dahyeon
 * @version 1.0.0
 * @date 2023-07-17
 * @summary
 **/

@RestController
@RequestMapping("/api")
@Tag(name = "hong user controller", description = "유저 컨트롤러")
@RequiredArgsConstructor
public class HongUserController {

    private final HongUserService hongUserService;

    @PostMapping("/user")
    @ApiDocumentResponse
    public ResponseEntity<Long> insert(@RequestBody HongUserDTO hongUserDTO){
        System.out.println("hongUserDTO = " + hongUserDTO);
        Long userUid = hongUserService.joinUser(hongUserDTO);
        return ResponseEntity.ok(userUid);
    }

    @GetMapping("/user")
    @ApiDocumentResponse
    public ResponseEntity<HongUser> loginUser(@AuthenticationPrincipal PrincipalDetails principalDetails){
        HongUser user = principalDetails.getUser();
        return ResponseEntity.ok(user);
    }

    @GetMapping("/userById")
    @ApiDocumentResponse
    public ResponseEntity<HongUserVO> getUserById(String userId){
        HongUserVO hongUserByUserId = hongUserService.getHongUserByUserId(userId);
        return ResponseEntity.ok(hongUserByUserId);
    }
}
