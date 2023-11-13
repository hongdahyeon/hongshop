package hongshop.hongshop.domain.base;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomeController {

    @GetMapping("/login")
    public String login(){
        return "/login";
    }

    @GetMapping({"/", ""})
    public String index(){
        return "index";
    }

    @GetMapping("/front/initialPassword")
    public String initialPassword(){
        return "initialPassword";
    }

    @GetMapping("/talk")
    public String talk(){
        return "talk/talk";
    }
}
