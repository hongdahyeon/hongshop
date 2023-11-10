package hongshop.hongshop.domain.order;

import hongshop.hongshop.domain.order.vo.HongOrderVO;
import hongshop.hongshop.domain.user.HongUser;
import hongshop.hongshop.global.auth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/manager/order")
public class HongOrderManagerController {

    private final HongOrderService hongOrderService;
    @GetMapping({"/", ""})
    public String index(Model model, @AuthenticationPrincipal PrincipalDetails principalDetails){
        HongUser user = principalDetails.getUser();
        List<HongOrderVO> list = hongOrderService.listWithChkReview(user);
        model.addAttribute("orderList", list);
        return "manager/order";
    }
}