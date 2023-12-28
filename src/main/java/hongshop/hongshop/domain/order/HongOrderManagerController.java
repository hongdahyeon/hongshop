package hongshop.hongshop.domain.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/manager/order")
public class HongOrderManagerController {

    @GetMapping({"/", ""})
    public String index(){
        return "manager/order";
    }
}