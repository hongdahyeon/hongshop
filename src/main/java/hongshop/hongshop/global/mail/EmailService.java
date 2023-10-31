package hongshop.hongshop.global.mail;

public interface EmailService {

    void sendInitialPwdEmail(String to, String initialPassword);
    void sendUserIdEmail(String to, String userId);
    void sendEmail(String to, String subject, String text);
}