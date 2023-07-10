package hongshop.hongshop.domain.user;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;

@Getter @Setter
public class HongUserDTO {

    private String userId;
    private String password;
    private HongUserRole role;
}
