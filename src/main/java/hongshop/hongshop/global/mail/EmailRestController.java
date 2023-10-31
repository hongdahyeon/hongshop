package hongshop.hongshop.global.mail;

import hongshop.hongshop.global.mail.impl.EmailServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/manager/email")
public class EmailRestController {

    private final EmailServiceImpl emailService;

    @RequestMapping("/send")
    public String send(){
//        emailService.sendEmail("user-email", "제목", "내용");
        return "send email successfully";
    }

}
