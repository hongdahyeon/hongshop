package hongshop.hongshop.domain.order;

import hongshop.hongshop.domain.order.dto.HongOrderFromCartDTO;
import hongshop.hongshop.domain.order.dto.HongOrderFromShopDTO;
import hongshop.hongshop.domain.order.dto.HongOrderStatusDTO;
import hongshop.hongshop.domain.order.vo.HongManagerOrderReviewVO;
import hongshop.hongshop.domain.order.vo.HongOrderDeliverVO;
import hongshop.hongshop.domain.order.vo.HongUserOrderReviewVO;
import hongshop.hongshop.global.auth.PrincipalDetails;
import hongshop.hongshop.global.response.ApiDocumentResponse;
import hongshop.hongshop.global.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
* @fileName HongOrderRestController
* @author dahyeon
* @version 1.0.0
* @date 2023-07-18
* @summary
**/

@RestController
@RequestMapping("/api")
@Tag(name = "hong order rest controller", description = "주문 Rest 컨트롤러")
@RequiredArgsConstructor
public class HongOrderRestController {

    private final HongOrderService hongOrderService;

    @PostMapping("/order-from-cart")
    @Operation(summary = "insert order", description = "주문 저장 - 결제하기 from 장바구니")
    @ApiDocumentResponse
    public Response saveFromCart(@RequestBody HongOrderFromCartDTO hongOrderFromCartDTO){
        Long saveId = hongOrderService.saveFromCart(hongOrderFromCartDTO);
        return Response.ok(saveId);
    }

    @PostMapping("/order-from-shop")
    @Operation(summary = "insert order", description = "주문 저장 - 결제하기 from 상품 화면")
    @ApiDocumentResponse
    public Response saveFromShop(@RequestBody HongOrderFromShopDTO hongOrderFromShopDTO){
        Long saveId = hongOrderService.saveFromShop(hongOrderFromShopDTO);
        return Response.ok(saveId);
    }

    @PutMapping("/order-status/{id}")
    @Operation(summary = "update status of order", description = "주문정보에서 주문 상태값 변경")
    @ApiDocumentResponse
    public Response updateStatus(@PathVariable Long id, @RequestBody HongOrderStatusDTO hongOrderStatusDTO) {
        hongOrderService.updateStatus(id, hongOrderStatusDTO);
        return Response.ok("해당 상품의 상태값이 변경되었습니다.");
    }

    @GetMapping("/order-review/{id}")
    @Operation(summary = "orderId 하위에 있는 orderDetail들에 대해 리뷰달렸는지 찾기", description = "orderId 하위에 있는 orderDetail들에 대해 리뷰달렸는지 찾기")
    @ApiDocumentResponse
    public Response orderReview(@PathVariable Long id, @AuthenticationPrincipal PrincipalDetails principalDetails){
        List<HongUserOrderReviewVO> orderDetailReviews = hongOrderService.getOrderDetailReviews(id, principalDetails.getUser());
        return Response.ok(orderDetailReviews);
    }

    @GetMapping("/order-with-reviewChk")
    @Operation(summary = "orderId 리스트 조회, 해당 주문 건들에 대해 리뷰 작성 여부 체크", description = "orderId 리스트 조회, 해당 주문 건들에 대해 리뷰 작성 여부 체크")
    @ApiDocumentResponse
    public Response orderWithReviewChk(){
        List<HongManagerOrderReviewVO> list = hongOrderService.listWithChkReview();
        return Response.ok(list);
    }

    @GetMapping("/order-with-deliver")
    @Operation(summary = "로그인한 사용자의 주문 정보 & 배송 정보 조회", description = "로그인한 사용자의 주문 정보 & 배송 정보 조회")
    @ApiDocumentResponse
    public Response orderWithDetailAndDeliver(@AuthenticationPrincipal PrincipalDetails principalDetails){
        List<HongOrderDeliverVO> list = hongOrderService.getOrderAndDeliverByUserId(principalDetails.getUser().getId());
        return Response.ok(list);
    }
}
