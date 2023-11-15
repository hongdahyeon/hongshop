package hongshop.hongshop.domain.message;

import hongshop.hongshop.domain.message.vo.HongMessageVO;
import hongshop.hongshop.domain.user.HongRoleType;
import hongshop.hongshop.domain.user.HongUser;
import hongshop.hongshop.domain.user.HongUserService;
import hongshop.hongshop.domain.user.vo.HongUserMessageVO;
import hongshop.hongshop.global.auth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/talk")
public class MessageController {

    private final HongMessageService hongMessageService;
    private final HongUserService hongUserService;

    @GetMapping({"", "/"})
    public String index(Model model, @AuthenticationPrincipal PrincipalDetails principalDetails){
        HongUser user = principalDetails.getUser();
        model.addAttribute("sender", user);

        if(user.getRole().equals(HongRoleType.ROLE_SUPER)) {
            List<HongMessageVO> messageLstByReceiver = hongMessageService.getMessageLstByReceiver(user);
            model.addAttribute("messageCanUser", messageLstByReceiver);
        }else {
            List<HongUserMessageVO> messageCanUser = hongUserService.getMessageCanUser(user);
            model.addAttribute("messageCanUser", messageCanUser);
        }

        return "talk/talk";
    }
}
