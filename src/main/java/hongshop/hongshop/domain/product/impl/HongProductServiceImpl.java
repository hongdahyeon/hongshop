package hongshop.hongshop.domain.product.impl;

import hongshop.hongshop.domain.category.HongCategory;
import hongshop.hongshop.domain.category.HongCategoryRepository;
import hongshop.hongshop.domain.file.FileState;
import hongshop.hongshop.domain.file.HongFileService;
import hongshop.hongshop.domain.fileGroup.HongFileGroupService;
import hongshop.hongshop.domain.fileGroup.vo.HongFileGroupVO;
import hongshop.hongshop.domain.orderDetail.HongOrderDetailService;
import hongshop.hongshop.domain.orderDetail.vo.HongOrderDetailUserVO;
import hongshop.hongshop.domain.product.HongProduct;
import hongshop.hongshop.domain.product.HongProductRepository;
import hongshop.hongshop.domain.product.HongProductService;
import hongshop.hongshop.domain.product.dto.HongProductDTO;
import hongshop.hongshop.domain.product.vo.HongPrdouctUserVO;
import hongshop.hongshop.domain.product.vo.HongProductManagerVO;
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
 *              (3) view : 상품 정보 조회 with file
 *              (4) viewCheckUser : 상품 정보 조회 with file
 *                  -> check order user : order-status is 'CHARGED, DELIVER_ING'
 *              (5) productInfo : 상품 정보 조회 -> return entity
 *              (6) update : 상품 정보 업데이트 -> 상품 개수 변경에 따른 상품 재고값 변경
 *              (7) updateStockCnt : 주문 정보에 따른 상품 재고값 변경
 *              (8) delete : 상품 삭제 (deleteYn)
 *              (9) productUser : 상품ID를 통한 주문자 리스트 조회
 *              (10) getNewProducts : 최산 상품 가져오기 (newProduct 컬럼을 통해)
**/

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HongProductServiceImpl implements HongProductService {

    private final HongProductRepository hongProductRepository;
    private final HongCategoryRepository hongCategoryRepository;
    private final HongOrderDetailService hongOrderDetailService;
    private final HongFileService hongFileService;
    private final HongFileGroupService hongFileGroupService;

    @Override
    @Transactional(readOnly = false)
    public Long save(HongProductDTO hongProductDTO) {

        HongCategory hongCategory = hongCategoryRepository.findById(hongProductDTO.getHongCategoryId()).orElseThrow(() -> new IllegalArgumentException("there is no category"));

        // 1. delete file from list : deleteFile
        if(hongProductDTO.getDeleteFile().size() != 0){
            hongFileService.deleteFiles(hongProductDTO.getDeleteFile());
        }

        HongProduct hongProduct = null;
        if(hongProductDTO.getFileGroupId() == null) {

            hongProduct = HongProduct.hongProductInsertBuilder()
                    .hongCategory(hongCategory)
                    .productName(hongProductDTO.getProductName())
                    .productCnt(hongProductDTO.getProductCnt())
                    .productPrice(hongProductDTO.getProductPrice())
                    .productStock(hongProductDTO.getProductCnt())
                    .newProductYn(hongProductDTO.getNewProductYn())
                    .build();
        }else {
            hongFileService.updateFileState(hongProductDTO.getFileGroupId());
            hongProduct = HongProduct.hongProductInsertBuilder()
                    .hongCategory(hongCategory)
                    .productName(hongProductDTO.getProductName())
                    .productCnt(hongProductDTO.getProductCnt())
                    .productPrice(hongProductDTO.getProductPrice())
                    .productStock(hongProductDTO.getProductCnt())
                    .fileGroupId(hongProductDTO.getFileGroupId())           // if has file, insert file-group-id
                    .newProductYn(hongProductDTO.getNewProductYn())
                    .build();
        }

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
        if(hongProduct.getFileGroupId() != null) {
            HongFileGroupVO list = hongFileGroupService.listwithDeleteYnAndFileState(hongProduct.getFileGroupId(), "N", FileState.SAVED);         // if has file-group-id, show together
            return new HongProductVO(hongProduct, list);
        }else return new HongProductVO(hongProduct);
    }

    @Override
    public HongProductManagerVO viewCheckUser(Long id) {
        HongProduct hongProduct = hongProductRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("there is no product"));
        boolean empty = hongOrderDetailService.emptyChkByProductId(id);
        if(hongProduct.getFileGroupId() != null) {
            HongFileGroupVO list = hongFileGroupService.listwithDeleteYnAndFileState(hongProduct.getFileGroupId(), "N", FileState.SAVED);         // if has file-group-id, show together
            return new HongProductManagerVO(hongProduct, list, empty);
        }else return new HongProductManagerVO(hongProduct, empty);
    }

    @Override
    public HongProduct productInfo(Long id) {
        return hongProductRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("there is no product"));
    }

    @Override
    @Transactional(readOnly = false)
    public void update(HongProductDTO hongProductDTO, Long id) {
        HongProduct hongProduct = hongProductRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("there is no product"));
        if(hongProductDTO.getDeleteFile().size() != 0){
            hongFileService.deleteFiles(hongProductDTO.getDeleteFile());            // if has delete-files, delete it
        }
        if(hongProductDTO.getFileGroupId() != null) hongFileService.updateFileState(hongProductDTO.getFileGroupId());
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
    public HongPrdouctUserVO productUser(Long id) {
        HongProduct product = hongProductRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("there is no product"));
        List<HongOrderDetailUserVO> orderDetails = hongOrderDetailService.listByProductId(id);  // 상품Id를 통해 주문 사용자 정보 리스트를 가져온다.
        return new HongPrdouctUserVO(product, orderDetails);
    }

    @Override
    public List<HongProductVO> getNewProducts() {
        List<HongProduct> products = hongProductRepository.findAllByDeleteYnAndNewProductYn("N", "Y");// deleteYn: N , newProductYn: Y
        return products.stream().map(hongProduct -> {
            if(hongProduct.getFileGroupId() != null) {
                HongFileGroupVO list = hongFileGroupService.listwithDeleteYnAndFileState(hongProduct.getFileGroupId(), "N", FileState.SAVED);         // if has file-group-id, show together
                return new HongProductVO(hongProduct, list);
            }else return new HongProductVO(hongProduct);
        }).toList();
    }
}
