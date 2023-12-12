package hongshop.hongshop.domain.userEnable;

import hongshop.hongshop.domain.user.HongUser;
import hongshop.hongshop.global.util.TimeUtil;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="hong_user_enable")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HongUserEnable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hong_user_enable_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hong_user_id")
    private HongUser hongUser;

    @Column(name = "enable_msg")
    private String enableMsg;

    @Column(name = "enable_date")
    private String enableDate;

    @Column(name = "delete_yn")
    private String deleteYn;

    @Builder(builderMethodName = "hongUserEnableInsert")
    public HongUserEnable(HongUser hongUser, String enableMsg) {
        this.hongUser = hongUser;
        this.enableMsg = enableMsg;
        this.enableDate = TimeUtil.nowDate();
        this.deleteYn = "N";
    }

    public void deleteUserEnable(){
        this.deleteYn = "Y";
    }
}