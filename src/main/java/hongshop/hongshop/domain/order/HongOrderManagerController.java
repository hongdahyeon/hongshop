package hongshop.hongshop.domain.order;

import hongshop.hongshop.domain.order.vo.HongOrderVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/manager/order")
public class HongOrderManagerController {

    private final HongOrderService hongOrderService;
    @GetMapping({"/", ""})
    public String index(Model model){
        List<HongOrderVO> list = hongOrderService.listWithChkReview();
        model.addAttribute("orderList", list);
        return "manager/order";
    }
}