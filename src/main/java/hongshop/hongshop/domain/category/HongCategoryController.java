package hongshop.hongshop.domain.category;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/category")
public class HongCategoryController {

    @GetMapping({"/", ""})
    public String index(){
        return "manage/category";
    }
}
