package hongshop.hongshop.domain.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HongUserVO {

    private String userId;
    private HongUserRole role;

    public HongUserVO(String userId, HongUserRole hongUserRole){
        this.userId = userId;
        this.role = hongUserRole;
    }
}
