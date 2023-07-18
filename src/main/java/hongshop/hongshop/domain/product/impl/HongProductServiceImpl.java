package hongshop.hongshop.domain.product.impl;

import hongshop.hongshop.domain.category.HongCategory;
import hongshop.hongshop.domain.category.HongCategoryRepository;
import hongshop.hongshop.domain.product.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* @fileName HongProductServiceImpl
* @author dahyeon
* @version 1.0.0
* @date 2023-07-18
* @summary
**/

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HongProductServiceImpl implements HongProductService {

    private final HongProductRepository hongProductRepository;
    private final HongCategoryRepository hongCategoryRepository;

    @Override
    @Transactional(readOnly = false)
    public Long save(HongProductDTO hongProductDTO) {

        HongCategory hongCategory = hongCategoryRepository.findById(hongProductDTO.getHongCategoryId()).orElseThrow(() -> new IllegalArgumentException("there is no category"));

        HongProduct hongProduct = HongProduct.hongProductInsertBuilder()
                .hongCategory(hongCategory)
                .productName(hongProductDTO.getProductName())
                .productCnt(hongProductDTO.getProductCnt())
                .productPrice(hongProductDTO.getProductPrice())
                .productStock(hongProductDTO.getProductCnt())
                .build();

        HongProduct save = hongProductRepository.save(hongProduct);

        return save.getId();
    }

    @Override
    public List<HongProductVO> list() {
        List<HongProduct> list = hongProductRepository.findAll();
        return list.stream().map(HongProductVO::new).toList();
    }

    @Override
    public HongProductVO view(Long id) {
        HongProduct hongProduct = hongProductRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("there is no product"));
        return new HongProductVO(hongProduct);
    }

    @Override
    public HongProduct productInfo(Long id) {
        return hongProductRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("there is no product"));
    }

    @Override
    @Transactional(readOnly = false)
    public void update(HongProductDTO hongProductDTO, Long id) {
        HongProduct hongProduct = hongProductRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("there is no product"));
        hongProduct.updateProduct(hongProductDTO);
    }

    @Override
    @Transactional(readOnly = false)
    public void updateStockCnt(Integer orderCnt, HongProduct hongProduct) {
        hongProduct.updateStockCnt(orderCnt);
    }
}
