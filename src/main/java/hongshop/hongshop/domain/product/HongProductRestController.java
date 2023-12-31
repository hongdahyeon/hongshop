package hongshop.hongshop.domain.product;

import hongshop.hongshop.domain.product.dto.HongProductDTO;
import hongshop.hongshop.domain.product.vo.HongProductManagerVO;
import hongshop.hongshop.global.response.ApiDocumentResponse;
import hongshop.hongshop.global.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
* @fileName HongProductRestController
* @author dahyeon
* @version 1.0.0
* @date 2023-07-18
* @summary
**/

@RestController
@Tag(name = "hong product rest controller", description = "상품 Rest 컨트롤러")
@RequestMapping("/api")
@RequiredArgsConstructor
public class HongProductRestController {

    private final HongProductService hongProductService;

    @PostMapping("/product")
    @Operation(summary = "insert product", description = "물품 저장")
    @ApiDocumentResponse
    public Response save(@RequestBody HongProductDTO hongProductDTO){
        Long saveId = hongProductService.save(hongProductDTO);
        return Response.ok(saveId);
    }

    @GetMapping("/product-check-user/{id}")
    @Operation(summary = "get product view and check order user", description = "물품 단건 조회 그리고 해당 물품 사용자 있는지 유무")
    @ApiDocumentResponse
    public Response viewCheckUser(@PathVariable Long id){
        HongProductManagerVO view = hongProductService.viewCheckUser(id);
        return Response.ok(view);
    }

    @PutMapping("/product/{id}")
    @Operation(summary = "update product", description = "물품 수정")
    @ApiDocumentResponse
    public Response update(@PathVariable Long id, @RequestBody HongProductDTO hongProductDTO){
        hongProductService.update(hongProductDTO, id);
        return Response.ok("updated");
    }

    @DeleteMapping("/product/{id}")
    @Operation(summary = "delete product", description = "물품 삭제")
    @ApiDocumentResponse
    public Response delete(@PathVariable Long id) {
        hongProductService.delete(id);
        return Response.ok("삭제되었습니다.");
    }
}
