package hongshop.hongshop.domain.couponHas.impl;

import hongshop.hongshop.domain.coupon.HongCoupon;
import hongshop.hongshop.domain.coupon.HongCouponService;
import hongshop.hongshop.domain.couponHas.HongCouponHas;
import hongshop.hongshop.domain.couponHas.HongCouponHasRepository;
import hongshop.hongshop.domain.couponHas.HongCouponHasService;
import hongshop.hongshop.domain.couponHas.dto.HongCouponHasDTO;
import hongshop.hongshop.domain.couponHas.dto.HongCouponHasLstDTO;
import hongshop.hongshop.domain.couponHas.vo.HongCouponHasVO;
import hongshop.hongshop.domain.couponHist.HongCouponHistService;
import hongshop.hongshop.domain.couponHist.dto.HongCouponHistDTO;
import hongshop.hongshop.domain.user.HongUser;
import hongshop.hongshop.domain.user.HongUserRepository;
import hongshop.hongshop.domain.user.HongUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
* @fileName HongCouponHasServiceImpl
* @author dahyeon
* @version 1.0.0
* @date 2023-11-13
* @summary      (1) join : 해당 로그인 사용자에 대해 쿠폰 등록
 *              (2) joinAll : 여러 사용자들에 대해 -> 한번에 쿠폰 등록하기
 *              (3) list : 전체 사용자의 쿠폰 조회 -> 삭제 여부 N
 *              (4) view : 사용자의 쿠폰 등록 단건 조회
 *              (5) listByHongUser : 현재 로그인한 사용자의 쿠폰 등록 리스트 조회 -> 삭제 여부 N
 *                  - 이때 '쿠폰'에 대해 쿠폰의 삭제값이 N, 사용값이 Y
 *              (6) listByHongUserWithDeleteYn : 현재 로그인한 사용자의 쿠폰 등록 리스트 조회 -> 삭제여부 N (사용여부는 안따짐)
 *                  - 이때 '쿠폰'에 대해 쿠폰의 삭제값이 N, 사용값이 Y
 *              (7) delete : 쿠폰 등록 단건 삭제
 *              (8) useCoupon : 쿠폰 사용하기
 *              (9) getHongCouponHas : 사용자 쿠폰 등록 단건 조회 -> return entity
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
    public Long join(HongUser hongUser, HongCouponHasDTO hongCouponHasDTO) {
        HongCoupon hongCoupon = hongCouponService.getHongCoupon(hongCouponHasDTO.getHongCouponId());

        HongCouponHas hongCouponHas = HongCouponHas.hongCouponHasInsert()
                                                .hongCoupon(hongCoupon)
                                                .hongUser(hongUser)
                                                .build();

        HongCouponHas save = hongCouponHasRepository.save(hongCouponHas);
        return save.getId();
    }

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
    public List<HongCouponHasVO> list() {
        List<HongCouponHas> list = hongCouponHasRepository.findAllByDeleteYnIs("N");
        return list.stream().map(HongCouponHasVO::new).toList();
    }

    @Override
    public HongCouponHasVO view(Long id) {
        HongCouponHas hongCouponHas = hongCouponHasRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("there is no coupon has"));
        return new HongCouponHasVO(hongCouponHas);
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
