package hongshop.hongshop.domain.coupon;

import hongshop.hongshop.domain.coupon.vo.HongCouponVO;
import hongshop.hongshop.domain.user.HongUserService;
import hongshop.hongshop.domain.user.vo.HongUserCouponVO;
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
    private final HongUserService hongUserService;

    @GetMapping("/coupon")
    public String coupon(Model model){
        List<HongCouponVO> list = hongCouponService.listWithChkUser();
        List<HongUserCouponVO> userListForCoupon = hongUserService.getUserListForCoupon();
        model.addAttribute("coupons", list);
        model.addAttribute("userList", userListForCoupon);
        return "manager/coupon";
    }
}
