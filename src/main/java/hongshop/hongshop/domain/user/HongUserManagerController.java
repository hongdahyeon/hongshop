package hongshop.hongshop.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/manager/user")
@RequiredArgsConstructor
public class HongUserManagerController {

    private final HongUserService hongUserService;
    @GetMapping({"", "/"})
    public String index(){
        return "/manager/user";
    }
}
