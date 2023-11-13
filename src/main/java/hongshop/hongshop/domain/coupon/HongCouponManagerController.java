package hongshop.hongshop.domain.coupon;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("/manager")
@Controller
public class HongCouponManagerController {

    @GetMapping("/coupon")
    public String coupon(){
        return "manager/coupon";
    }
}
