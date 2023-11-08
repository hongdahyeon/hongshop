package hongshop.hongshop.domain.order.impl;

import hongshop.hongshop.domain.base.Address;
import hongshop.hongshop.domain.cart.HongCartService;
import hongshop.hongshop.domain.deliver.HongDeliverService;
import hongshop.hongshop.domain.order.HongOrder;
import hongshop.hongshop.domain.order.HongOrderRepository;
import hongshop.hongshop.domain.order.HongOrderService;
import hongshop.hongshop.domain.order.OrderStatus;
import hongshop.hongshop.domain.order.dto.HongOrderDTO;
import hongshop.hongshop.domain.order.dto.HongOrderFromCartDTO;
import hongshop.hongshop.domain.order.dto.HongOrderFromCartDetailsDTO;
import hongshop.hongshop.domain.order.dto.HongOrderStatusDTO;
import hongshop.hongshop.domain.order.vo.HongOrderVO;
import hongshop.hongshop.domain.orderDetail.HongOrderDetailService;
import hongshop.hongshop.domain.orderDetail.vo.HongOrderDetailVO;
import hongshop.hongshop.domain.product.HongProduct;
import hongshop.hongshop.domain.product.HongProductService;
import hongshop.hongshop.domain.user.HongUser;
import hongshop.hongshop.domain.user.HongUserService;
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
 *          (2) saveFromCart : 장바구니에서 정보 가져와 주문 (save와 로직은 비슷하지만 마지막에 해당 상품 정보를 장바구니에서 삭제함)
 *          (2) view
 *               order-id에 대해 order-detail-list 값을 함께 불러온다.
 *          (3) listOfUserOrder
 *               현재 로그인한 user의 주문 정보를 불러온다.
 *          (4) updateStatus : 주문 상태값 변경
 *          (5) list: 전체 주문 조회 with 주문 상세
**/

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HongOrderServiceImpl implements HongOrderService {

    private final HongOrderRepository hongOrderRepository;
    private final HongOrderDetailService hongOrderDetailService;
    private final HongProductService hongProductService;
    private final HongDeliverService hongDeliverService;
    private final HongCartService hongCartService;
    private final HongUserService hongUserService;

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

        // 5. finally save deliver
        hongDeliverService.join(hongUser.getAddress(), saveOrder);

        return saveOrder.getId();
    }

    @Override
    @Transactional(readOnly = false)
    public Long saveFromCart(HongOrderFromCartDTO hongOrderFromCartDTO) {

        HongUser hongUser = hongUserService.getHongUser(hongOrderFromCartDTO.getUserId()).get();

        // 1. first you need to make order
        HongOrder saveOrder = HongOrder.hongOrderInsertBuilder()
                .hongUser(hongUser)
                .orderStatus(OrderStatus.CHARGED)
                .orderDate(TimeUtil.nowDate())
                .build();
        saveOrder = hongOrderRepository.save(saveOrder);

        for(HongOrderFromCartDetailsDTO detailsDTO : hongOrderFromCartDTO.getOrders()){
            // 1. find product by productId first
            HongProduct product = hongProductService.productInfo(detailsDTO.getHongProductId());

            // ** if orderCnt is larger then stock throw error
            if(product.getProductStock() < detailsDTO.getOrderCnt()) {
                hongOrderRepository.delete(saveOrder);
                throw new IllegalArgumentException("stock is smaller than order count");
            }

            // 3. then save order details
            Integer orderPrice = detailsDTO.getOrderCnt() * product.getProductPrice();
            hongOrderDetailService.saveOrderDetails(saveOrder, product, detailsDTO.getOrderCnt(), orderPrice);

            // 4. update product removing stock
            hongProductService.updateStockCnt(detailsDTO.getOrderCnt(), product);

            // 5. then you need to remove prdocut from cart
            Long hongCartId = detailsDTO.getHongCartId();
            hongCartService.delete(hongCartId);
        }

        // 6. finally save deliver
        Address address = new Address(hongOrderFromCartDTO.getCity(), hongOrderFromCartDTO.getStreet(), hongOrderFromCartDTO.getZipcode());
        hongDeliverService.join(address, saveOrder);

        return saveOrder.getId();
    }

    @Override
    public List<HongOrderVO> list() {
        List<HongOrder> orders = hongOrderRepository.findAll();
        return orders.stream().map(order -> {
            List<HongOrderDetailVO> orderDetails = hongOrderDetailService.listOfDetailOrders(order.getId());
            return new HongOrderVO(order, orderDetails);
        }).toList();
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

    @Override
    @Transactional(readOnly = false)
    public void updateStatus(Long id, HongOrderStatusDTO hongOrderStatusDTO) {
        HongOrder hongOrder = hongOrderRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("there is no order"));
        hongOrder.updateStatus(hongOrderStatusDTO.getOrderStatus());
    }
}
