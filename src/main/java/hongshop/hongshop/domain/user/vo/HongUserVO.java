package hongshop.hongshop.domain.user.vo;

import hongshop.hongshop.domain.base.Address;
import hongshop.hongshop.domain.user.HongUser;
import lombok.Getter;
import lombok.Setter;

/**
 * @fileName HongUserVO
 * @author dahyeon
 * @version 1.0.0
 * @date 2023-07-17
 * @summary 사용자에게 hong user에 대한 데이터를 보여줄때 entity가 아닌 vo를 이용한다.
 **/


@Getter
@Setter
public class HongUserVO {

    private Long id;
    private String userId;
    private String role;
    private String userName;
    private String userEmail;
    private String city;
    private String street;
    private String zipcode;

    public HongUserVO(HongUser hongUser){
        this.id = hongUser.getId();
        this.userId = hongUser.getUserId();
        this.role = hongUser.getRole().toString();
        this.userName = hongUser.getUserName();
        this.userEmail = hongUser.getUserEmail();
        this.city = hongUser.getAddress().getCity();
        this.street = hongUser.getAddress().getStreet();
        this.zipcode = hongUser.getAddress().getZipcode();
    }
}
