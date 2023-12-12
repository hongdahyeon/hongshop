package hongshop.hongshop.domain.userEnable;


import hongshop.hongshop.domain.userEnable.dto.HongUserEnableToDisableDTO;
import hongshop.hongshop.domain.userEnable.vo.HongUserEnableVO;
import hongshop.hongshop.global.response.ApiDocumentResponse;
import hongshop.hongshop.global.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Slf4j
@Tag(name = "hong user enable rest controller", description = "유저 계정 활성화에 대한 Rest 컨트롤러")
@RequiredArgsConstructor
public class HongUserEnableRestController {

    private final HongUserEnableService hongUserEnableService;

    @PostMapping("/user-enable")
    @Operation(summary = "user 사용자 계정 비활성화", description = "user 사용자 계정 비활성화")
    @ApiDocumentResponse
    public Response join(@RequestBody HongUserEnableToDisableDTO hongUserEnableToDisableDTO) {
        Long joinId = hongUserEnableService.join(hongUserEnableToDisableDTO);
        return Response.ok(joinId);
    }

    @GetMapping("/user-enable/{id}")
    @Operation(summary = "user 사용자 계정 비활성화 정보", description = "user 사용자 계정 비활성화 정보")
    @ApiDocumentResponse
    public Response view(@PathVariable Long id){
        HongUserEnableVO info = hongUserEnableService.getInfo(id);
        return Response.ok(info);
    }


    @PutMapping("/user-enable/{id}")
    @Operation(summary = "user 사용자 계정 비활성화 정보", description = "user 사용자 계정 비활성화 정보")
    @ApiDocumentResponse
    public Response update(@PathVariable Long id){
        hongUserEnableService.updateDisableToEnable(id);
        return Response.ok("해당 계정 비활성화가 풀렸습니다.");
    }

}