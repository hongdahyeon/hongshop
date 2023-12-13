package hongshop.hongshop.domain.user.dto;

import lombok.Getter;
import lombok.Setter;

/**
* @fileName HongUserChangePwdDateDTO
* @author dahyeon
* @version 1.0.0
* @date 2023-12-13
* @summary  비밀번호 만료로 인해, 비밀번호 90일 연장
**/

@Getter @Setter
public class HongUserPwdDateDTO {
    private String userId;
    private String password;
}