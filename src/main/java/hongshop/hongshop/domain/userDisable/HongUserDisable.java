package hongshop.hongshop.domain.userDisable;

import hongshop.hongshop.domain.user.HongUser;
import hongshop.hongshop.global.util.TimeUtil;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="hong_user_disable")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HongUserDisable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hong_user_disable_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hong_user_id")
    private HongUser hongUser;

    @Column(name = "disable_msg")
    private String disableMsg;

    @Column(name = "disable_date")
    private String disableDate;

    @Column(name = "delete_yn")
    private String deleteYn;

    @Builder(builderMethodName = "hongUserDisableInsert")
    public HongUserDisable(HongUser hongUser, String disableMsg) {
        this.hongUser = hongUser;
        this.disableMsg = disableMsg;
        this.disableDate = TimeUtil.nowDate();
        this.deleteYn = "N";
    }

    public void deleteUserDisable(){
        this.deleteYn = "Y";
    }
}