package hongshop.hongshop.domain.couponRequest.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
* @fileName HongCouponRequestLstDTO
* @author dahyeon
* @version 1.0.0
* @date 2023-11-14
* @summary  요청 리스트로 -> 요청 승인하기
**/

@Getter @Setter
public class HongCouponRequestApproveLstDTO {

    private Long couponId;
    private List<Long> requestId;
    private List<Long> userId;
}