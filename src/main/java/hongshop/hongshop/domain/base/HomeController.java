package hongshop.hongshop.domain.base;

import hongshop.hongshop.domain.user.HongUserService;
import hongshop.hongshop.global.auth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final HongUserService hongUserService;

    @GetMapping("/login")
    public String login(){
        return "/login";
    }

    @GetMapping({"/", ""})
    public String index(@AuthenticationPrincipal PrincipalDetails principalDetails){
        if(principalDetails == null) return "/login";
        return "index";
    }
}
