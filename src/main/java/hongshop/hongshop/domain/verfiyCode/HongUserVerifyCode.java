package hongshop.hongshop.domain.verfiyCode;

import hongshop.hongshop.global.util.TimeUtil;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="hong_user_verify_code")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HongUserVerifyCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hong_user_verify_code_id")
    private Long id;

    @Column(name = "user_email")
    private String userEmail;

    @Column(name = "verify_code")
    private String verifyCode;

    @Column(name = "verify_expire")
    private LocalDateTime verifyExpire;

    @Column(name = "verify_check")
    private boolean verifyCheck;

    @Column(name = "verify_created")
    private LocalDateTime verifyCreated;


    @Builder(builderMethodName = "hongUserVerifyCodeInsert")
    public HongUserVerifyCode(String userEmail, String verifyCode) {
        this.userEmail = userEmail;
        this.verifyCode = verifyCode;
        this.verifyExpire = TimeUtil.daysAfter_Date(1);
        this.verifyCheck = false;
        this.verifyCreated = LocalDateTime.now();
    }

    public void changeVerifyCheck(){
        this.verifyCheck = true;
    }
}