package hongshop.hongshop.domain.cart.impl;

import hongshop.hongshop.domain.cart.HongCart;
import hongshop.hongshop.domain.cart.HongCartRepository;
import hongshop.hongshop.domain.cart.HongCartService;
import hongshop.hongshop.domain.cart.dto.HongCartDTO;
import hongshop.hongshop.domain.cart.vo.HongCartWithProductVO;
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
* @summary      (1) save : 로그인한 사용자의 장바구니 저장 (단건)
 *              (2) getUsersListOfCartById : user의 id로 장바구니 리스트 가져오기 (삭제여부 N)
 *                  -> 상품 정보 함께 조회 (상품 사진 정보도 함께 조회 : 삭제여부 N, 저장여부 SAVED)
 *              (3) delete : 장바구니 삭제
 *              (4) deleteSeveral : 장바구니 여러개 삭제
 *              (5) listOfChoose : 장바구니에서 선택한 값들 조회
 *                  -> 상품 정보 함께 조회 (상품 사진 정보도 함께 조회 : 삭제여부 N, 저장여부 SAVED)
 *              (6) updateCnt : 장바구니 담기는 개수 변경
 *                  -> 이에 따른 가격 변경
 *              (7) findIfEmpty : user-id와 product-id로 해당 사용자가 해당 상품을 장바구니에 담았는지 확인하기
 *                  -> 만일 담겼더라도, 장바구니에서 삭제된 상품인지 체크 (삭제여부 N)
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
    public List<HongCartWithProductVO> getUsersListOfCartById(Long id) {
        List<HongCart> listOfCart = hongCartRepository.findAllByHongUserIdAndDeleteYn(id, "N");
        return listOfCart.stream().map(hongCart -> {
            Long fileGroupId = hongCart.getHongProduct().getFileGroupId();
            HongFileGroupVO list = hongFileGroupService.listWithDeleteYnAndFileState(fileGroupId, "N", FileState.SAVED);         // if has file-group-id, show together
            return new HongCartWithProductVO(hongCart, list);
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
    public List<HongCartWithProductVO> listOfChoose(List<Long> ids) {
        List<HongCartWithProductVO> hongCarts = new ArrayList<>();
        if(ids != null && ids.size() > 0 ) {
            for (Long id : ids) {
                HongCart hongCart = hongCartRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("there is no cart"));
                Long fileGroupId = hongCart.getHongProduct().getFileGroupId();
                HongFileGroupVO list = hongFileGroupService.listWithDeleteYnAndFileState(fileGroupId, "N", FileState.SAVED);         // if has file-group-id, show together
                hongCarts.add(new HongCartWithProductVO(hongCart, list));
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
        List<HongCart> hongCarts = hongCartRepository.findAllByHongUserIdAndHongProductIdAndDeleteYn(userId, productId, "N");
        return hongCarts.isEmpty();
    }
}
