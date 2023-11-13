package hongshop.hongshop.domain.order.dto;

import lombok.Getter;
import lombok.Setter;

/**
* @fileName HongOrderFromShopDTO
* @author dahyeon
* @version 1.0.0
* @date 2023-11-09
* @summary  상품 리스트 화면에서 선택후 바로 주문
**/

@Getter
@Setter
public class HongOrderFromShopDTO {

    private Integer orderCnt;           // 주문개수
    private Long hongProductId;         // 상품 번호 id

    // 주문자 기본 정보
    private String userId;
    private String city;
    private String street;
    private String zipcode;

    // 사용자 등록 쿠폰 id
    private Long hongCouponHasId;
}
