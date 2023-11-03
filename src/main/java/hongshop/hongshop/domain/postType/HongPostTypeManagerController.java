package hongshop.hongshop.domain.postType;

import hongshop.hongshop.domain.postType.vo.HongPostTypeVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/manager")
@Controller
public class HongPostTypeManagerController {

    private final HongPostTypeService hongPostTypeService;

    @GetMapping("/postType")
    public String index(Model model){
        List<HongPostTypeVO> list = hongPostTypeService.listWithPost();
        model.addAttribute("postTypeList", list);
        return "/manage/postType";
    }
}
