package hongshop.hongshop.domain.category;

import hongshop.hongshop.domain.category.vo.HongCategoryVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/category")
public class HongCategoryController {

    private final HongCategoryService hongCategoryService;
    @GetMapping("/new")
    public String newCategory(Model model){
        model.addAttribute("imageName", "672e9509-a9a8-4a3c-a61e-550465352093.png");
        return "category/new";
    }

    @GetMapping("/{id}")
    public String category(@PathVariable Long id, Model model){
        HongCategoryVO list = hongCategoryService.showWithProduct(id);
        model.addAttribute("categories", list);
        return "category/index";
    }
}
