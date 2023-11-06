package hongshop.hongshop.domain.postType;

import hongshop.hongshop.domain.postType.dto.HongPostTypeDTO;
import hongshop.hongshop.domain.postType.vo.HongPostTypeVO;
import hongshop.hongshop.global.response.ApiDocumentResponse;
import hongshop.hongshop.global.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
* @fileName HongPostTypeRestController
* @author dahyeon
* @version 1.0.0
* @date 2023-08-08
* @summary
**/

@RestController
@RequestMapping("/api")
@Tag(name = "hong post type rest controller", description = "게시판 - 게시글 타입 Rest 컨트롤러")
@RequiredArgsConstructor
public class HongPostTypeRestController {

    private final HongPostTypeService hongPostTypeService;

    @GetMapping("/type")
    @Operation(summary = "get post type list", description = "게시글 타입 리스트 가져오기")
    @ApiDocumentResponse
    public Response list(){
        List<HongPostTypeVO> list = hongPostTypeService.list();
        return Response.ok(list);
    }

    @GetMapping("/type/header")
    @Operation(summary = "get post type list for header", description = "게시글 타입 리스트 가져오기 - 헤더용")
    @ApiDocumentResponse
    public Response listForHeader(){
        List<HongPostTypeVO> list = hongPostTypeService.listForHeader();
        return Response.ok(list);
    }

    @PostMapping("/type")
    @Operation(summary = "insert post type", description = "게시글 타입 저장하기")
    @ApiDocumentResponse
    public Response save(@RequestBody HongPostTypeDTO hongPostTypeDTO){
        Long joinId = hongPostTypeService.join(hongPostTypeDTO);
        return Response.ok(joinId);
    }

    @GetMapping("/listWithPost")
    @Operation(summary = "get post type list with post", description = "게시글 타입 리스트 with 게시글들 가져오기")
    @ApiDocumentResponse
    public Response listWithPost(){
        List<HongPostTypeVO> hongPostTypeVOS = hongPostTypeService.listWithPost();
        return Response.ok(hongPostTypeVOS);
    }

    @GetMapping("/type/{id}")
    @Operation(summary = "get post type view", description = "게시글 타입 단건 조회")
    @ApiDocumentResponse
    public Response view(@PathVariable Long id){
        HongPostTypeVO view = hongPostTypeService.view(id);
        return Response.ok(view);
    }

    @PutMapping("/type/{id}")
    @Operation(summary = "update post type", description = "게시글 타입 수정")
    @ApiDocumentResponse
    public Response update(@PathVariable Long id, @RequestBody HongPostTypeDTO hongPostTypeDTO) {
        hongPostTypeService.update(id, hongPostTypeDTO);
        return Response.ok("게시판 유형이 수정되었습니다.");
    }

    @DeleteMapping("/type/{id}")
    @Operation(summary = "delete post type", description = "게시글 타입 삭제")
    @ApiDocumentResponse
    public Response delete(@PathVariable Long id){
        hongPostTypeService.delete(id);
        return Response.ok("게시판 유형이 삭제되었습니다.");
    }

}
