package hongshop.hongshop.domain.deliver;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/manager")
@RequiredArgsConstructor
public class HongDeliverManagerController {

    @GetMapping("/deliver")
    public String deliver(){
        return "manager/deliver";
    }
}
