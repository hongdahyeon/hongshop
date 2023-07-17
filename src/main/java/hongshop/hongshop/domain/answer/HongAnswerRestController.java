package hongshop.hongshop.domain.answer;

import hongshop.hongshop.global.response.ApiDocumentResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @fileName HongAnswerRepository
 * @author dahyeon
 * @version 1.0.0
 * @date 2023-07-17
 * @summary
 **/

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "hong answer rest controller", description = "게시글에 대한 답변 Rest 컨트롤러")
public class HongAnswerRestController {

    private final HongAnswerService hongAnswerService;

    @GetMapping("/answer")
    @Operation(summary = "get answer list", description = "전체 답변 리스트 가져오기")
    @ApiDocumentResponse
    public ResponseEntity<List<HongAnswerVO>> list(){
        List<HongAnswerVO> list = hongAnswerService.list();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/answerByPost/{id}")
    @Operation(summary = "get answer list by PostId", description = "게시글의 답변 리스트 가져오기")
    @ApiDocumentResponse
    public ResponseEntity<List<HongAnswerVO>> list(@PathVariable Long id){
        List<HongAnswerVO> list = hongAnswerService.listByHongPostId(id);
        return ResponseEntity.ok(list);
    }

    @PostMapping("/answer")
    @Operation(summary = "insert answer", description = "답변 저장")
    @ApiDocumentResponse
    public ResponseEntity<Long> save(@RequestBody HongAnswerDTO hongAnswerDTO){
        Long joinId = hongAnswerService.join(hongAnswerDTO);
        return ResponseEntity.ok(joinId);
    }

    @GetMapping("/answer/{id}")
    @Operation(summary = "get answer view", description = "단건 답변 조회")
    @ApiDocumentResponse
    public ResponseEntity<HongAnswerVO> show(@PathVariable Long id){
        HongAnswerVO show = hongAnswerService.show(id);
        return ResponseEntity.ok(show);
    }


    @PutMapping("/answer/{id}")
    @Operation(summary = "update answer", description = "답변 업데이트")
    @ApiDocumentResponse
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody HongAnswerDTO hongAnswerDTO){
        hongAnswerService.update(hongAnswerDTO, id);
        return ResponseEntity.ok("updated");
    }

    @DeleteMapping("/answer/{id}")
    @Operation(summary = "delete answer", description = "답변 삭제")
    @ApiDocumentResponse
    public ResponseEntity<String> delete(@PathVariable Long id){
        hongAnswerService.delete(id);
        return ResponseEntity.ok("deleted");
    }
}
