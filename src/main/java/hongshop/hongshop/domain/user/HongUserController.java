package hongshop.hongshop.domain.user;

import hongshop.hongshop.domain.cart.HongCartService;
import hongshop.hongshop.domain.cart.vo.HongCartVO;
import hongshop.hongshop.domain.review.HongReviewService;
import hongshop.hongshop.domain.review.vo.HongReviewVO;
import hongshop.hongshop.global.auth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.apache.commons.text.StringEscapeUtils;
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
    private final HongCartService hongCartService;
    private final HongReviewService hongReviewService;

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
        return "user/cart";
    }

    @GetMapping("/order")   // 회원 - 배송 및 주문 정보 페이지
    public String orderUser() {
        return "user/order";
    }

    @GetMapping("/review")  // 회원 - 리뷰 작성 리스트
    public String reviewUser(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
        HongUser user = principalDetails.getUser();
        List<HongReviewVO> hongReviewVOS = hongReviewService.userReview(user);
        hongReviewVOS.forEach(hongReview -> {
           hongReview.setReviewContet(StringEscapeUtils.unescapeHtml4(hongReview.getReviewContet()));
        });
        model.addAttribute("reviews", hongReviewVOS);
        return "user/review";
    }


    @GetMapping("/coupon")
    public String couponUser(){
        return "user/coupon";
    }
}
