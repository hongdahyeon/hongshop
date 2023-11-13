package hongshop.hongshop.domain.cart.impl;

import hongshop.hongshop.domain.cart.*;
import hongshop.hongshop.domain.cart.dto.HongCartDTO;
import hongshop.hongshop.domain.cart.vo.HongCartVO;
import hongshop.hongshop.domain.file.FileState;
import hongshop.hongshop.domain.fileGroup.HongFileGroupService;
import hongshop.hongshop.domain.fileGroup.vo.HongFileGroupVO;
import hongshop.hongshop.domain.product.HongProduct;
import hongshop.hongshop.domain.product.HongProductService;
import hongshop.hongshop.domain.user.HongUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
* @fileName HongCartServiceImpl
* @author dahyeon
* @version 1.0.0
* @date 2023-07-18
* @summary      (1) saveLst : 장바구니 저장 (여러개)
 *              (2) save : 장바구니 저장 (단건)
 *              (3) getUsersListOfCartById : user의 id로 장바구니 리스트 가져오기
 *              (4) delete : 장바구니 삭제
 *              (5) deleteSeveral : 장바구니 여러개 삭제
 *              (6) listOfChoose : 장바구니에서 선택한 값들 조회 -> 해당 정보 및 해당상품의 file 정보
 *              (7) updateCnt : 장바구니 담기는 개수 변경
 *              (8) findIfEmpty : user-id와 product-id로 해당 사용자가 해당 상품을 장바구니에 담았는지 확인하기
**/

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HongCartServiceImpl implements HongCartService {

    private final HongCartRepository hongCartRepository;
    private final HongProductService hongProductService;
    private final HongFileGroupService hongFileGroupService;

    @Override
    @Transactional(readOnly = false)
    public Integer saveLst(List<HongCartDTO> cartDTOList, HongUser hongUser) {

        int savedCart = 0;

        for (HongCartDTO hongCartDTO : cartDTOList) {

            // 1. find product by productId first
            HongProduct product = hongProductService.productInfo(hongCartDTO.getHongProductId());
            Integer orderPrice = hongCartDTO.getCartCnt() * product.getProductPrice();

            HongCart hongCart = HongCart.hongCartInsertBuilder()
                    .hongUser(hongUser)
                    .hongProduct(product)
                    .cartCnt(hongCartDTO.getCartCnt())
                    .cartPrice(orderPrice)
                    .build();

            hongCartRepository.save(hongCart);
            savedCart = savedCart + 1;

        }
        return savedCart;
    }

    @Override
    @Transactional(readOnly = false)
    public Long save(HongCartDTO hongCartDTO, HongUser hongUser) {

        HongProduct product = hongProductService.productInfo(hongCartDTO.getHongProductId());
        Integer orderPrice = hongCartDTO.getCartCnt() * product.getProductPrice();

        HongCart hongCart = HongCart.hongCartInsertBuilder()
                .hongUser(hongUser)
                .hongProduct(product)
                .cartCnt(hongCartDTO.getCartCnt())
                .cartPrice(orderPrice)
                .build();
        HongCart save = hongCartRepository.save(hongCart);
        return save.getId();
    }

    @Override
    public List<HongCartVO> getUsersListOfCartById(Long id) {
        List<HongCart> listOfCart = hongCartRepository.findAllByHongUserIdAndDeleteYn(id, "N");
        return listOfCart.stream().map(hongCart -> {
            Long fileGroupId = hongCart.getHongProduct().getFileGroupId();
            HongFileGroupVO list = hongFileGroupService.listwithDeleteYnAndFileState(fileGroupId, "N", FileState.SAVED);         // if has file-group-id, show together
            return new HongCartVO(hongCart, list);
        }).toList();
    }

    @Override
    @Transactional(readOnly = false)
    public void delete(Long id) {
        HongCart hongCart = hongCartRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("there is no cart"));
        hongCart.deleteCart();
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteSeveral(Long[] ids) {
        for(Long id : ids) {
            HongCart hongCart = hongCartRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("there is no cart"));
            hongCart.deleteCart();
        }
    }

    @Override
    public List<HongCartVO> listOfChoose(List<Long> ids) {
        List<HongCartVO> hongCarts = new ArrayList<>();
        if(ids != null && ids.size() > 0 ) {
            for (Long id : ids) {
                HongCart hongCart = hongCartRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("there is no cart"));
                Long fileGroupId = hongCart.getHongProduct().getFileGroupId();
                HongFileGroupVO list = hongFileGroupService.listwithDeleteYnAndFileState(fileGroupId, "N", FileState.SAVED);         // if has file-group-id, show together
                hongCarts.add(new HongCartVO(hongCart, list));
            }
        }
        return hongCarts;
    }

    @Override
    @Transactional(readOnly = false)
    public void updateCnt(Long id, HongCartDTO hongCartDTO) {
        HongCart hongCart = hongCartRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("there is no cart"));
        HongProduct product = hongProductService.productInfo(hongCartDTO.getHongProductId());
        Integer orderPrice = hongCartDTO.getCartCnt() * product.getProductPrice();

        hongCart.updateCntAndPrice(hongCartDTO.getCartCnt(), orderPrice);
    }

    @Override
    public boolean findIfEmpty(Long userId, Long productId) {
        List<HongCart> hongcarts = hongCartRepository.findAllByHongUserIdAndHongProductIdAndDeleteYn(userId, productId, "N");
        return hongcarts.isEmpty();
    }
}
