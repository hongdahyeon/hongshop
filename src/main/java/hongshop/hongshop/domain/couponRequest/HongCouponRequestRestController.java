package hongshop.hongshop.domain.couponRequest;


import hongshop.hongshop.domain.couponRequest.dto.HongCouponRequestDTO;
import hongshop.hongshop.domain.couponRequest.dto.HongCouponRequestLstDTO;
import hongshop.hongshop.domain.couponRequest.vo.HongCouponRequestVO;
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
@Tag(name = "hong coupon request rest controller", description = "사용자의 쿠폰 요청 Rest 컨트롤러")
public class HongCouponRequestRestController {

    private final HongCouponRequestService hongCouponRequestService;

    @PostMapping("/coupon-request")
    @Operation(summary = "insert coupon-request", description = "사용자의 쿠폰 요청 저장하기")
    @ApiDocumentResponse
    public Response save(@RequestBody HongCouponRequestDTO hongCouponRequestDTO, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        Long joinId = hongCouponRequestService.join(hongCouponRequestDTO, principalDetails.getUser());
        return Response.ok(joinId);
    }

    @GetMapping("/coupon-request")
    @Operation(summary = "get list of coupon-request", description = "사용자의 쿠폰 요청 리스트 조회")
    @ApiDocumentResponse
    public Response list() {
        List<HongCouponRequestVO> list = hongCouponRequestService.list();
        return Response.ok(list);
    }

    @GetMapping("/coupon-request-user")
    @Operation(summary = "get list of coupon-request by user", description = "사용자의 쿠폰 요청 리스트 조회 - 로그인한 사용자꺼")
    @ApiDocumentResponse
    public Response listOfUser(@AuthenticationPrincipal PrincipalDetails principalDetails){
        List<HongCouponRequestVO> list = hongCouponRequestService.listOfUser(principalDetails.getUser());
        return Response.ok(list);
    }

    @DeleteMapping("/coupon-request-user/{id}")
    @Operation(summary = "delete coupon-request", description = "사용자의 쿠폰 요청 삭제")
    @ApiDocumentResponse
    public Response delete(@PathVariable Long id){
        hongCouponRequestService.delete(id);
        return Response.ok("해당 쿠폰 요청을 삭제했습니다.");
    }

    @GetMapping("/coupon-request-by/{id}")
    @Operation(summary = "get list of coupon-request group by coupon", description = "사용자의 쿠폰 요청 리스트 조회 - group by 쿠폰")
    @ApiDocumentResponse
    public Response listGroupByCoupon(@PathVariable Long id) {
        List<HongCouponRequestVO> list = hongCouponRequestService.listByCoupon(id);
        return Response.ok(list);
    }

    @PutMapping("/coupon-request-approve")
    @Operation(summary = "coupon-request approve", description = "쿠폰 요청 승인")
    @ApiDocumentResponse
    public Response requestApprove(@RequestBody HongCouponRequestLstDTO hongCouponRequestLstDTO){
        Integer successRequest = hongCouponRequestService.approveRequest(hongCouponRequestLstDTO);
        return Response.ok(successRequest);
    }
}