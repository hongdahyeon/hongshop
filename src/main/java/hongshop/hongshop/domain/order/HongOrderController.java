package hongshop.hongshop.domain.order;

import hongshop.hongshop.domain.base.Address;
import hongshop.hongshop.domain.cart.HongCartService;
import hongshop.hongshop.domain.cart.vo.HongCartVO;
import hongshop.hongshop.domain.couponHas.HongCouponHasService;
import hongshop.hongshop.domain.couponHas.vo.HongCouponHasVO;
import hongshop.hongshop.domain.product.HongProductService;
import hongshop.hongshop.domain.product.vo.HongProductVO;
import hongshop.hongshop.domain.user.HongUserService;
import hongshop.hongshop.global.auth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
public class HongOrderController {

    private final HongCartService hongCartService;
    private final HongUserService hongUserService;
    private final HongProductService hongProductService;
    private final HongCouponHasService hongCouponHasService;

    // 장바구니에서 선택한 정보들로 구매 화면 이동
    @GetMapping("/cart")
    public String index(@RequestParam(name = "ids", required = false) List<Long> ids, Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        Long id = principalDetails.getUser().getId();
        String userId = principalDetails.getUser().getUserId();
        Address address = hongUserService.getAddress(id);
        List<HongCartVO> chooseLst = hongCartService.listOfChoose(ids);
        List<HongCouponHasVO> hongCoupons = hongCouponHasService.listByHongUser(principalDetails.getUser());

        Integer totalPrice = 0;
        if(chooseLst.size() > 0) {
            for (HongCartVO cart : chooseLst) {
                totalPrice += cart.getCartPrice();
            }
        }

        model.addAttribute("ids", ids);                 // 선택한 cart-id
        model.addAttribute("id", id);                   // 사용자-id
        model.addAttribute("userId", userId);
        model.addAttribute("chooseLst", chooseLst);     // 선택한 cart 정보lst
        model.addAttribute("address", address);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("hongCoupons", hongCoupons);
        return "order/cartIndex";
    }

    // 상품 리스트 화면에서 선택한 정보들로 구매 화면 이동
    @GetMapping("/shop")
    public String shop(@RequestParam(name = "productId", required = true) Long productId, @RequestParam(name= "orderNum", required = true) Integer orderNum, @AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
        Long id = principalDetails.getUser().getId();
        String userId = principalDetails.getUser().getUserId();
        Address address = hongUserService.getAddress(id);
        List<HongCouponHasVO> hongCoupons = hongCouponHasService.listByHongUser(principalDetails.getUser());

        HongProductVO product = hongProductService.view(productId);
        Integer totalPrice = product.getProductPrice() * orderNum;

        model.addAttribute("product", product);
        model.addAttribute("orderNum", orderNum);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("address", address);
        model.addAttribute("userId", userId);
        model.addAttribute("hongCoupons", hongCoupons);

        return "order/shopIndex";
    }

}
