package hongshop.hongshop.domain.coupon.impl;

import hongshop.hongshop.domain.coupon.HongCoupon;
import hongshop.hongshop.domain.coupon.HongCouponRepository;
import hongshop.hongshop.domain.coupon.HongCouponService;
import hongshop.hongshop.domain.coupon.dto.HongCouponDTO;
import hongshop.hongshop.domain.coupon.vo.HongCouponVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* @fileName HongCouponServiceImpl
* @author dahyeon
* @version 1.0.0
* @date 2023-11-13
* @summary  (1) join : 쿠폰 저장
 *          (2) list : 쿠폰 전체 리스트 조회 ->  삭제 여부 N
 *          (3) view : 쿠폰 단건 조회
 *          (4) update : 쿠폰 수정
 *          (5) delete : 쿠폰 삭제
 *          (6) getHongCoupon : 쿠폰 단건 조회 -> return entity
**/

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HongCouponServiceImpl implements HongCouponService {

    private final HongCouponRepository hongCouponRepository;

    @Override
    @Transactional(readOnly = false)
    public Long join(HongCouponDTO hongCouponDTO) {

        HongCoupon hongCoupon = HongCoupon.hongCouponInsert()
                .couponName(hongCouponDTO.getCouponName())
                .couponRate(hongCouponDTO.getCouponRate())
                .useAt(hongCouponDTO.getUseAt())
                .startDate(hongCouponDTO.getStartDate())
                .endDate(hongCouponDTO.getEndDate())
                .build();

        HongCoupon save = hongCouponRepository.save(hongCoupon);
        return save.getId();
    }

    @Override
    public List<HongCouponVO> list() {
        List<HongCoupon> all = hongCouponRepository.findAllByDeleteYnIs("N");
        return all.stream().map(HongCouponVO::new).toList();
    }

    @Override
    public HongCouponVO view(Long id) {
        HongCoupon hongCoupon = hongCouponRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("there is no coupon"));
        return new HongCouponVO(hongCoupon);
    }

    @Override
    @Transactional(readOnly = false)
    public void update(Long id, HongCouponDTO hongCouponDTO) {
        HongCoupon hongCoupon = hongCouponRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("there is no coupon"));
        hongCoupon.updateCoupon(hongCouponDTO);
    }

    @Override
    @Transactional(readOnly = false)
    public void delete(Long id) {
        HongCoupon hongCoupon = hongCouponRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("there is no coupon"));
        hongCoupon.deleteCoupon();
    }

    @Override
    public HongCoupon getHongCoupon(Long id) {
        return hongCouponRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("there is no coupon"));
    }
}