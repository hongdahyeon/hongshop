package hongshop.hongshop.domain.postType;

import hongshop.hongshop.domain.post.HongPostService;
import hongshop.hongshop.domain.post.HongPostVO;
import hongshop.hongshop.domain.postType.html.BbsType;
import hongshop.hongshop.domain.postType.html.CRUD;
import hongshop.hongshop.global.auth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public String list(@PathVariable Long id, Model model, @AuthenticationPrincipal PrincipalDetails principalDetails){
        HongPostTypeVO view = hongPostTypeService.view(id);
        model.addAttribute("type", view);
        model.addAttribute("role", principalDetails.getUser().getRole());
        return "bbs/" + BbsType.getHtmlName(view.getPostType(), CRUD.INDEX.html());
    }
}
