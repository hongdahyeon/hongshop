package hongshop.hongshop.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class HongUserController {

    private final HongUserService hongUserService;

    @PostMapping("/user")
    public ResponseEntity<Long> insert(@RequestBody HongUserDTO hongUserDTO){
        Long userUid = hongUserService.joinUser(hongUserDTO);
        return ResponseEntity.ok(userUid);
    }
}
