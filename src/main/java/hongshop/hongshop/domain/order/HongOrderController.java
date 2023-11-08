package hongshop.hongshop.domain.order;

import hongshop.hongshop.domain.base.Address;
import hongshop.hongshop.domain.cart.HongCartService;
import hongshop.hongshop.domain.cart.vo.HongCartVO;
import hongshop.hongshop.domain.user.HongUserService;
import hongshop.hongshop.domain.user.vo.HongUserVO;
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

    @GetMapping({"", "/"})
    public String index(@RequestParam(name = "ids", required = false) List<Long> ids, Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        Long id = principalDetails.getUser().getId();
        HongUserVO user = hongUserService.getHongUserById(id);
        String userId = user.getUserId();
        Address address = new Address(user.getCity(), user.getStreet(), user.getZipcode());
        List<HongCartVO> chooseLst = hongCartService.listOfChoose(ids);

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
        return "order/index";
    }

}
