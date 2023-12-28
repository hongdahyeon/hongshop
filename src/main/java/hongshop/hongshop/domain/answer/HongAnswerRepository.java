package hongshop.hongshop.domain.answer;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @fileName HongAnswerRepository
 * @author dahyeon
 * @version 1.0.0
 * @date 2023-07-17
 * @summary     (1) findAllByHongPostIdAndDeleteYnIs : 게시글 Id, 삭제여부 N 으로 답변 리스트 조회
 **/

public interface HongAnswerRepository extends JpaRepository<HongAnswer, Long> {

    List<HongAnswer> findAllByHongPostIdAndDeleteYnIs(Long hongPostId, String deleteYn);
}
