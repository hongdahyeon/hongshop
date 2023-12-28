package hongshop.hongshop.domain.answer;

import hongshop.hongshop.domain.answer.dto.HongAnswerDTO;
import hongshop.hongshop.domain.answer.vo.HongAnswerUserVO;
import hongshop.hongshop.domain.answer.vo.HongAnswerVO;
import hongshop.hongshop.global.response.ApiDocumentResponse;
import hongshop.hongshop.global.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public Response list(){
        List<HongAnswerVO> list = hongAnswerService.list();
        return Response.ok(list);
    }

    @GetMapping("/answerByPost/{id}")
    @Operation(summary = "get answer list by PostId", description = "게시글의 답변 리스트 가져오기")
    @ApiDocumentResponse
    public Response list(@PathVariable Long id){
        List<HongAnswerUserVO> list = hongAnswerService.listByHongPostId(id);
        return Response.ok(list);
    }

    @PostMapping("/answer")
    @Operation(summary = "insert answer", description = "답변 저장")
    @ApiDocumentResponse
    public Response save(@Valid @RequestBody HongAnswerDTO hongAnswerDTO){
        Long joinId = hongAnswerService.join(hongAnswerDTO);
        return Response.ok(joinId);
    }

    @GetMapping("/answer/{id}")
    @Operation(summary = "get answer view", description = "단건 답변 조회")
    @ApiDocumentResponse
    public Response show(@PathVariable Long id){
        HongAnswerUserVO show = hongAnswerService.show(id);
        return Response.ok(show);
    }


    @PutMapping("/answer/{id}")
    @Operation(summary = "update answer", description = "답변 업데이트")
    @ApiDocumentResponse
    public Response update(@PathVariable Long id, @Valid @RequestBody HongAnswerDTO hongAnswerDTO){
        hongAnswerService.update(hongAnswerDTO, id);
        return Response.ok("updated");
    }

    @DeleteMapping("/answer/{id}")
    @Operation(summary = "delete answer", description = "답변 삭제")
    @ApiDocumentResponse
    public Response delete(@PathVariable Long id){
        hongAnswerService.delete(id);
        return Response.ok("deleted");
    }
}
