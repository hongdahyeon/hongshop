package hongshop.hongshop.domain.deliver;

import hongshop.hongshop.global.response.Response;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "hong deliver rest controller", description = "배송 Rest 컨트롤러")
public class HongDeliverRestController {

    private final HongDeliverService hongDeliverService;

    @GetMapping("/deliver/{id}")
    public Response view(@PathVariable Long id){
        HongDeliverVO view = hongDeliverService.view(id);
        return Response.ok(view);
    }
}
