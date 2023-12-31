package hongshop.hongshop.domain.deliver;

import hongshop.hongshop.domain.deliver.dto.HongDeliverAddressDTO;
import hongshop.hongshop.domain.deliver.dto.HongDeliverStatusDTO;
import hongshop.hongshop.domain.deliver.vo.HongDeliverReviewChkVO;
import hongshop.hongshop.global.response.ApiDocumentResponse;
import hongshop.hongshop.global.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
* @fileName HongDeliverRestController
* @author dahyeon
* @version 1.0.0
* @date 2023-07-19
* @summary
**/

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "hong deliver rest controller", description = "배송 Rest 컨트롤러")
public class HongDeliverRestController {

    private final HongDeliverService hongDeliverService;

    @PutMapping("/deliver-status/{id}")
    @Operation(summary = "update deliver status", description = "배송 상태 정보 업데이트")
    @ApiDocumentResponse
    public Response updateStatus(@PathVariable Long id, @RequestBody HongDeliverStatusDTO hongDeliverStatusDTO){
        hongDeliverService.updateStatus(hongDeliverStatusDTO, id);
        return Response.ok("해당 배송 상태정보를 변경하였습니다.");
    }

    @PutMapping("/deliver-address/{id}")
    @Operation(summary = "update deliver address", description = "배송 주소 정보 업데이트")
    @ApiDocumentResponse
    public Response updateAddress(@PathVariable Long id, @RequestBody HongDeliverAddressDTO hongDeliverAddressDTO) {
        hongDeliverService.updateAddres(id, hongDeliverAddressDTO);
        return Response.ok("해당 배송 주소정보를 변경하였습니다.");
    }

    @GetMapping("/deliver-with-reviewChk")
    @Operation(summary = "deliver 리스트 조회, 해당 주문 건들에 대해 리뷰 작성 여부 체크", description = "deliver 리스트 조회, 해당 주문 건들에 대해 리뷰 작성 여부 체크")
    @ApiDocumentResponse
    public Response deliverWithReviewChk(){
        List<HongDeliverReviewChkVO> list = hongDeliverService.allWithChkReview();
        return Response.ok(list);
    }

}
