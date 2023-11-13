package hongshop.hongshop.domain.postType;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
* @fileName HongPostTypeRepository
* @author dahyeon
* @version 1.0.0
* @date 2023-08-08
* @summary
 *              (1) findAllByDeleteYnOrderByOrderNum : 전체 조회 - 삭제여부 / 순서정렬
 *              (2) findAllByDeleteYnAndUseAtOrderByOrderNum : 전체 조회 - 삭제 여부, 사용여부 / 순서정렬
 *              (3) findAllByDeleteYnAndUseAtAndPostType : 전체 조회 - 삭제 여부, 사용여부 , 게시판 유형
**/

public interface HongPostTypeRepository extends JpaRepository<HongPostType, Long> {

    List<HongPostType> findAllByDeleteYnOrderByOrderNum(String deleteAt);

    List<HongPostType> findAllByDeleteYnAndUseAtOrderByOrderNum(String deleteAt, String useAt);
    
    List<HongPostType> findAllByDeleteYnAndUseAtAndPostType(String deleteAt, String useAt, PostType postType);
}
