package hongshop.hongshop.domain.fileGroup;

import hongshop.hongshop.domain.fileGroup.impl.HongFileGroupServiceImpl;
import hongshop.hongshop.global.response.ApiDocumentResponse;
import hongshop.hongshop.global.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* @fileName HongFileGroupRestController
* @author dahyeon
* @version 1.0.0
* @date 2023-07-18
* @summary
**/

@RestController
@RequiredArgsConstructor
@Tag(name = "file group rest controller", description = "파일 그룹 Rest 컨트롤러")
@RequestMapping("/api")
public class HongFileGroupRestController {

    private final HongFileGroupService hongFileGroupService;

    @GetMapping("/file-group/{id}")
    @Operation(summary = "get file group view with files", description = "파일 그룹 정보 & 파일 리스트")
    @ApiDocumentResponse
    public Response viewWithFiles(@PathVariable Long id){
        HongFileGroupVO viewWithFiles = hongFileGroupService.list(id);
        return Response.ok(viewWithFiles);
    }
}
