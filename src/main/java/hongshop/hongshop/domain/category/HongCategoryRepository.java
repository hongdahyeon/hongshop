package hongshop.hongshop.domain.category;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @fileName HongCategoryRepository
 * @author dahyeon
 * @version 1.0.0
 * @date 2023-07-17
 * @summary     (1) findAllByDeleteYnIsOrderByOrderNum : 카테고리 정보 리스트 조회 
 *                  -> 삭제여부 N, orderNum으로 정렬
 **/

public interface HongCategoryRepository extends JpaRepository<HongCategory, Long> {

    List<HongCategory> findAllByDeleteYnIsOrderByOrderNum(String deleteYn);
}
