package hongshop.hongshop.domain.userDisable;


import hongshop.hongshop.domain.userDisable.dto.HongUserEnableToDisableDTO;
import hongshop.hongshop.domain.userDisable.vo.HongUserDisableVO;
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
@Tag(name = "hong user disable rest controller", description = "유저 계정 비활성화에 대한 Rest 컨트롤러")
@RequiredArgsConstructor
public class HongUserDisableRestController {

    private final HongUserDisableService hongUserDisableService;

    @PostMapping("/user-disable")
    @Operation(summary = "user 사용자 계정 비활성화", description = "user 사용자 계정 비활성화")
    @ApiDocumentResponse
    public Response join(@RequestBody HongUserEnableToDisableDTO hongUserEnableToDisableDTO) {
        Long joinId = hongUserDisableService.join(hongUserEnableToDisableDTO);
        return Response.ok(joinId);
    }

    @GetMapping("/user-disable/{id}")
    @Operation(summary = "user 사용자 계정 비활성화 정보", description = "user 사용자 계정 비활성화 정보")
    @ApiDocumentResponse
    public Response view(@PathVariable Long id){
        HongUserDisableVO info = hongUserDisableService.getInfo(id);
        return Response.ok(info);
    }


    @PutMapping("/user-enable/{id}")
    @Operation(summary = "user 사용자 계정 비활성화 풀기", description = "user 사용자 계정 비활성화 풀기")
    @ApiDocumentResponse
    public Response update(@PathVariable Long id){
        hongUserDisableService.updateDisableToEnable(id);
        return Response.ok("해당 계정 비활성화가 풀렸습니다.");
    }

}