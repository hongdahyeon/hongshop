package hongshop.hongshop.domain.deliver;

import hongshop.hongshop.domain.deliver.vo.HongDeliverVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/manager")
@RequiredArgsConstructor
public class HongDeliverManagerController {

    private final HongDeliverService hongDeliverService;

    @GetMapping("/deliver")
    public String deliver(Model model){
        List<HongDeliverVO> all = hongDeliverService.allWithChkReview();
        model.addAttribute("delivers", all);
        return "manager/deliver";
    }
}
