package hongshop.hongshop.domain.category;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @fileName HongCategoryRepository
 * @author dahyeon
 * @version 1.0.0
 * @date 2023-07-17
 * @summary
 **/

public interface HongCategoryRepository extends JpaRepository<HongCategory, Long> {

    List<HongCategory> findAllByDeleteYnIs(String deleteYn);
}
