package hongshop.hongshop.domain.orderDetail;

import hongshop.hongshop.domain.orderDetail.vo.HongOrderDetailUserVO;
import hongshop.hongshop.domain.orderDetail.vo.HongOrderDetailVO;
import hongshop.hongshop.global.response.ApiDocumentResponse;
import hongshop.hongshop.global.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Tag(name = "hong order detail rest controller", description = "주문 상세 정보 Rest 컨트롤러")
@RequestMapping("/api")
@RequiredArgsConstructor
public class HongOrderDetailRestController {

    private final HongOrderDetailService hongOrderDetailService;

    @GetMapping("/order-user/{id}")
    @Operation(summary = "order user list by productId", description = "productId를 주문한 사용자 리스트 조회")
    @ApiDocumentResponse
    public Response orderUser(@PathVariable Long id){
        List<HongOrderDetailUserVO> list = hongOrderDetailService.listByProductId(id);
        return Response.ok(list);
    }

    @GetMapping("/order-detail/{id}")
    @Operation(summary = "order detail list by orderId", description = "주문Id로 상세 주문 정보 불러오기")
    @ApiDocumentResponse
    public Response orderDetail(@PathVariable Long id){
        List<HongOrderDetailVO> list = hongOrderDetailService.listOfDetailOrders(id);
        return Response.ok(list);
    }
}