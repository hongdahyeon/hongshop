package hongshop.hongshop.domain.user;

import hongshop.hongshop.global.auth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class HongUserController {

    private final HongUserService hongUserService;

    @GetMapping("/myInfo")
    public String myInfo(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model){
        String userId = principalDetails.getUser().getUserId();
        model.addAttribute("user", hongUserService.getHongUserByUserId(userId));
        return "user/myInfo";
    }

}
