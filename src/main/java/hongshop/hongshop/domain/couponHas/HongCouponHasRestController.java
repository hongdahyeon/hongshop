package hongshop.hongshop.domain.couponHas;

import hongshop.hongshop.domain.couponHas.dto.HongCouponHasDTO;
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

    @PostMapping("/coupon-has")
    @Operation(summary = "insert coupon-has", description = "사용자가 갖는 쿠폰 저장하기")
    @ApiDocumentResponse
    public Response save(@RequestBody HongCouponHasDTO hongCouponHasDTO, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        Long joinId = hongCouponHasService.join(principalDetails.getUser(), hongCouponHasDTO);
        return Response.ok(joinId);
    }

    @GetMapping("/coupon-has")
    @Operation(summary = "list coupon-has", description = "사용자의 쿠폰 리스트 전체 조회")
    @ApiDocumentResponse
    public Response list(){
        List<HongCouponHasVO> list = hongCouponHasService.list();
        return Response.ok(list);
    }


    @GetMapping("/coupon-has/{id}")
    @Operation(summary = "coupon-has view", description = "사용자의 쿠폰 리스트 단건 조회")
    @ApiDocumentResponse
    public Response view(@PathVariable Long id){
        HongCouponHasVO view = hongCouponHasService.view(id);
        return Response.ok(view);
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

    @GetMapping("/use-coupon-has/{id}")
    @Operation(summary = "coupon-has view", description = "사용자의 쿠폰 리스트 단건 조회")
    @ApiDocumentResponse
    public Response useCopon(@PathVariable Long id){
        Integer couponRate = hongCouponHasService.useCoupon(id);
        return Response.ok("해당 쿠폰을 사용했습니다. 할인가격: " + couponRate);
    }


}
