package hongshop.hongshop.domain.post;

import hongshop.hongshop.global.response.ApiDocumentResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<List<HongPostVO>> list(){
        List<HongPostVO> list = hongPostService.list();
        return ResponseEntity.ok(list);
    }

    @PostMapping("/post")
    @Operation(summary = "insert post", description = "게시글 저장")
    @ApiDocumentResponse
    public ResponseEntity<Long> save(@RequestBody HongPostDTO hongPostDTO){
        Long joinId = hongPostService.join(hongPostDTO);
        return ResponseEntity.ok(joinId);
    }

    @GetMapping("/post/{id}")
    @Operation(summary = "get post view", description = "단건 게시글 조회")
    @ApiDocumentResponse
    public ResponseEntity<HongPostVO> show(@PathVariable Long id){
        HongPostVO show = hongPostService.show(id);
        return ResponseEntity.ok(show);
    }

    @GetMapping("/postWithAnswer/{id}")
    @Operation(summary = "get post view with answer list", description = "단건 게시글 조회 & 댓글 리스트")
    @ApiDocumentResponse
    public ResponseEntity<HongPostVO> showWithAnswer(@PathVariable Long id){
        HongPostVO hongPostVO = hongPostService.postWithAnswer(id);
        return ResponseEntity.ok(hongPostVO);
    }

    @PutMapping("/post/{id}")
    @Operation(summary = "update post", description = "게시글 업데이트")
    @ApiDocumentResponse
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody HongPostDTO hongPostDTO){
        hongPostService.update(hongPostDTO, id);
        return ResponseEntity.ok("updated");
    }

    @DeleteMapping("/post/{id}")
    @Operation(summary = "delete post", description = "게시글 삭제")
    @ApiDocumentResponse
    public ResponseEntity<String> delete(@PathVariable Long id){
        hongPostService.delete(id);
        return ResponseEntity.ok("deleted");
    }

}
