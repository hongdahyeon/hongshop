package hongshop.hongshop.domain.coupon.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class HongCouponDTO {
    private String couponName;
    private Integer couponRate;
    private String useAt;
    private LocalDate startDate;
    private LocalDate endDate;
}