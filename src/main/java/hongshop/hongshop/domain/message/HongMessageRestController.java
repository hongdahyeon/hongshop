package hongshop.hongshop.domain.message;

import hongshop.hongshop.domain.message.dto.HongMessageDTO;
import hongshop.hongshop.global.response.ApiDocumentResponse;
import hongshop.hongshop.global.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        Long joinId = hongMessageService.join(hongMessageDTO);
        return Response.ok(joinId);
    }

}