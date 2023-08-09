package hongshop.hongshop.domain.postType;

import hongshop.hongshop.domain.post.HongPostService;
import hongshop.hongshop.domain.post.HongPostVO;
import hongshop.hongshop.domain.postType.html.BbsType;
import hongshop.hongshop.domain.postType.html.CRUD;
import hongshop.hongshop.global.auth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
        model.addAttribute("user", principalDetails.getUser());
        return "bbs/" + BbsType.getHtmlName(view.getPostType(), CRUD.INDEX.html());
    }

    @GetMapping("/view/{id}")
    public String view(@PathVariable Long id, Model model, HttpServletResponse res, HttpServletRequest req){
        HongPostVO post = hongPostService.postWithFileAndAnswer(id);
        HongPostTypeVO type = hongPostTypeService.view(post.getTypeId());

        post.setContent(StringEscapeUtils.unescapeHtml4(post.getContent()));
        hongPostService.updateReadCnt(post.getId(), req, res);

        model.addAttribute("post", post);
        model.addAttribute("type", type);
        return "bbs/" + BbsType.getHtmlName(post.getType(), CRUD.VIEW.html());
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model){
        HongPostVO post = hongPostService.postWithFileAndAnswer(id);
        HongPostTypeVO type = hongPostTypeService.view(post.getTypeId());

        post.setContent(StringEscapeUtils.unescapeHtml4(post.getContent()));
        model.addAttribute("post", post);
        model.addAttribute("type", type);
        return "bbs/" + BbsType.getHtmlName(post.getType(), CRUD.EDIT.html());
    }
}
