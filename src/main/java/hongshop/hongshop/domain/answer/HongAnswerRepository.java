package hongshop.hongshop.domain.answer;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @fileName HongAnswerRepository
 * @author dahyeon
 * @version 1.0.0
 * @date 2023-07-17
 * @summary
 **/

public interface HongAnswerRepository extends JpaRepository<HongAnswer, Long> {

    public List<HongAnswer> findAllByHongPostId(Long hongPostId);
}
