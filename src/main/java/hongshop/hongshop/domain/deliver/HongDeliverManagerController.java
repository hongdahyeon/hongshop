package hongshop.hongshop.domain.deliver;

import hongshop.hongshop.domain.deliver.vo.HongDeliverVO;
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
@RequestMapping("/manager")
@RequiredArgsConstructor
public class HongDeliverManagerController {

    private final HongDeliverService hongDeliverService;

    @GetMapping("/deliver")
    public String deliver(Model model, @AuthenticationPrincipal PrincipalDetails principalDetails){
        HongUser user = principalDetails.getUser();
        List<HongDeliverVO> all = hongDeliverService.allWithChkReview(user);
        model.addAttribute("delivers", all);
        return "manager/deliver";
    }
}
