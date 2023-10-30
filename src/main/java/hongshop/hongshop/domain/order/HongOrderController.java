package hongshop.hongshop.domain.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/order")
public class HongOrderController {

    private final HongOrderService hongOrderService;
    @GetMapping({"/", ""})
    public String index(Model model){
        List<HongOrderVO> list = hongOrderService.list();
        model.addAttribute("orderList", list);
        return "manage/order";
    }
}