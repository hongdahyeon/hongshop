package hongshop.hongshop.global.mail.impl;

import hongshop.hongshop.domain.user.HongUserService;
import hongshop.hongshop.global.mail.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;
    private final HongUserService hongUserService;

    public void findPassword(){
//        HongUserVO hongUserVO = hongUserService.getHongUserByUserId(userEmail);
    }

    public void sendEmail(String to, String subject, String body){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        javaMailSender.send(message);
    }

}
