package hongshop.hongshop.domain.couponHist.impl;

import hongshop.hongshop.domain.couponHas.HongCouponHas;
import hongshop.hongshop.domain.couponHas.HongCouponHasRepository;
import hongshop.hongshop.domain.couponHist.HongCouponHist;
import hongshop.hongshop.domain.couponHist.HongCouponHistRepository;
import hongshop.hongshop.domain.couponHist.HongCouponHistService;
import hongshop.hongshop.domain.couponHist.dto.HongCouponHistDTO;
import hongshop.hongshop.domain.couponHist.vo.HongCouponHistVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* @fileName HongCouponHistServiceImpl
* @author dahyeon
* @version 1.0.0
* @date 2023-11-13
* @summary  (1) join : 사용자 쿠폰 이용 이력 저장
 *          (2) list : 사용자들 쿠폰 이용 이력 전체 조회
**/


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HongCouponHistServiceImpl implements HongCouponHistService {

    private final HongCouponHistRepository hongCouponHistRepository;
    private final HongCouponHasRepository hongCouponHasRepository;

    @Override
    @Transactional(readOnly = false)
    public Long join(HongCouponHistDTO hongCouponHistDTO) {
        HongCouponHas hongCouponHas = hongCouponHasRepository.findById(hongCouponHistDTO.getHongCouponHasId()).orElseThrow(() -> new IllegalArgumentException("there is no coupon has"));

        HongCouponHist hongCouponHist = HongCouponHist.hongCouponHistInsert()
                                            .hongCouponHas(hongCouponHas)
                                            .build();
        HongCouponHist save = hongCouponHistRepository.save(hongCouponHist);
        return save.getId();
    }

    @Override
    public List<HongCouponHistVO> list() {
        List<HongCouponHist> all = hongCouponHistRepository.findAll();
        return all.stream().map(HongCouponHistVO::new).toList();
    }
}
