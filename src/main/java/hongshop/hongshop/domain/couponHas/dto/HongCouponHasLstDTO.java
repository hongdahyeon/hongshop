package hongshop.hongshop.domain.couponHas.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
* @fileName HongCouponHasLstDTO
* @author dahyeon
* @version 1.0.0
* @date 2023-11-14
* @summary  사용자 쿠폰 지급 dto
 *          - userId lst의 사용자들에게 couponId 쿠폰을 지급
**/

@Getter @Setter
public class HongCouponHasLstDTO {

    private List<Long> userId;
    private Long couponId;
}