package hongshop.hongshop.domain.couponHist.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class HongCouponHistDTO {

    private Long hongCouponHasId;

    public HongCouponHistDTO(Long hongCouponHasId) {
        this.hongCouponHasId = hongCouponHasId;
    }
}
