package hongshop.hongshop.domain.userDisable;


import hongshop.hongshop.domain.userDisable.vo.HongUserDisableVO;
import hongshop.hongshop.global.response.ApiDocumentResponse;
import hongshop.hongshop.global.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
* @fileName HongUserDisableFrontRestController
* @author dahyeon
* @version 1.0.0
* @date 2023-12-12
* @summary
**/

@RestController
@RequestMapping("/front/api")
@Slf4j
@Tag(name = "hong user disable front rest controller", description = "유저 계정 비활성화 Front Rest 컨트롤러")
@RequiredArgsConstructor
public class HongUserDisableFrontRestController {

    private final HongUserDisableService hongUserDisableService;

    @GetMapping("/user-disable/{userId}")
    @Operation(summary = "user 사용자 계정 비활성화 메시지 정보", description = "user 사용자 계정 비활성화 메시지 정보")
    @ApiDocumentResponse
    public Response view(@PathVariable String userId) {
        HongUserDisableVO view = hongUserDisableService.view(userId);
        return Response.ok(view);
    }

}