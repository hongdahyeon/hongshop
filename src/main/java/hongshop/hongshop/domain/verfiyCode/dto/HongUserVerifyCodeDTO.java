package hongshop.hongshop.domain.verfiyCode.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class HongUserVerifyCodeDTO {

    private String userEmail;
    private String verifyCode;

    public HongUserVerifyCodeDTO(String userEmail, String verifyCode){
        this.userEmail = userEmail;
        this.verifyCode = verifyCode;
    }
}