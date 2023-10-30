package hongshop.hongshop.domain.user;

import hongshop.hongshop.domain.base.Address;
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
    private String city;
    private String street;
    private String zipcode;

    public HongUserVO(String userId, HongRoleType hongUserRole, Address address){
        this.userId = userId;
        this.role = hongUserRole.toString();
        this.city = address.getCity();
        this.street = address.getStreet();
        this.zipcode = address.getZipcode();
    }

    public HongUserVO(HongUser hongUser){
        this.id = hongUser.getId();
        this.userId = hongUser.getUserId();
        this.role = hongUser.getRole().toString();
        this.city = hongUser.getAddress().getCity();
        this.street = hongUser.getAddress().getStreet();
        this.zipcode = hongUser.getAddress().getZipcode();
    }
}
