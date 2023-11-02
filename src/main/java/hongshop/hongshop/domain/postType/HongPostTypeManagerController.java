package hongshop.hongshop.domain.postType;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("/manager")
@Controller
public class HongPostTypeManagerController {

    @GetMapping("/postType")
    public String index(){
        return "/manage/postType";
    }
}
