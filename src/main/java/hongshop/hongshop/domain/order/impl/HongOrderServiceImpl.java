package hongshop.hongshop.domain.order.impl;

import hongshop.hongshop.domain.order.*;
import hongshop.hongshop.domain.orderDetail.HongOrderDetailService;
import hongshop.hongshop.domain.orderDetail.HongOrderDetailVO;
import hongshop.hongshop.domain.product.HongProduct;
import hongshop.hongshop.domain.product.HongProductService;
import hongshop.hongshop.domain.user.HongUser;
import hongshop.hongshop.global.util.TimeUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* @fileName HongOrderServiceImpl
* @author dahyeon
* @version 1.0.0
* @date 2023-07-18
* @summary  (1) save
 *               한개의 order에 대해 여러개의 order-detail을 갖는다.
 *               만약 여러개의 order-detail 중에 'out of stock'이 있다면 -> throw와 함께 주문을 삭제 시킨다.
 *               ** 해당 물품이 재고가 있다면, 주문 저장 후, 재고값도 차감시켜준다.
 *          (2) view
 *               order-id에 대해 order-detail-list 값을 함께 불러온다.
 *          (3) listOfUserOrder
 *               현재 로그인한 user의 주문 정보를 불러온다.
**/

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HongOrderServiceImpl implements HongOrderService {

    private final HongOrderRepository hongOrderRepository;
    private final HongOrderDetailService hongOrderDetailService;
    private final HongProductService hongProductService;

    @Override
    @Transactional(readOnly = false)
    public Long save(List<HongOrderDTO> listofOrders, HongUser hongUser) {

        // 1. first you need to make order
        HongOrder saveOrder = HongOrder.hongOrderInsertBuilder()
                .hongUser(hongUser)
                .orderStatus(OrderStatus.CHARGED)
                .orderDate(TimeUtil.nowDate())
                .build();
        saveOrder = hongOrderRepository.save(saveOrder);

        for(HongOrderDTO hongOrderDTO : listofOrders){
            // 1. find product by productId first
            HongProduct product = hongProductService.productInfo(hongOrderDTO.getHongProductId());

            // ** if orderCnt is larger then stock throw error
            if(product.getProductStock() < hongOrderDTO.getOrderCnt()) {
                hongOrderRepository.delete(saveOrder);
                throw new IllegalArgumentException("stock is smaller than order count");
            }

            // 3. then save order details
            Integer orderPrice = hongOrderDTO.getOrderCnt() * product.getProductPrice();
            hongOrderDetailService.saveOrderDetails(saveOrder, product, hongOrderDTO.getOrderCnt(), orderPrice);

            // 4. update product removing stock
            hongProductService.updateStockCnt(hongOrderDTO.getOrderCnt(), product);

        }

        return saveOrder.getId();
    }

    @Override
    public HongOrderVO view(Long id) {
        HongOrder hongOrder = hongOrderRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("there is no order"));
        List<HongOrderDetailVO> listofDetails = hongOrderDetailService.listOfDetailOrders(hongOrder.getId());
        return new HongOrderVO(hongOrder, listofDetails);
    }

    @Override
    public List<HongOrderVO> listOfUserOrder(Long id) {
        List<HongOrder> listOfOrder = hongOrderRepository.findAllByHongUserId(id);
        return listOfOrder.stream().map(order -> {
            List<HongOrderDetailVO> listofDetails = hongOrderDetailService.listOfDetailOrders(order.getId());
            return new HongOrderVO(order, listofDetails);
        }).toList();
    }
}
