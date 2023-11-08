package hongshop.hongshop.domain.deliver.dto;

import lombok.Getter;
import lombok.Setter;

/**
* @fileName HongDeliverAddressDTO
* @author dahyeon
* @version 1.0.0
* @date 2023-11-09
* @summary  배송 주소 변경 -> 배송 상태가 AWAIT 일 경우에만 가능
**/

@Getter @Setter
public class HongDeliverAddressDTO {

    private String city;
    private String street;
    private String zipcode;
}
