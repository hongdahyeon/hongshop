package hongshop.hongshop.domain.post;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * @fileName HongPostRepository
 * @author dahyeon
 * @version 1.0.0
 * @date 2023-07-17
 * @summary
 **/

public interface HongPostRepository extends JpaRepository<HongPost, Long> {

    public List<HongPost> findAllByHongPostTypeId(Long honPostTypeId);
}
