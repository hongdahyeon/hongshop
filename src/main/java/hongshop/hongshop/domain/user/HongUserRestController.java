package hongshop.hongshop.domain.user;

import hongshop.hongshop.domain.user.dto.HongUserDTO;
import hongshop.hongshop.domain.user.dto.HongUserRoleDTO;
import hongshop.hongshop.domain.user.vo.HongUserCouponVO;
import hongshop.hongshop.domain.user.vo.HongUserVO;
import hongshop.hongshop.global.response.ApiDocumentResponse;
import hongshop.hongshop.global.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/reset/userNonLocked/{userId}")
    @Operation(summary = "update user nonLocked", description = "회원 계정 잠김 여부 변경")
    @ApiDocumentResponse
    public Response updateUserNonLocked(@PathVariable String userId){
        hongUserService.updateUserNonLocked(userId);
        return Response.ok("해당 사용자의 계정 정지가 풀렸습니다.");
    }

    @GetMapping("/user-coupon")
    @Operation(summary = "get list of user for coupon", description = "쿠폰 지급을 위한 유저 리스트 조회")
    @ApiDocumentResponse
    public Response userForCoupon(){
        List<HongUserCouponVO> userListForCoupon = hongUserService.getUserListForCoupon();
        return Response.ok(userListForCoupon);
    }
}
