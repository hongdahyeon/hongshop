package hongshop.hongshop.domain.user.vo;

import hongshop.hongshop.domain.user.HongUser;
import lombok.Getter;
import lombok.Setter;

/**
* @fileName HongUserMessageVO
* @author dahyeon
* @version 1.0.0
* @date 2023-11-15
* @summary  메시지를 할 수 있는 슈퍼 관리자 vo
**/


@Getter @Setter
public class HongUserMessageVO {

    private Long id;
    private String userId;
    private String userNm;

    public HongUserMessageVO(HongUser hongUser){
        this.id = hongUser.getId();
        this.userId = hongUser.getUserId();
        this.userNm = hongUser.getUserName();
    }
}