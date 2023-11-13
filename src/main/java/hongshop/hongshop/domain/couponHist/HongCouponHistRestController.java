package hongshop.hongshop.domain.couponHist;

import hongshop.hongshop.domain.couponHist.dto.HongCouponHistDTO;
import hongshop.hongshop.domain.couponHist.vo.HongCouponHistVO;
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
@Tag(name = "hong coupon hist rest controller", description = "사용자의 쿠폰 사용이력 Rest 컨트롤러")
public class HongCouponHistRestController {

    private final HongCouponHistService hongCouponHistService;

    @PostMapping("/coupon-hist")
    @Operation(summary = "insert coupon-hist", description = "사용자가 이용한 쿠폰 이력 저장하기")
    @ApiDocumentResponse
    public Response save(@RequestBody HongCouponHistDTO hongCouponHistDTO) {
        Long joinId = hongCouponHistService.join(hongCouponHistDTO);
        return Response.ok(joinId);
    }

    @GetMapping("/coupon-hist")
    @Operation(summary = "coupon-hist list", description = "사용자가 이용한 쿠폰 이력 전체 조회")
    @ApiDocumentResponse
    public Response list(){
        List<HongCouponHistVO> list = hongCouponHistService.list();
        return Response.ok(list);
    }
}
