package hongshop.hongshop.domain.order;

import hongshop.hongshop.domain.order.dto.HongOrderDTO;
import hongshop.hongshop.domain.order.dto.HongOrderFromCartDTO;
import hongshop.hongshop.domain.order.dto.HongOrderStatusDTO;
import hongshop.hongshop.domain.order.vo.HongOrderVO;
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

    @PostMapping("/order")
    @Operation(summary = "insert order", description = "주문 저장")
    @ApiDocumentResponse
    public Response save(@RequestBody List<HongOrderDTO> hongOrderDTO, @AuthenticationPrincipal PrincipalDetails principalDetails){
        if(principalDetails == null) throw new IllegalArgumentException("you need to login first");
        Long saveId = hongOrderService.save(hongOrderDTO, principalDetails.getUser());
        return Response.ok(saveId);
    }

    @PostMapping("/order-from-cart")
    @Operation(summary = "insert order", description = "주문 저장")
    @ApiDocumentResponse
    public Response saveFromCart(@RequestBody List<HongOrderFromCartDTO> hongOrderDTO, @AuthenticationPrincipal PrincipalDetails principalDetails){
        Long saveId = hongOrderService.saveFromCart(hongOrderDTO, principalDetails.getUser());
        return Response.ok(saveId);
    }

    @GetMapping("/order")
    @Operation(summary = "order list", description = "주문 정보 조회")
    @ApiDocumentResponse
    public Response list(){
        List<HongOrderVO> list = hongOrderService.list();
        return Response.ok(list);
    }

    @GetMapping("/order/{id}")
    @Operation(summary = "get view of order with details", description = "주문 & 상세 주문 정보 단건 조회")
    @ApiDocumentResponse
    public Response view(@PathVariable Long id){
        HongOrderVO view = hongOrderService.view(id);
        return Response.ok(view);
    }

    @GetMapping("/user-order")
    @Operation(summary = "get view of user order with details", description = "로그인한 사용자의 주문 & 상세 주문 정보 단건 조회")
    @ApiDocumentResponse
    public Response listOfUserOrder(@AuthenticationPrincipal PrincipalDetails principalDetails){
        if(principalDetails == null) throw new IllegalArgumentException("you need to login first");
        List<HongOrderVO> hongOrderVOS = hongOrderService.listOfUserOrder(principalDetails.getUser().getId());
        return Response.ok(hongOrderVOS);
    }

    @PutMapping("/orderStatus/{id}")
    @Operation(summary = "update status of order", description = "주문정보에서 주문 상태값 변경")
    @ApiDocumentResponse
    public Response updateStatus(@PathVariable Long id, @RequestBody HongOrderStatusDTO hongOrderStatusDTO) {
        hongOrderService.updateStatus(id, hongOrderStatusDTO);
        return Response.ok("해당 상품의 상태값이 변경되었습니다.");
    }

}
