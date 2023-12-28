package hongshop.hongshop.domain.couponHist.dto;

import lombok.Getter;
import lombok.Setter;

/**
* @fileName HongCouponHistDTO
* @author dahyeon
* @version 1.0.0
* @date 2023-12-28
* @summary 쿠폰 사용 이력 DTO
**/

@Getter @Setter
public class HongCouponHistDTO {

    private Long hongCouponHasId;

    public HongCouponHistDTO(Long hongCouponHasId) {
        this.hongCouponHasId = hongCouponHasId;
    }
}
