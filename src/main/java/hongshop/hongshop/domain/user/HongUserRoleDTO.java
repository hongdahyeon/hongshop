package hongshop.hongshop.domain.user;

import lombok.Getter;
import lombok.Setter;

/**
 * @fileName HongUserRoleDTO
 * @author dahyeon
 * @version 1.0.0
 * @date 2023-10-30
 * @summary  사용자 권한 정보 변경 dto
 **/

@Getter @Setter
public class HongUserRoleDTO {
    private HongRoleType role;
}
