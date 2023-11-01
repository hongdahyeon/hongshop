package hongshop.hongshop.domain.fileLog.vo;

import hongshop.hongshop.domain.fileLog.HongFileLog;
import hongshop.hongshop.domain.user.vo.HongUserVO;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class HongFileLogVO {

    private Long hongFileId;
    private String userName;
    private String userId;
    private String downloadDate;

    public HongFileLogVO(HongFileLog hongFileLog, HongUserVO hongUserVO) {
        this.hongFileId = hongFileLog.getHongFile().getId();
        this.userName = hongUserVO.getUserName();
        this.userId = hongUserVO.getUserId();
        this.downloadDate = hongFileLog.getCreatedDate();
    }
}
