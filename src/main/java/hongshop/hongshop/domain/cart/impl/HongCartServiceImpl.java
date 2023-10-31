package hongshop.hongshop.domain.cart.impl;

import hongshop.hongshop.domain.cart.*;
import hongshop.hongshop.domain.cart.dto.HongCartDTO;
import hongshop.hongshop.domain.cart.vo.HongCartVO;
import hongshop.hongshop.domain.product.HongProduct;
import hongshop.hongshop.domain.product.HongProductService;
import hongshop.hongshop.domain.user.HongUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* @fileName HongCartServiceImpl
* @author dahyeon
* @version 1.0.0
* @date 2023-07-18
* @summary
**/

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HongCartServiceImpl implements HongCartService {

    private final HongCartRepository hongCartRepository;
    private final HongProductService hongProductService;

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
    public List<HongCartVO> getUsersListOfCartByLoginUser(HongUser hongUser) {
        List<HongCart> listOfCart = hongCartRepository.findAllByHongUserId(hongUser.getId());
        return listOfCart.stream().map(HongCartVO::new).toList();
    }

    @Override
    public List<HongCartVO> getUsersListOfCartById(Long id) {
        List<HongCart> listOfCart = hongCartRepository.findAllByHongUserId(id);
        return listOfCart.stream().map(HongCartVO::new).toList();
    }

    @Override
    @Transactional(readOnly = false)
    public void delete(Long id) {
        HongCart hongCart = hongCartRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("there is no cart"));
        hongCartRepository.delete(hongCart);
    }

}
