package hongshop.hongshop.domain.category.impl;

import hongshop.hongshop.domain.category.*;
import hongshop.hongshop.domain.product.HongProduct;
import hongshop.hongshop.domain.product.HongProductRepository;
import hongshop.hongshop.domain.product.HongProductVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @fileName HongCategoryServiceImpl
 * @author dahyeon
 * @version 1.0.0
 * @date 2023-07-17
 * @summary
 **/

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HongCategoryServiceImpl implements HongCategoryService {

    private final HongCategoryRepository hongCategoryRepository;
    private final HongProductRepository hongProductRepository;

    @Override
    @Transactional(readOnly = false)
    public Long join(HongCategoryDTO hongCategoryDTO) {

        HongCategory hongCategory = HongCategory.hongCategoryInsertBuilder()
                .categoryName(hongCategoryDTO.getCategoryName())
                .description(hongCategoryDTO.getDescription())
                .build();
        HongCategory save = hongCategoryRepository.save(hongCategory);

        return save.getId();
    }

    @Override
    public List<HongCategoryVO> list() {
        List<HongCategory> all = hongCategoryRepository.findAll();
        return all.stream().map(HongCategoryVO::new).toList();
    }

    @Override
    public List<HongCategoryVO> listWithProduct() {
        List<HongCategory> all = hongCategoryRepository.findAll();
        return all.stream().map(category -> {
            List<HongProduct> productList = hongProductRepository.findAllByHongCategoryId(category.getId());
            List<HongProductVO> list = productList.stream().map(HongProductVO::new).toList();
            return new HongCategoryVO(category, list);
        }).toList();
    }

    @Override
    public HongCategoryVO show(Long id) {
        HongCategory hongCategory = hongCategoryRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("there is no category"));
        return new HongCategoryVO(hongCategory);
    }

    @Override
    public HongCategoryVO showWithProduct(Long id) {
        HongCategory hongCategory = hongCategoryRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("there is no category"));
        List<HongProduct> productList = hongProductRepository.findAllByHongCategoryId(id);
        List<HongProductVO> list = productList.stream().map(HongProductVO::new).toList();
        return new HongCategoryVO(hongCategory, list);
    }

    @Override
    @Transactional(readOnly = false)
    public void update(HongCategoryDTO hongCategoryDTO, Long id) {
        HongCategory hongCategory = hongCategoryRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("there is no category"));
        hongCategory.updateCategory(hongCategoryDTO);
    }
}
