package hongshop.hongshop.domain.product.impl;

import hongshop.hongshop.domain.category.HongCategory;
import hongshop.hongshop.domain.category.HongCategoryRepository;
import hongshop.hongshop.domain.orderDetail.HongOrderDetailService;
import hongshop.hongshop.domain.orderDetail.vo.HongOrderDetailUserVO;
import hongshop.hongshop.domain.product.*;
import hongshop.hongshop.domain.product.dto.HongProductDTO;
import hongshop.hongshop.domain.product.vo.HongPrdouctUserVO;
import hongshop.hongshop.domain.product.vo.HongProductVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* @fileName HongProductServiceImpl
* @author dahyeon
* @version 1.0.0
* @date 2023-07-18
* @summary      (1) save : 상품 정보를 저장
 *              (2) list : 상품 정보 리스트 조회
 *              (3) view, productInfo : 상품 정보 조회
 *              (4) update : 상품 정보 업데이트 -> 상품 개수 변경에 따른 상품 재고값 변경
 *              (5) updateStockCnt : 주문 정보에 따른 상품 재고값 변경
 *              (6) delete : 상품 삭제 (deleteYn)
 *              (7) productUser : 상품ID를 통한 주문자 리스트 조회
**/

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HongProductServiceImpl implements HongProductService {

    private final HongProductRepository hongProductRepository;
    private final HongCategoryRepository hongCategoryRepository;
    private final HongOrderDetailService hongOrderDetailService;

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

    @Override
    @Transactional(readOnly = false)
    public void delete(Long id) {
        HongProduct product = hongProductRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("there is no product"));
        product.deleteProduct();
    }

    @Override
    @Transactional(readOnly = true)
    public HongPrdouctUserVO productUser(Long id) {
        HongProduct product = hongProductRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("there is no product"));
        List<HongOrderDetailUserVO> orderDetails = hongOrderDetailService.listByProductId(id);
        return new HongPrdouctUserVO(product, orderDetails);
    }
}
