package hongshop.hongshop.domain.category;


import hongshop.hongshop.global.response.ApiDocumentResponse;
import hongshop.hongshop.global.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @fileName HongCategoryRestController
 * @author dahyeon
 * @version 1.0.0
 * @date 2023-07-17
 * @summary
 **/

@RestController
@RequestMapping("/api")
@Tag(name = "hong category rest controller", description = "카테고리 Rest 컨트롤러")
@RequiredArgsConstructor
public class HongCategoryRestController {

    private final HongCategoryService hongCategoryService;

    @GetMapping("/category")
    @Operation(summary = "get category list", description = "물품 카테고리 리스트 가져오기")
    @ApiDocumentResponse
    public Response list(){
        List<HongCategoryVO> list = hongCategoryService.list();
        return Response.ok(list);
    }

    @GetMapping("/category-product")
    @Operation(summary = "get category list with product", description = "물품 카테고리 & 물품들 리스트 가져오기")
    @ApiDocumentResponse
    public Response listWithProduct(){
        List<HongCategoryVO> list = hongCategoryService.listWithProduct();
        return Response.ok(list);
    }

    @PostMapping("/category")
    @Operation(summary = "insert category", description = "카테고리 저장")
    @ApiDocumentResponse
    public Response save(@Valid @RequestBody HongCategoryDTO hongCategoryDTO){
        Long joinId = hongCategoryService.join(hongCategoryDTO);
        return Response.ok(joinId);
    }

    @GetMapping("/category/{id}")
    @Operation(summary = "get category view", description = "물품 카테고리 단건 조회")
    @ApiDocumentResponse
    public Response view(@PathVariable Long id){
        HongCategoryVO show = hongCategoryService.show(id);
        return Response.ok(show);
    }

    @GetMapping("/category-product/{id}")
    @Operation(summary = "get category&product view", description = "물품 카테고리&물품들 단건 조회")
    @ApiDocumentResponse
    public Response viewWithProduct(@PathVariable Long id){
        HongCategoryVO show = hongCategoryService.showWithProduct(id);
        return Response.ok(show);
    }

    @PutMapping("/category/{id}")
    @Operation(summary = "update category", description = "물품 카테고리 업데이트")
    @ApiDocumentResponse
    public Response update(@PathVariable Long id, @Valid @RequestBody HongCategoryDTO hongCategoryDTO){
        hongCategoryService.update(hongCategoryDTO, id);
        return Response.ok("updated");
    }

    @DeleteMapping("/category/{id}")
    @Operation(summary = "delete category", description = "물품 카테고리 삭제")
    @ApiDocumentResponse
    public Response delete(@PathVariable Long id) {
        hongCategoryService.delete(id);
        return Response.ok("deleted");
    }

}
