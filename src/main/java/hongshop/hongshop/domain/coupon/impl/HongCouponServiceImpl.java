package hongshop.hongshop.domain.coupon.impl;

import hongshop.hongshop.domain.coupon.HongCoupon;
import hongshop.hongshop.domain.coupon.HongCouponRepository;
import hongshop.hongshop.domain.coupon.HongCouponService;
import hongshop.hongshop.domain.coupon.dto.HongCouponDTO;
import hongshop.hongshop.domain.coupon.vo.HongCouponGroupHistVO;
import hongshop.hongshop.domain.coupon.vo.HongCouponVO;
import hongshop.hongshop.domain.couponHas.HongCouponHasRepository;
import hongshop.hongshop.domain.couponHist.HongCouponHistService;
import hongshop.hongshop.domain.couponHist.vo.HongCouponHistUserVO;
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
 *          (7) listWithChkUser : 쿠폰 전체 리스트 조회 -> 삭제여부 N
 *              - 해당 쿠폰을 아직 사용하지 않고 갖고 있는 사람이 있는지 체크 (없다면 해당 쿠폰은 삭제 가능)
 *           (8) couponAndUserHist : 쿠폰 전체 조회 -> 삭제여주 N
 *              - 이떄, 해당 쿠폰을 사용한 사용자 이력 리스트도 함께 조회
**/

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HongCouponServiceImpl implements HongCouponService {

    private final HongCouponRepository hongCouponRepository;
    private final HongCouponHasRepository hongCouponHasRepository;
    private final HongCouponHistService hongCouponHistService;

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
    public List<HongCouponVO> listWithChkUser() {
        List<HongCoupon> all = hongCouponRepository.findAllByDeleteYnIs("N");
        return all.stream().map(hongCoupon -> {
            boolean userIsEmpty = true;
            if("Y".equals(hongCoupon.getUseAt())) userIsEmpty = hongCouponHasRepository.findAllByUseAtAndDeleteYnAndHongCoupon_Id("N", "N", hongCoupon.getId()).isEmpty();
            return new HongCouponVO(hongCoupon, userIsEmpty);
        }).toList();
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

    @Override
    public List<HongCouponGroupHistVO> couponAndUserHist() {
        List<HongCoupon> all = hongCouponRepository.findAllByDeleteYnIs("N");
        return all.stream().map(hongCoupon -> {
            List<HongCouponHistUserVO> hongCouponHistUserVOS = hongCouponHistService.listByCouponId(hongCoupon.getId());
            return new HongCouponGroupHistVO(hongCoupon, hongCouponHistUserVOS);
        }).toList();
    }
}
