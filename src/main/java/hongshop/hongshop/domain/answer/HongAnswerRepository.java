package hongshop.hongshop.domain.answer;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HongAnswerRepository extends JpaRepository<HongAnswer, Long> {

    public List<HongAnswer> findAllByHongPostId(Long hongPostId);
}
