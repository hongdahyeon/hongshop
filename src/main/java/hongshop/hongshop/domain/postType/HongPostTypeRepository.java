package hongshop.hongshop.domain.postType;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
* @fileName HongPostTypeRepository
* @author dahyeon
* @version 1.0.0
* @date 2023-08-08
* @summary
 *              (1) findAllByDeleteAtOrderByOrderNum : 전체 조회 - 삭제여부 / 순서정렬
 *              (2) findAllByDeleteAtAndUseAtOrderByOrderNum : 전체 조회 - 삭제 여부, 사용여부 / 순서정렬
**/

public interface HongPostTypeRepository extends JpaRepository<HongPostType, Long> {

    List<HongPostType> findAllByDeleteAtOrderByOrderNum(String deleteAt);

    List<HongPostType> findAllByDeleteAtAndUseAtOrderByOrderNum(String deleteAt, String useAt);
}
