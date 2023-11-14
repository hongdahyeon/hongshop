package hongshop.hongshop.domain.user.vo;

import hongshop.hongshop.domain.user.HongUser;
import lombok.Getter;
import lombok.Setter;

/**
* @fileName HongUserCouponVO
* @author dahyeon
* @version 1.0.0
* @date 2023-11-14
* @summary (관리자) 쿠폰 사용자에게 등록하기 위해 사용자 리스트 띄우기 vo
**/

@Getter @Setter
public class HongUserCouponVO {

    private Long id;
    private String userId;
    private String userName;

    public HongUserCouponVO(HongUser hongUser){
        this.id = hongUser.getId();
        this.userId = hongUser.getUserId();
        this.userName = hongUser.getUserName();
    }
}