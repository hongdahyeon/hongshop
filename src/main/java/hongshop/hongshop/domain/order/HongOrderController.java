package hongshop.hongshop.domain.order;

import hongshop.hongshop.global.auth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
public class HongOrderController {

    private final HongOrderService hongOrderService;

    @GetMapping({"", "/"})
    public String index(@RequestParam(name = "ids", required = false) List<Long> ids, Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        Long userid = principalDetails.getUser().getId();
        model.addAttribute("ids", ids);
        model.addAttribute("userid", userid);
        return "order/index";
    }

}
