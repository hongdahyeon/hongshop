package hongshop.hongshop.domain.couponRequest.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
* @fileName HongCouponRequestLstDTO
* @author dahyeon
* @version 1.0.0
* @date 2023-11-14
* @summary (로그인한 사용자) 쿠폰 여러개 한번에 요청하기
**/

@Getter @Setter
public class HongCouponRequestLstDTO {
    private List<Long> couponId = new ArrayList<>();
}
