package hongshop.hongshop.domain.deliver;

import hongshop.hongshop.domain.deliver.dto.HongDeliverDTO;
import hongshop.hongshop.domain.deliver.vo.HongDeliverVO;
import hongshop.hongshop.global.response.ApiDocumentResponse;
import hongshop.hongshop.global.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/deliver/{id}")
    @Operation(summary = "get deliver view", description = "배송 리스트 가져오기")
    @ApiDocumentResponse
    public Response view(@PathVariable Long id){
        HongDeliverVO view = hongDeliverService.view(id);
        return Response.ok(view);
    }

    @PutMapping("/deliver/{id}")
    @Operation(summary = "update deliver", description = "배송 정보 업데이트")
    @ApiDocumentResponse
    public Response update(@PathVariable Long id, @RequestBody HongDeliverDTO hongDeliverDTO){
        hongDeliverService.update(hongDeliverDTO, id);
        return Response.ok("updated");
    }
}
