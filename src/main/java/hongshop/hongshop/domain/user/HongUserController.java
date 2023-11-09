package hongshop.hongshop.domain.user;

import hongshop.hongshop.domain.cart.HongCartService;
import hongshop.hongshop.domain.cart.vo.HongCartVO;
import hongshop.hongshop.domain.order.HongOrderService;
import hongshop.hongshop.domain.order.vo.HongOrderDeliverVO;
import hongshop.hongshop.global.auth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class HongUserController {

    private final HongUserService hongUserService;
    private final HongOrderService hongOrderService;
    private final HongCartService hongCartService;

    @GetMapping("/myInfo")  // 회원정보 수정페이지
    public String myInfo(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model){
        String userId = principalDetails.getUser().getUserId();
        model.addAttribute("user", hongUserService.getHongUserByUserId(userId));
        return "user/myInfo";
    }

    @GetMapping("/cart")    // 회원 - 장바구니 페이지
    public String index(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model){
        Long id = principalDetails.getUser().getId();
        List<HongCartVO> cart = hongCartService.getUsersListOfCartById(id);
        model.addAttribute("cart", cart);
        model.addAttribute("id", id);
        return "cart/index";
    }

    @GetMapping("/order")   // 회원 - 배송 및 주문 정보 페이지
    public String orderUser(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
        Long id = principalDetails.getUser().getId();
        List<HongOrderDeliverVO> orders = hongOrderService.getOrderAndDeliverByUserId(id);
        model.addAttribute("orders", orders);
        model.addAttribute("id", id);
        return "user/order";
    }

}
