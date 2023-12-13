package hongshop.hongshop.domain.user;

import hongshop.hongshop.domain.base.Address;
import hongshop.hongshop.domain.social.HongSocialUser;
import hongshop.hongshop.domain.user.dto.HongUserDTO;
import hongshop.hongshop.global.util.TimeUtil;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @fileName HongUser
 * @author dahyeon
 * @version 1.0.0
 * @date 2023-07-17
 * @summary  'hong_user' 엔티티
 **/

@Entity
@Table(name="hong_user")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HongUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hong_user_id")
    private Long id;

    @Column(name="user_id")
    private String userId;

    @Column(name = "password")
    private String password;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "user_email")
    private String userEmail;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private HongRoleType role;

    @Column(name = "address")
    @Embedded
    private Address address;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hong_social_user_id")
    private HongSocialUser hongSocialUser;

    @Column(name = "pwd_fail_cnt")
    private Integer pwdFailCnt;

    @Column(name = "user_non_locked")
    public Boolean userNonLocked;

    @Column(name = "user_enable")
    public Boolean userEnable;
    
    @Column(name = "pwd_end_date")
    public String pwdEndDate;

    @Column(name = "last_login_date")
    public String lastLoginDate;

    @Builder(builderMethodName = "hongUserInsertBuilder")
    public HongUser(String userId, String password, HongRoleType role, Address address, String userName, String userEmail, HongSocialUser hongSocialUser){
        this.userId = userId;
        this.password = password;
        this.hongSocialUser = hongSocialUser;
        this.role = role;
        this.address = address;
        this.userName = userName;
        this.userEmail = userEmail;
        this.pwdFailCnt = 0;
        this.userNonLocked = true;
        this.userEnable = true;
        this.pwdEndDate = TimeUtil.daysAfter(90);   // 회원가입일로부터 90일
        this.lastLoginDate = TimeUtil.nowDate();
    }

    public void updateHongUser(HongUserDTO hongUserDTO){
        if(hongUserDTO.getPassword() != null) this.password = hongUserDTO.getPassword();
        this.address = new Address(hongUserDTO.getCity(), hongUserDTO.getStreet(), hongUserDTO.getZipcode());
        if(hongUserDTO.getUserName() != null) this.userName = hongUserDTO.getUserName();
        if(hongUserDTO.getUserEmail() != null) this.userEmail = hongUserDTO.getUserEmail();
    }

    public void updateUserRole(HongRoleType hongRoleType){
        this.role = hongRoleType;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void updatePwdFailCnt(){
        this.pwdFailCnt = this.pwdFailCnt + 1;
    }

    public void updatePwdFailCntAndUserNonLocked(){
        this.pwdFailCnt = this.pwdFailCnt + 1;
        this.userNonLocked = false;
    }

    public void resetPwdFailCntAndUserNonLocked(){
        this.pwdFailCnt = 0;
        this.userNonLocked = true;
    }

    public void updateLastLoginDate(){
        this.lastLoginDate = TimeUtil.nowDate();
    }

    public void changeEnableToDisable(){
        this.userEnable = false;
    }

    public void changeDisableToEnable(){
        this.userEnable = true;
    }

    public void add90Days() {
        this.pwdEndDate =  TimeUtil.daysAfter(90);   // 오늘로부터 90일
    }

    public void changePwd(String password){
        this.password = password;
    }
}
