package hongshop.hongshop.domain.couponHas;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
* @fileName HongCouponHasRepository
* @author dahyeon
* @version 1.0.0
* @date 2023-11-13
* @summary  (1)  findAllByDeleteYnIs : 전체 리스트 조회 -> 삭제여부 N
 *          (2) findAllByHongUserIdAndDeleteYnAndUseAt : 전체 리스트 조회 -> 사용자ID, 삭제여부 N, 사용여부 N
 *              - 사용여부가 Y이면 이미 사용한 쿠폰
 *          (3) findAllByUseAtAndDeleteYnAndHongCoupon_Id : 전체 리스트 조회 -> 사용여부 N, 삭제여부 N, 쿠폰 ID
 *              - 관리자 입장에서 쿠폰을 삭제할 수 있는 경우는
 *                  -> 쿠폰 사용여부가 N일 떄
 *                  -> 쿠폰 사용여부가 Y이지만, 해당 쿠폰을 갖고 있는 사용자들이 이미 쿠폰을 사용했거나 삭제했을 때
 *                     그렇기 떄문에 사용여부가 N인것을 찾아서 아직 사용안하고 갖고 있는 쿠폰ID가 있는지 찾는다.
**/

public interface HongCouponHasRepository extends JpaRepository<HongCouponHas, Long> {

    List<HongCouponHas> findAllByDeleteYnIs(String deleteYn);

    List<HongCouponHas> findAllByHongUserIdAndDeleteYnAndUseAt(Long userId, String deleteYn, String useAt);

    List<HongCouponHas> findAllByUseAtAndDeleteYnAndHongCoupon_Id(String useAt, String deleteYn, Long couponId);
}
