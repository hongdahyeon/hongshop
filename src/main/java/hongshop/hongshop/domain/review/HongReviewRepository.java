package hongshop.hongshop.domain.review;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HongReviewRepository extends JpaRepository<HongReview, Long> {

    List<HongReview> findAllByHongUserId(Long id);
}
