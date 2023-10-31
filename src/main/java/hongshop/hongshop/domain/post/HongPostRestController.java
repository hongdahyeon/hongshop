package hongshop.hongshop.domain.post;

import hongshop.hongshop.domain.post.dto.HongPostDTO;
import hongshop.hongshop.domain.post.vo.HongPostVO;
import hongshop.hongshop.global.response.ApiDocumentResponse;
import hongshop.hongshop.global.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @fileName HongPostRestController
 * @author dahyeon
 * @version 1.0.0
 * @date 2023-07-17
 * @summary
 **/

@RestController
@RequestMapping("/api")
@Tag(name = "hong post rest controller", description = "게시글 Rest 컨트롤러")
@RequiredArgsConstructor
public class HongPostRestController {

    private final HongPostService hongPostService;

    @GetMapping("/post")
    @Operation(summary = "get post list", description = "게시글 리스트 가져오기")
    @ApiDocumentResponse
    public Response list(){
        List<HongPostVO> list = hongPostService.list();
        return Response.ok(list);
    }

    @PostMapping("/post")
    @Operation(summary = "insert post", description = "게시글 저장")
    @ApiDocumentResponse
    public Response save(@Valid @RequestBody HongPostDTO hongPostDTO){
        Long joinId = hongPostService.join(hongPostDTO);
        return Response.ok(joinId);
    }

    @GetMapping("/post/{id}")
    @Operation(summary = "get post view", description = "단건 게시글 조회")
    @ApiDocumentResponse
    public Response show(@PathVariable Long id){
        HongPostVO show = hongPostService.show(id);
        return Response.ok(show);
    }

    @GetMapping("/postWithAnswer/{id}")
    @Operation(summary = "get post view with answer list", description = "단건 게시글 조회 & 댓글 리스트")
    @ApiDocumentResponse
    public Response showWithAnswer(@PathVariable Long id){
        HongPostVO hongPostVO = hongPostService.postWithAnswer(id);
        return Response.ok(hongPostVO);
    }

    @GetMapping("/postWithFile/{id}")
    @Operation(summary = "get post view with file list", description = "단건 게시글 조회 & 파일 리스트")
    @ApiDocumentResponse
    public Response showWithFile(@PathVariable Long id){
        HongPostVO hongPostVO = hongPostService.postWithFile(id);
        return Response.ok(hongPostVO);
    }

    @GetMapping("/postsWithFileByPostType/{id}")
    @Operation(summary = "get posts with file list by post type", description = "게시글 타입을 통해 게시글들 & 파일 리스트")
    @ApiDocumentResponse
    public Response postsWithFileByPostType(@PathVariable Long id){
        List<HongPostVO> hongPostVOS = hongPostService.postsWithFileByPostType(id);
        hongPostVOS.forEach(hongPostVO -> {
            hongPostVO.setContent(StringEscapeUtils.unescapeHtml4(hongPostVO.getContent()));
        });
        return Response.ok(hongPostVOS);
    }

    @PutMapping("/post/{id}")
    @Operation(summary = "update post", description = "게시글 업데이트")
    @ApiDocumentResponse
    public Response update(@PathVariable Long id, @Valid @RequestBody HongPostDTO hongPostDTO){
        hongPostService.update(hongPostDTO, id);
        return Response.ok("updated");
    }

    @DeleteMapping("/post/{id}")
    @Operation(summary = "delete post", description = "게시글 삭제")
    @ApiDocumentResponse
    public Response delete(@PathVariable Long id){
        hongPostService.delete(id);
        return Response.ok("deleted");
    }

}
