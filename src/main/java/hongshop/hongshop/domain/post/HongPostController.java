package hongshop.hongshop.domain.post;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/post")
@RequiredArgsConstructor
@Slf4j
public class HongPostController {

    private final HongPostService hongPostService;
    @GetMapping("/view/{id}")
    public String view(@PathVariable Long id, HttpServletRequest req, HttpServletResponse res){
        hongPostService.updateReadCnt(id, req, res);
        return "index";
    }
}
