package hongshop.hongshop.domain.user;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class HongUserDTO {

    private String userId;
    private String password;
    private HongRoleType role;

    private String city;
    private String street;
    private String zipcode;
}
