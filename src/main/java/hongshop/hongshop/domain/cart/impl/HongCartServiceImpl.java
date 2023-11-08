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
* @summary      (1) save : 장바구니 저장
 *              (2) getUsersListOfCartById : user의 id로 장바구니 리스트 가져오기
 *              (3) delete : 장바구니 삭제
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
    public Integer save(List<HongCartDTO> cartDTOList, HongUser hongUser) {

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
}
