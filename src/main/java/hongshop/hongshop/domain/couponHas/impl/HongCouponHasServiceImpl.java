package hongshop.hongshop.domain.couponHas.impl;

import hongshop.hongshop.domain.coupon.HongCoupon;
import hongshop.hongshop.domain.coupon.HongCouponService;
import hongshop.hongshop.domain.couponHas.HongCouponHas;
import hongshop.hongshop.domain.couponHas.HongCouponHasRepository;
import hongshop.hongshop.domain.couponHas.HongCouponHasService;
import hongshop.hongshop.domain.couponHas.dto.HongCouponHasLstDTO;
import hongshop.hongshop.domain.couponHas.vo.HongCouponHasVO;
import hongshop.hongshop.domain.couponHist.HongCouponHistService;
import hongshop.hongshop.domain.couponHist.dto.HongCouponHistDTO;
import hongshop.hongshop.domain.user.HongUser;
import hongshop.hongshop.domain.user.HongUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
* @fileName HongCouponHasServiceImpl
* @author dahyeon
* @version 1.0.0
* @date 2023-11-13
* @summary      (1) joinAll : 여러 사용자들에 대해 -> 한번에 쿠폰 등록하기
 *              (2) listByHongUser : 현재 로그인한 사용자의 쿠폰 등록 리스트 조회 -> 'COUPON-HAS' 삭제 여부 N, 사용여부 N
 *                  - 이때 '쿠폰'에 대해 쿠폰의 삭제값이 N, 사용값이 Y
 *              (3) listByHongUserWithDeleteYn : 현재 로그인한 사용자의 쿠폰 등록 리스트 조회 -> 삭제여부 N (사용여부는 안따짐)
 *                  - 이때 '쿠폰'에 대해 쿠폰의 삭제값이 N, 사용값이 Y
 *              (4) delete : 쿠폰 등록 단건 삭제
 *              (5) useCoupon : 쿠폰 사용하기
 *              (6) getHongCouponHas : 사용자 쿠폰 등록 단건 조회 -> return entity
**/


@Service
@RequiredArgsConstructor
@Transactional(readOnly = false)
public class HongCouponHasServiceImpl implements HongCouponHasService {

    private final HongCouponHasRepository hongCouponHasRepository;
    private final HongCouponService hongCouponService;
    private final HongCouponHistService hongCouponHistService;
    private final HongUserRepository hongUserRepository;

    @Override
    @Transactional(readOnly = false)
    public Integer joinAll(HongCouponHasLstDTO hongCouponHasLstDTO) {
        HongCoupon hongCoupon = hongCouponService.getHongCoupon(hongCouponHasLstDTO.getCouponId());

        Integer saveUser = 0;
        for (Long userId: hongCouponHasLstDTO.getUserId()) {
            HongUser hongUser = hongUserRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("there is no user"));
            HongCouponHas hongCouponHas = HongCouponHas.hongCouponHasInsert()
                    .hongCoupon(hongCoupon)
                    .hongUser(hongUser)
                    .build();

            hongCouponHasRepository.save(hongCouponHas);
            saveUser += 1;
        }
        return saveUser;
    }

    @Override
    public List<HongCouponHasVO> listByHongUser(HongUser hongUser) {
        List<HongCouponHas> allByHongUserId = hongCouponHasRepository.findAllByHongUserIdAndDeleteYnAndUseAt(hongUser.getId(), "N", "N");
        return allByHongUserId.stream().filter(hongCouponHas ->
            !"Y".equals(hongCouponHas.getHongCoupon().getDeleteYn()) &&
            !"N".equals(hongCouponHas.getHongCoupon().getUseAt())
        ).map(HongCouponHasVO::new)
                .toList();
    }

    @Override
    public List<HongCouponHasVO> listByHongUserWithDeleteYn(HongUser hongUser) {
        List<HongCouponHas> allLst = hongCouponHasRepository.findAllByHongUserIdAndDeleteYn(hongUser.getId(), "N");
        return allLst.stream().filter(hongCouponHas ->
            !"Y".equals(hongCouponHas.getHongCoupon().getDeleteYn()) &&
            !"N".equals(hongCouponHas.getHongCoupon().getUseAt())
        ).map(HongCouponHasVO::new)
                .toList();
    }

    @Override
    @Transactional(readOnly = false)
    public void delete(Long id) {
        HongCouponHas hongCouponHas = hongCouponHasRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("there is no coupon has"));
        hongCouponHas.deleteHongCouponHas();
    }

    @Override
    @Transactional(readOnly = false)
    public Integer useCoupon(Long id) {
        HongCouponHas hongCouponHas = hongCouponHasRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("there is no coupon has"));
        hongCouponHas.useHongCoupon();

        // insert in coupon-hist table
        hongCouponHistService.join(new HongCouponHistDTO(id));

        return hongCouponHas.getHongCoupon().getCouponRate();
    }

    @Override
    public HongCouponHas getHongCouponHas(Long id) {
        return hongCouponHasRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("there is no coupon has"));
    }
}
