package hongshop.hongshop.domain.fileLog;

import hongshop.hongshop.domain.fileLog.vo.HongFileLogVO;
import hongshop.hongshop.global.response.ApiDocumentResponse;
import hongshop.hongshop.global.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Tag(name = "hong file log rest controller", description = "파일 로그 Rest 컨트롤러")
@RequestMapping("/api")
@RequiredArgsConstructor
public class HongFileLogRestController {

    private final HongFileLogService hongFileLogService;

    @GetMapping("/log/{id}")
    @Operation(summary = "get file's download log list", description = "파일의 다운로드 로그 기록 리스트 조회")
    @ApiDocumentResponse
    public Response list(@PathVariable Long id) {
        List<HongFileLogVO> list = hongFileLogService.list(id);
        return Response.ok(list);
    }
}
