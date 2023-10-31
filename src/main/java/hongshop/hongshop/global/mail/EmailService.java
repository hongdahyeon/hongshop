package hongshop.hongshop.global.mail;

public interface EmailService {

    void findPassword();
    void sendEmail(String to, String subject, String body);
}