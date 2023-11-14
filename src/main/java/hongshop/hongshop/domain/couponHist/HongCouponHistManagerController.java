package hongshop.hongshop.domain.couponHist;

import hongshop.hongshop.domain.coupon.HongCouponService;
import hongshop.hongshop.domain.coupon.vo.HongCouponGroupHistVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/manager")
public class HongCouponHistManagerController {

    private final HongCouponService hongCouponService;

    @GetMapping("/couponHist")
    public String index(Model model) {
        List<HongCouponGroupHistVO> couponHist = hongCouponService.couponAndUserHist();
        model.addAttribute("couponHist", couponHist);
        return "manager/couponHist";
    }
}
