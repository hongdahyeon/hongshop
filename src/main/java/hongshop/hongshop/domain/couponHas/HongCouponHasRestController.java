package hongshop.hongshop.domain.couponHas;

import hongshop.hongshop.domain.couponHas.dto.HongCouponHasLstDTO;
import hongshop.hongshop.domain.couponHas.vo.HongCouponHasVO;
import hongshop.hongshop.global.auth.PrincipalDetails;
import hongshop.hongshop.global.response.ApiDocumentResponse;
import hongshop.hongshop.global.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "hong coupon has rest controller", description = "사용자의 쿠폰 has Rest 컨트롤러")
public class HongCouponHasRestController {

    private final HongCouponHasService hongCouponHasService;

    @PostMapping("/coupon-has-user")
    @Operation(summary = "insert coupon-has by userLst", description = "사용자가 갖는 쿠폰 저장하기 - 사용자 리스트로 해당 사용자들 전부 발급")
    @ApiDocumentResponse
    public Response saveByUser(@RequestBody HongCouponHasLstDTO hongCouponHasLstDTO) {
        Integer saveUser = hongCouponHasService.joinAll(hongCouponHasLstDTO);
        return Response.ok(saveUser);
    }

    @GetMapping("/coupon-user-has")
    @Operation(summary = "list coupon-user-has", description = "로그인한 사용자가 갖고 있는 쿠폰 리스트 조회")
    @ApiDocumentResponse
    public Response userHasList(@AuthenticationPrincipal PrincipalDetails principalDetails){
        List<HongCouponHasVO> hongCouponHasVOS = hongCouponHasService.listByHongUser(principalDetails.getUser());
        return Response.ok(hongCouponHasVOS);
    }

    @DeleteMapping("/coupon-has/{id}")
    @Operation(summary = "coupon-has delete", description = "사용자의 쿠폰 리스트 단건 삭제")
    @ApiDocumentResponse
    public Response delete(@PathVariable Long id){
        hongCouponHasService.delete(id);
        return Response.ok("해당 사용자의 쿠폰을 삭제했습니다.");
    }

    @GetMapping("/coupon-has-chk")
    @Operation(summary = "사용자가 갖고 있는(사용가능한) 쿠폰 리스트 조회 -> 쿠폰 삭제 가능 여부 체킹", description = "사용자가 갖고 있는(사용가능한) 쿠폰 리스트 조회 -> 쿠폰 삭제 가능 여부 체킹")
    @ApiDocumentResponse
    public Response couponHasChk(@AuthenticationPrincipal PrincipalDetails principalDetails){
        List<HongCouponHasVO> list = hongCouponHasService.listByHongUserWithDeleteYn(principalDetails.getUser());
        return Response.ok(list);
    }
}
