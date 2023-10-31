package hongshop.hongshop.domain.fileLog.vo;

import hongshop.hongshop.domain.fileLog.HongFileLog;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class HongFileLogVO {

    private Long hongFileId;
    private String userName;
    private String downloadDate;

    public HongFileLogVO(HongFileLog hongFileLog,String userName) {
        this.hongFileId = hongFileLog.getHongFile().getId();
        this.userName = userName;
        this.downloadDate = hongFileLog.getCreatedDate();
    }
}
