package hongshop.hongshop.domain.category.impl;

import hongshop.hongshop.domain.category.HongCategory;
import hongshop.hongshop.domain.category.HongCategoryRepository;
import hongshop.hongshop.domain.category.HongCategoryService;
import hongshop.hongshop.domain.category.dto.HongCategoryDTO;
import hongshop.hongshop.domain.category.vo.HongCategoryVO;
import hongshop.hongshop.domain.file.FileState;
import hongshop.hongshop.domain.fileGroup.HongFileGroupService;
import hongshop.hongshop.domain.fileGroup.vo.HongFileGroupVO;
import hongshop.hongshop.domain.product.HongProduct;
import hongshop.hongshop.domain.product.HongProductRepository;
import hongshop.hongshop.domain.product.vo.HongProductVO;
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
    private final HongFileGroupService hongFileGroupService;

    @Override
    @Transactional(readOnly = false)
    public Long join(HongCategoryDTO hongCategoryDTO) {

        HongCategory hongCategory = HongCategory.hongCategoryInsertBuilder()
                .categoryName(hongCategoryDTO.getCategoryName())
                .description(hongCategoryDTO.getDescription())
                .orderNum(hongCategoryDTO.getOrderNum())
                .build();
        HongCategory save = hongCategoryRepository.save(hongCategory);

        return save.getId();
    }

    @Override
    public List<HongCategoryVO> list() {
        List<HongCategory> all = hongCategoryRepository.findAllByDeleteYnIsOrderByOrderNum("N");
        return all.stream().map(HongCategoryVO::new).toList();
    }

    @Override
    public List<HongCategoryVO> listWithProduct() {
        List<HongCategory> all = hongCategoryRepository.findAllByDeleteYnIsOrderByOrderNum("N");
        return all.stream().map(category -> {
            List<HongProduct> productList = hongProductRepository.findAllByHongCategoryIdAndDeleteYnIs(category.getId(), "N");
            List<HongProductVO> productVOList = productList.stream().map(hongProduct -> {
                if (hongProduct.getFileGroupId() != null) {
                    HongFileGroupVO list = hongFileGroupService.listwithDeleteYnAndFileState(hongProduct.getFileGroupId(), "N", FileState.SAVED);         // if has file-group-id, show together
                    return new HongProductVO(hongProduct, list);
                } else return new HongProductVO(hongProduct);
            }).toList();
            return new HongCategoryVO(category, productVOList);
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
        List<HongProduct> productList = hongProductRepository.findAllByHongCategoryIdAndDeleteYnIs(id, "N");
        List<HongProductVO> list = productList.stream().map(HongProductVO::new).toList();
        return new HongCategoryVO(hongCategory, list);
    }

    @Override
    @Transactional(readOnly = false)
    public void update(HongCategoryDTO hongCategoryDTO, Long id) {
        HongCategory hongCategory = hongCategoryRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("there is no category"));
        hongCategory.updateCategory(hongCategoryDTO);
    }

    @Override
    @Transactional(readOnly = false)
    public void delete(Long id) {
        HongCategory hongCategory = hongCategoryRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("there is no category"));
        hongCategory.deleteCategory();
    }
}
