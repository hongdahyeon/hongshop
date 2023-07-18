package hongshop.hongshop.domain.fileLog;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "hong file log rest controller", description = "파일 로그 Rest 컨트롤러")
@RequestMapping("/api")
@RequiredArgsConstructor
public class HongFileLogRestController {

    private final HongFileLogService hongFileLogService;
}
