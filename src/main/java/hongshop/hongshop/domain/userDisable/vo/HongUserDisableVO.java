package hongshop.hongshop.domain.userDisable.vo;

import hongshop.hongshop.domain.userDisable.HongUserDisable;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class HongUserDisableVO {

    private Long enableId;
    private String enableMsg;
    private String enableDate;
    private String userId;

    public HongUserDisableVO(HongUserDisable hongUserEnable) {
        this.enableId = hongUserEnable.getId();
        this.enableMsg = hongUserEnable.getDisableMsg();
        this.enableDate = hongUserEnable.getDisableDate();
        this.userId = hongUserEnable.getHongUser().getUserId();
    }
}