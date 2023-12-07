package hongshop.hongshop.domain.coupon;


import hongshop.hongshop.domain.coupon.dto.HongCouponDTO;
import hongshop.hongshop.domain.coupon.vo.HongCouponGroupHistVO;
import hongshop.hongshop.domain.coupon.vo.HongCouponGroupRequestVO;
import hongshop.hongshop.domain.coupon.vo.HongCouponVO;
import hongshop.hongshop.global.response.ApiDocumentResponse;
import hongshop.hongshop.global.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "hong coupon rest controller", description = "쿠폰 Rest 컨트롤러")
public class HongCouponRestController {

    private final HongCouponService hongCouponService;

    @PostMapping("/coupon")
    @Operation(summary = "insert coupon", description = "쿠폰 저장하기")
    @ApiDocumentResponse
    public Response save(@RequestBody HongCouponDTO hongCouponDTO) {
        Long joinId = hongCouponService.join(hongCouponDTO);
        return Response.ok(joinId);
    }

    @GetMapping("/coupon")
    @Operation(summary = "get all coupon", description = "쿠폰 리스트 조회")
    @ApiDocumentResponse
    public Response list(){
        List<HongCouponVO> list = hongCouponService.list();
        return Response.ok(list);
    }

    @GetMapping("/coupon/{id}")
    @Operation(summary = "get coupon view", description = "쿠폰 단건 조회")
    @ApiDocumentResponse
    public Response view(@PathVariable Long id) {
        HongCouponVO view = hongCouponService.view(id);
        return Response.ok(view);
    }

    @PutMapping("/coupon/{id}")
    @Operation(summary = "update coupon", description = "쿠폰 수정")
    @ApiDocumentResponse
    public Response update(@PathVariable Long id, @RequestBody HongCouponDTO hongCouponDTO) {
        hongCouponService.update(id, hongCouponDTO);
        return Response.ok("해당 쿠폰을 수정하였습니다.");
    }

    @DeleteMapping("/coupon/{id}")
    @Operation(summary = "delete coupon", description = "쿠폰 삭제")
    @ApiDocumentResponse
    public Response delete(@PathVariable Long id) {
        hongCouponService.delete(id);
        return Response.ok("해당 쿠폰을 삭제하였습니다.");
    }

    @GetMapping("/coupon-and-request")
    @Operation(summary = "쿠폰 하위, 쿠폰 요청 리스트 조회", description = "쿠폰 하위, 쿠폰 요청 리스트 조회")
    @ApiDocumentResponse
    public Response couponAndRequest(){
        List<HongCouponGroupRequestVO> list = hongCouponService.couponAndRequest();
        return Response.ok(list);
    }

    @GetMapping("/coupon-hist-user")
    @Operation(summary = "쿠폰 사용자 리스트 조회", description = "쿠폰 사용자 리스트 조회")
    @ApiDocumentResponse
    public Response couponHistUser(){
        List<HongCouponGroupHistVO> couponHist = hongCouponService.couponAndUserHist();
        return Response.ok(couponHist);
    }

    @GetMapping("/coupon-chk-deleteAble")
    @Operation(summary = "쿠폰 리스트 조회 - 삭제 가능 여부 체킹", description = "쿠폰 리스트 조회 - 삭제 가능 여부 체킹")
    @ApiDocumentResponse
    public Response couponChkWithUser(){
        List<HongCouponVO> list = hongCouponService.listWithChkUser();
        return Response.ok(list);
    }

}
