package hongshop.hongshop.domain.product;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
* @fileName HongProductRepository
* @author dahyeon
* @version 1.0.0
* @date 2023-07-18
* @summary
**/

public interface HongProductRepository extends JpaRepository<HongProduct, Long> {

    List<HongProduct> findAllByHongCategoryIdAndDeleteYnIs(Long id, String deleteYn);

    List<HongProduct> findAllByDeleteYnAndNewProductYn(String deleteYn, String newProductYn);
}
