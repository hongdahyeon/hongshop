package hongshop.hongshop.domain.category;

import hongshop.hongshop.domain.category.vo.HongCategoryVO;
import hongshop.hongshop.domain.postType.HongPostTypeService;
import hongshop.hongshop.domain.postType.vo.HongPostTypeVO;
import hongshop.hongshop.domain.product.HongProductService;
import hongshop.hongshop.domain.product.vo.HongProductVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/category")
public class HongCategoryController {
    private final HongPostTypeService hongPostTypeService;
    private final HongCategoryService hongCategoryService;
    private final HongProductService hongProductService;

    @GetMapping("/new")
    public String newCategory(Model model){
        List<HongProductVO> newProdcuts = hongProductService.getNewProducts();
        List<HongCategoryVO> list = hongCategoryService.listWithProduct();
        HongPostTypeVO qnaPost = hongPostTypeService.getQnaPost();

        model.addAttribute("newProdcuts", newProdcuts);
        model.addAttribute("categories", list);
        model.addAttribute("qnaPost", qnaPost);

        return "category/index";
    }
}
