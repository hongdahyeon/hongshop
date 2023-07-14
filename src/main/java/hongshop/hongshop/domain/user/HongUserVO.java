package hongshop.hongshop.domain.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HongUserVO {

    private String userId;
    private HongRoleType role;
    private String city;
    private String street;
    private String zipcode;

    public HongUserVO(String userId, HongRoleType hongUserRole, Address address){
        this.userId = userId;
        this.role = hongUserRole;
        this.city = address.getCity();
        this.street = address.getStreet();
        this.zipcode = address.getZipcode();
    }
}
