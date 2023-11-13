package hongshop.hongshop.domain.coupon;

import hongshop.hongshop.domain.coupon.vo.HongCouponVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/manager")
@Controller
public class HongCouponManagerController {

    private final HongCouponService hongCouponService;

    @GetMapping("/coupon")
    public String coupon(Model model){
        List<HongCouponVO> list = hongCouponService.listWithChkUser();
        model.addAttribute("coupons", list);
        return "manager/coupon";
    }
}
