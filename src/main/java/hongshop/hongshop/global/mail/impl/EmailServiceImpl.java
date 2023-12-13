package hongshop.hongshop.global.mail.impl;

import hongshop.hongshop.global.mail.EmailService;
import hongshop.hongshop.global.util.StringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;


@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;
    @Override
    public void sendInitialPwdEmail(String to, String initialPassword){
        String subject = "초기화된 비밀번호입니다.";
        String text = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<body>\n" +
                "    <div class=\"container\">\n" +
                "        <div class=\"form-group\">\n" +
                "            <label for=\"telephone\">초기화된 비밀번호입니다</label>\n" +
                "            <input type=\"text\" class=\"form-control\" value=\"" + initialPassword + "\" readonly>\n" +
                "        </div>\n" +
                "        <span style=\"color: red; font-size: 12px;\">로그인 후\n" +
                "            <span style=\"text-decoration: underline;\">\"프로필 이미지 클릭 > 나의정보\"</span>\n" +
                "            에서 비밀번호를 변경해주시기 바랍니다.\n" +
                "        </span>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>";

        this.sendEmail(to, subject, text);
    }

    @Override
    public void sendUserIdEmail(String to, String userId) {
        String subject = "아이디입니다.";
        String text = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<body>\n" +
                "    <div class=\"container\">\n" +
                "        <div class=\"form-group\">\n" +
                "            <label for=\"telephone\">아이디입니다.</label>\n" +
                "            <input type=\"text\" class=\"form-control\" value=\"" + userId + "\" readonly>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>";

        this.sendEmail(to, subject, text);
    }

    @Override
    public void sendVerification(String to) {
        // TODO: 이메일 인증코드 저장하는 테이블 생성후, 이메일 전송 시 코드값 저장
        String subject = "이메일 인증번호입니다.";
        String verifiCode = StringUtil.random(6);
        String text = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<body>\n" +
                "    <div class=\"container\">\n" +
                "        <div class=\"form-group\">\n" +
                "            <label for=\"telephone\">인증번호입니다.</label>\n" +
                "            <input type=\"text\" class=\"form-control\" value=\"" + verifiCode + "\" readonly>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>";
        this.sendEmail(to, subject, text);
    }

    @Override
    public void sendEmail(String to, String subject, String text) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, true);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
