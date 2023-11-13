package hongshop.hongshop.domain.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
* @fileName HongCartRepository
* @author dahyeon
* @version 1.0.0
* @date 2023-07-18
* @summary  (1) findAllByHongUserIdAndDeleteYn : 로그인한 사용자의 장바구니 정보 리스트로 불러오기 (장바구니 delteYn, 상품 deleteYn -> 'N')
 *          (2) findAllByHongUserIdAndHongProductIdAndDeleteYn : 로그인한 사용자의 장바구니에서 'product'가 있는지 체크
**/

public interface HongCartRepository extends JpaRepository<HongCart, Long> {
    @Query("SELECT c FROM HongCart c JOIN FETCH c.hongProduct p WHERE c.hongUser.id = :id AND c.deleteYn = :deleteYn AND p.deleteYn = :deleteYn")
    List<HongCart> findAllByHongUserIdAndDeleteYn(@Param("id") Long id, @Param("deleteYn") String deleteYn);

    @Query("SELECT c FROM HongCart c JOIN FETCH c.hongProduct p WHERE c.hongUser.id = :userId AND p.id = :productId AND c.deleteYn = :deleteYn AND p.deleteYn = :deleteYn")
    List<HongCart> findAllByHongUserIdAndHongProductIdAndDeleteYn(@Param("userId") Long userId, @Param("productId") Long productId, @Param("deleteYn") String deleteYn);
}
