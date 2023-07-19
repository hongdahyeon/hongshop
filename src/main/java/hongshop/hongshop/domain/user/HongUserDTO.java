package hongshop.hongshop.domain.user;

import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotNull;

/**
 * @fileName HongUserDTO
 * @author dahyeon
 * @version 1.0.0
 * @date 2023-07-17
 * @summary 사용자로부터 데이터 입력을 받을때 entity가 아닌 dto를 통해 받는다.
 **/

@Getter @Setter
public class HongUserDTO {

    @NotNull
    private String userId;
    @NotNull
    private String password;

    private HongRoleType role;

    private String city;
    private String street;
    private String zipcode;
}
