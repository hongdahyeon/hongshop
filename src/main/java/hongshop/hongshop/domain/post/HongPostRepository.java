package hongshop.hongshop.domain.post;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * @fileName HongPostRepository
 * @author dahyeon
 * @version 1.0.0
 * @date 2023-07-17
 * @summary (1) findAllByHongPostTypeIdAndDeleteYnIs : 게시글 타입 Id, 삭제여부 N -> 게시글 리스트 조회
 **/

public interface HongPostRepository extends JpaRepository<HongPost, Long> {

    List<HongPost> findAllByHongPostTypeIdAndDeleteYnIs(Long honPostTypeId, String deleteYn);
}
