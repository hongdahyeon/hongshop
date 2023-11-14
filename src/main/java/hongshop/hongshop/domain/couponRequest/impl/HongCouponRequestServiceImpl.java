package hongshop.hongshop.domain.couponRequest.impl;

import hongshop.hongshop.domain.coupon.HongCoupon;
import hongshop.hongshop.domain.coupon.HongCouponRepository;
import hongshop.hongshop.domain.couponRequest.HongCouponRequest;
import hongshop.hongshop.domain.couponRequest.HongCouponRequestRepository;
import hongshop.hongshop.domain.couponRequest.HongCouponRequestService;
import hongshop.hongshop.domain.couponRequest.dto.HongCouponRequestDTO;
import hongshop.hongshop.domain.couponRequest.vo.HongCouponRequestVO;
import hongshop.hongshop.domain.user.HongUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* @fileName HongCouponRequestServiceImpl
* @author dahyeon
* @version 1.0.0
* @date 2023-11-14
* @summary  (1) join : 사용자의 쿠폰 요청 저장
 *          (2) list : 사용자의 쿠폰 요청 전체 조회 -> 삭제여부 N
 *          (3) listOfUser : '로그인한' 사용자의 쿠폰 요청 전체 조회 -> 삭제여부 N, 요청승인여부 N
 *          (4) delete : 쿠폰 요청 삭제
 *          (5) listByCoupon : 쿠폰-ID로 사용자의 쿠폰 요청 전체 조회 -> 삭제여부 N, 요청승인여부 N
 *          (6) approveRequest : 사용자의 쿠폰 요청 승인
**/

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HongCouponRequestServiceImpl implements HongCouponRequestService {

    private final HongCouponRequestRepository hongCouponRequestRepository;
    private final HongCouponRepository hongCouponRepository;

    @Override
    @Transactional(readOnly = false)
    public Long join(HongCouponRequestDTO hongCouponRequestDTO, HongUser hongUser) {

        HongCoupon hongCoupon = hongCouponRepository.findById(hongCouponRequestDTO.getHongCouponId()).orElseThrow(() -> new IllegalArgumentException("there is no coupon"));

        HongCouponRequest hongCouponRequest = HongCouponRequest.hongCouponRequestInsert()
                                                    .hongCoupon(hongCoupon)
                                                    .hongUser(hongUser)
                                                    .build();

        HongCouponRequest save = hongCouponRequestRepository.save(hongCouponRequest);
        return save.getId();
    }

    @Override
    public List<HongCouponRequestVO> list() {
        List<HongCouponRequest> all = hongCouponRequestRepository.findAllByDeleteYnIs("N");
        return all.stream().map(HongCouponRequestVO::new).toList();
    }

    @Override
    public List<HongCouponRequestVO> listOfUser(HongUser hongUser) {
        List<HongCouponRequest> list = hongCouponRequestRepository.findAllByDeleteYnAndHongUser_IdAndRequestApproveYn("N", hongUser.getId(), "N");
        return list.stream().map(HongCouponRequestVO::new).toList();
    }

    @Override
    @Transactional(readOnly = false)
    public void delete(Long id) {
        HongCouponRequest hongCouponRequest = hongCouponRequestRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("there is no request"));
        hongCouponRequest.deleteRequest();
    }

    @Override
    public List<HongCouponRequestVO> listByCoupon(Long couponId) {
        List<HongCouponRequest> couponRequestLst = hongCouponRequestRepository.findAllByDeleteYnAndHongCoupon_IdAndRequestApproveYn("N", couponId, "N");
        return couponRequestLst.stream().map(HongCouponRequestVO::new).toList();
    }

    @Override
    @Transactional(readOnly = false)
    public void approveRequest(Long id) {
        HongCouponRequest hongCouponRequest = hongCouponRequestRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("there is no request"));
        hongCouponRequest.approveRequest();
    }
}
