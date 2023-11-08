package hongshop.hongshop.domain.order.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
* @fileName HongOrderFromCartDTO
* @author dahyeon
* @version 1.0.0
* @date 2023-11-08
* @summary  장바구니에서 상품 주문하기 (기본 정보 : 주소, 사용자id ) + 주문 상세 정보
**/

@Getter @Setter
public class HongOrderFromCartDTO {

    private List<HongOrderFromCartDetailsDTO> orders;
    private String userId;
    private String city;
    private String street;
    private String zipcode;
}
