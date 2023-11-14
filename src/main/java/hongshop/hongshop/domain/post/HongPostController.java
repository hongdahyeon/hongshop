package hongshop.hongshop.domain.post;

import hongshop.hongshop.domain.answer.HongAnswerService;
import hongshop.hongshop.domain.answer.vo.HongAnswerVO;
import hongshop.hongshop.domain.post.vo.HongPostVO;
import hongshop.hongshop.domain.postType.HongPostTypeService;
import hongshop.hongshop.domain.post.html.BbsType;
import hongshop.hongshop.domain.post.html.CRUD;
import hongshop.hongshop.domain.postType.PostType;
import hongshop.hongshop.domain.postType.vo.HongPostTypeVO;
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
import java.util.List;

@Controller
@RequestMapping("/bbs")
@RequiredArgsConstructor
public class HongPostController {

    private final HongPostTypeService hongPostTypeService;
    private final HongPostService hongPostService;
    private final HongAnswerService hongAnswerService;

    @GetMapping("/{id}")
    public String list(@PathVariable Long id, Model model, @AuthenticationPrincipal PrincipalDetails principalDetails){
        HongPostTypeVO view = hongPostTypeService.view(id);
        List<HongPostVO> postList = hongPostService.postsWithFileByPostType(id);

        if(view.getPostType().equals(PostType.FAQ.toString())) {
            postList.forEach(post ->  {
                post.setContent(StringEscapeUtils.unescapeHtml4(post.getContent()));
            });
        }

        model.addAttribute("id", id);
        model.addAttribute("type", view);
        model.addAttribute("user", principalDetails.getUser());
        model.addAttribute("postList", postList);
        return "bbs/" + BbsType.getHtmlName(view.getPostType(), CRUD.INDEX.html());
    }

    @GetMapping("/view/{id}")
    public String view(@PathVariable Long id, Model model, HttpServletResponse res, HttpServletRequest req, @AuthenticationPrincipal PrincipalDetails principalDetails){
        HongPostVO post = hongPostService.postWithFileAndAnswer(id);
        HongPostTypeVO type = hongPostTypeService.view(post.getTypeId());
        List<HongAnswerVO> answers = hongAnswerService.listByHongPostId(id);

        post.setContent(StringEscapeUtils.unescapeHtml4(post.getContent()));
        hongPostService.updateReadCnt(post.getId(), req, res);

        model.addAttribute("post", post);
        model.addAttribute("type", type);
        model.addAttribute("answers", answers);
        model.addAttribute("user", principalDetails.getUser());

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

    @GetMapping("/save/{id}")
    public String save(@PathVariable Long id, Model model) {
        HongPostTypeVO type = hongPostTypeService.view(id);

        model.addAttribute("type", type);
        return "bbs/" + BbsType.getHtmlName(type.getPostType(), CRUD.SAVE.html());
    }
}
