package hongshop.hongshop.domain.userEnable.vo;

import hongshop.hongshop.domain.userEnable.HongUserEnable;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class HongUserEnableVO {

    private Long enableId;
    private String enableMsg;
    private String enableDate;
    private String userId;

    public HongUserEnableVO(HongUserEnable hongUserEnable) {
        this.enableId = hongUserEnable.getId();
        this.enableMsg = hongUserEnable.getEnableMsg();
        this.enableDate = hongUserEnable.getEnableDate();
        this.userId = hongUserEnable.getHongUser().getUserId();
    }
}