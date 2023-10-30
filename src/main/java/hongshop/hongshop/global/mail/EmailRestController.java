package hongshop.hongshop.global.mail;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/manager/email")
public class EmailRestController {

    private final EmailService emailService;

    @RequestMapping("/send")
    public String send(){
        emailService.sendEmail("user-email", "제목", "내용");
        return "send email successfully";
    }

}
