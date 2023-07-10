package hongshop.hongshop.domain.user;

import hongshop.hongshop.global.auth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class HongUserController {

    private final HongUserService hongUserService;

    @PostMapping("/user")
    public ResponseEntity<Long> insert(@RequestBody HongUserDTO hongUserDTO){
        System.out.println("hongUserDTO = " + hongUserDTO);
        Long userUid = hongUserService.joinUser(hongUserDTO);
        return ResponseEntity.ok(userUid);
    }

    @GetMapping("/user")
    public ResponseEntity<HongUser> loginUser(@AuthenticationPrincipal PrincipalDetails principalDetails){
        HongUser user = principalDetails.getUser();
        return ResponseEntity.ok(user);
    }

    @GetMapping("/userById")
    public ResponseEntity<HongUserVO> getUserById(String userId){
        HongUserVO hongUserByUserId = hongUserService.getHongUserByUserId(userId);
        return ResponseEntity.ok(hongUserByUserId);
    }
}
