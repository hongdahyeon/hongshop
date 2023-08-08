package hongshop.hongshop.domain.postType;

import hongshop.hongshop.domain.post.HongPostService;
import hongshop.hongshop.domain.post.HongPostVO;
import hongshop.hongshop.domain.postType.html.BbsType;
import hongshop.hongshop.domain.postType.html.CRUD;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/bbs")
@RequiredArgsConstructor
public class HongPostTypeController {

    private final HongPostTypeService hongPostTypeService;
    private final HongPostService hongPostService;

    @GetMapping("/{id}")
    public String list(@PathVariable Long id, Model model){
        HongPostTypeVO view = hongPostTypeService.view(id);
        List<HongPostVO> posts = hongPostService.postsWithFileByPostType(view.getPostTypeId());

        model.addAttribute("type", view);
        model.addAttribute("posts", posts);
        return "bbs/" + BbsType.getHtmlName(view.getPostType(), CRUD.INDEX.html());
    }
}
