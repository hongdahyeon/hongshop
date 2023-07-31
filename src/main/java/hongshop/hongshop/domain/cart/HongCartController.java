package hongshop.hongshop.domain.cart;

import hongshop.hongshop.domain.user.HongUser;
import hongshop.hongshop.global.auth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class HongCartController {

    private final HongCartService hongCartService;

    @GetMapping("/{id}")
    public String index(@PathVariable Long id, Model model){
        List<HongCartVO> cart = hongCartService.getUsersListOfCartById(id);
        model.addAttribute("cart", cart);
        return "cart/index";
    }
}
