package hongshop.hongshop.domain.couponRequest.impl;

import hongshop.hongshop.domain.coupon.HongCoupon;
import hongshop.hongshop.domain.coupon.HongCouponRepository;
import hongshop.hongshop.domain.couponHas.HongCouponHas;
import hongshop.hongshop.domain.couponHas.HongCouponHasRepository;
import hongshop.hongshop.domain.couponRequest.HongCouponRequest;
import hongshop.hongshop.domain.couponRequest.HongCouponRequestRepository;
import hongshop.hongshop.domain.couponRequest.HongCouponRequestService;
import hongshop.hongshop.domain.couponRequest.dto.HongCouponRequestDTO;
import hongshop.hongshop.domain.couponRequest.dto.HongCouponRequestApproveLstDTO;
import hongshop.hongshop.domain.couponRequest.dto.HongCouponRequestLstDTO;
import hongshop.hongshop.domain.couponRequest.vo.HongCouponRequestVO;
import hongshop.hongshop.domain.user.HongUser;
import hongshop.hongshop.domain.user.HongUserRepository;
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
 *          (2) joinAll : 쿠폰리스트에 대해 각각 요청 저장
 *          (3) list : 사용자의 쿠폰 요청 전체 조회 -> 삭제여부 N
 *          (4) listOfUser : '로그인한' 사용자의 쿠폰 요청 전체 조회 -> 삭제여부 N, 요청승인여부 N
 *          (5) delete : 쿠폰 요청 삭제
 *          (6) deleteSeveral : 쿠폰 요청 여러개 삭제
 *          (7) listByCoupon : 쿠폰-ID로 사용자의 쿠폰 요청 전체 조회 -> 삭제여부 N, 요청승인여부 N
 *          (8) approveRequest : 사용자의 쿠폰 요청 승인
 *          (9) insertCouponHas : 'hong-coupon-has' 테이블 저장 (체인 문제로 해당 부분만 빼둠)
**/

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HongCouponRequestServiceImpl implements HongCouponRequestService {

    private final HongCouponRequestRepository hongCouponRequestRepository;
    private final HongCouponRepository hongCouponRepository;
    private final HongCouponHasRepository hongCouponHasRepository;
    private final HongUserRepository hongUserRepository;

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
    @Transactional(readOnly = false)
    public Integer joinAll(HongCouponRequestLstDTO hongCouponRequestLstDTO, HongUser hongUser) {
        Integer joinCnt = 0;

        for (Long couponId : hongCouponRequestLstDTO.getCouponId()) {
            HongCoupon hongCoupon = hongCouponRepository.findById(couponId).orElseThrow(() -> new IllegalArgumentException("there is no coupon"));
            HongCouponRequest hongCouponRequest = HongCouponRequest.hongCouponRequestInsert()
                    .hongCoupon(hongCoupon)
                    .hongUser(hongUser)
                    .build();

            HongCouponRequest save = hongCouponRequestRepository.save(hongCouponRequest);
            joinCnt += 1;
        }

        return joinCnt;
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
    @Transactional(readOnly = false)
    public Integer deleteSeveral(List<Long> ids) {
        Integer deleteCnt = 0;
        for(Long id : ids) {
            HongCouponRequest hongCouponRequest = hongCouponRequestRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("there is no request"));
            hongCouponRequest.deleteRequest();
            deleteCnt += 1;
        }
        return deleteCnt;
    }

    @Override
    public List<HongCouponRequestVO> listByCoupon(Long couponId) {
        List<HongCouponRequest> couponRequestLst = hongCouponRequestRepository.findAllByDeleteYnAndHongCoupon_IdAndRequestApproveYn("N", couponId, "N");
        return couponRequestLst.stream().map(HongCouponRequestVO::new).toList();
    }

    @Override
    @Transactional(readOnly = false)
    public Integer approveRequest(HongCouponRequestApproveLstDTO hongCouponRequestApproveLstDTO) {

        Integer successRequest = 0;

        for (int i = 0; i < hongCouponRequestApproveLstDTO.getRequestId().size(); i++) {
            Long requestId = hongCouponRequestApproveLstDTO.getRequestId().get(i);
            Long userId = hongCouponRequestApproveLstDTO.getUserId().get(i);

            HongCouponRequest hongCouponRequest = hongCouponRequestRepository.findById(requestId).orElseThrow(() -> new IllegalArgumentException("there is no request"));
            HongUser hongUser = hongUserRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("there is no user"));

            hongCouponRequest.approveRequest();
            this.insertCouponHas(hongCouponRequestApproveLstDTO.getCouponId(), hongUser);
            successRequest += 1;
        }

        return successRequest;
    }

    @Override
    @Transactional(readOnly = false)
    public void insertCouponHas(Long couponId, HongUser hongUser) {
        HongCoupon hongCoupon = hongCouponRepository.findById(couponId).orElseThrow(() -> new IllegalArgumentException("there is no coupon"));

        HongCouponHas hongCouponHas = HongCouponHas.hongCouponHasInsert()
                .hongCoupon(hongCoupon)
                .hongUser(hongUser)
                .build();

        hongCouponHasRepository.save(hongCouponHas);
    }
}
