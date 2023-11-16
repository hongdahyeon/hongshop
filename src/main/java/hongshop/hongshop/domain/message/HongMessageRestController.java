package hongshop.hongshop.domain.message;

import hongshop.hongshop.domain.message.dto.HongMessageDTO;
import hongshop.hongshop.domain.message.vo.HongMessageVO;
import hongshop.hongshop.global.auth.PrincipalDetails;
import hongshop.hongshop.global.response.ApiDocumentResponse;
import hongshop.hongshop.global.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "hong message rest controller", description = "메시지 Rest 컨트롤러")
@RequestMapping("/api")
public class HongMessageRestController {

    private final HongMessageService hongMessageService;

    @PostMapping("/message")
    @Operation(summary = "insert message", description = "메시지 저장")
    @ApiDocumentResponse
    public Response join(@RequestBody HongMessageDTO hongMessageDTO) {
        List<HongMessageVO> getMessage = hongMessageService.join(hongMessageDTO);
        return Response.ok(getMessage);
    }

    @GetMapping("/message/{id}")
    @Operation(summary = "get message list", description = "메시지 리스트 조회")
    @ApiDocumentResponse
    public Response getMsgLst(@PathVariable Long id, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        List<HongMessageVO> messageLst = hongMessageService.getMessageLst(id, principalDetails.getUser().getId());
        return Response.ok(messageLst);
    }

}