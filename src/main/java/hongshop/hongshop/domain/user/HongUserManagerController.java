package hongshop.hongshop.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/manager/user")
@RequiredArgsConstructor
public class HongUserManagerController {

    private final HongUserService hongUserService;
    @GetMapping({"", "/"})
    public String index(Model model){
        List<HongUserVO> list = hongUserService.list();
        model.addAttribute("userList", list);
        return "/manage/user";
    }
}
