package hongshop.hongshop.domain.order.impl;

import hongshop.hongshop.domain.base.Address;
import hongshop.hongshop.domain.cart.HongCartService;
import hongshop.hongshop.domain.couponHas.HongCouponHas;
import hongshop.hongshop.domain.couponHas.HongCouponHasService;
import hongshop.hongshop.domain.deliver.DeliverStatus;
import hongshop.hongshop.domain.deliver.HongDeliver;
import hongshop.hongshop.domain.deliver.HongDeliverService;
import hongshop.hongshop.domain.deliver.vo.HongDeliverVO;
import hongshop.hongshop.domain.order.HongOrder;
import hongshop.hongshop.domain.order.HongOrderRepository;
import hongshop.hongshop.domain.order.HongOrderService;
import hongshop.hongshop.domain.order.OrderStatus;
import hongshop.hongshop.domain.order.dto.HongOrderFromCartDTO;
import hongshop.hongshop.domain.order.dto.HongOrderFromCartDetailsDTO;
import hongshop.hongshop.domain.order.dto.HongOrderFromShopDTO;
import hongshop.hongshop.domain.order.dto.HongOrderStatusDTO;
import hongshop.hongshop.domain.order.vo.HongManagerOrderReviewVO;
import hongshop.hongshop.domain.order.vo.HongOrderDeliverVO;
import hongshop.hongshop.domain.order.vo.HongUserOrderReviewVO;
import hongshop.hongshop.domain.orderDetail.HongOrderDetailService;
import hongshop.hongshop.domain.orderDetail.vo.HongOrderDetailVO;
import hongshop.hongshop.domain.product.HongProduct;
import hongshop.hongshop.domain.product.HongProductService;
import hongshop.hongshop.domain.review.HongReview;
import hongshop.hongshop.domain.review.HongReviewRepository;
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
* @summary  (1) saveFromCart : 장바구니에서 정보 가져와 주문 (save와 로직은 비슷하지만 마지막에 해당 상품 정보를 장바구니에서 삭제함)
 *          (2) saveFromShop : 상품 리스트 화면에서 선택 후 바로 주문
 *          =>> 한개의 order에 대해 여러개의 order-detail을 갖는다.
 *              만약 여러개의 order-detail 중에 'out of stock'이 있다면 -> throw와 함께 주문을 삭제 시킨다.
 *              ** 해당 물품이 재고가 있다면, 주문 저장 후, 재고값도 차감시켜준다.

 *          (3) updateStatus : 주문 상태값 변경   ->  주문 상태에 따른 배송 상태도 변경
 *          (4) getOrderAndDeliverByUserId : 사용자 id를 통해 주문 정보 & 배송 정보 불러오기
 *          (5) listWithChkReview : 전체 주문 조회 with 주문 상세 with review write boolean
 *          (6) getOrderDetailReviews : 주문건 상세 주문건 상품들에 대한 정보와 그 주문상품의 리뷰가 달렸는지 여부 가져오기
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
    private final HongReviewRepository hongReviewRepository;
    private final HongCouponHasService hongCouponHasService;

    @Override
    @Transactional(readOnly = false)
    public Long saveFromCart(HongOrderFromCartDTO hongOrderFromCartDTO) {

        HongUser hongUser = hongUserService.getHongUser(hongOrderFromCartDTO.getUserId()).get();

        HongOrder saveOrder = null;
        Integer couponRate = 0;
        if(hongOrderFromCartDTO.getHongCouponHasId() != null) {                                                                 // ** 사용자가 쿠폰을 사용했다면 ...
            HongCouponHas hongCouponHas = hongCouponHasService.getHongCouponHas(hongOrderFromCartDTO.getHongCouponHasId());
            couponRate = hongCouponHasService.useCoupon(hongOrderFromCartDTO.getHongCouponHasId());
            saveOrder = HongOrder.hongOrderInsertBuilder()
                    .hongUser(hongUser)
                    .orderStatus(OrderStatus.CHARGED)
                    .orderDate(TimeUtil.nowDate())
                    .hongCouponHas(hongCouponHas)
                    .build();
        }else {
            saveOrder = HongOrder.hongOrderInsertBuilder()
                    .hongUser(hongUser)
                    .orderStatus(OrderStatus.CHARGED)
                    .orderDate(TimeUtil.nowDate())
                    .build();
        }

        // 1. 주문 정보 저장
        saveOrder = hongOrderRepository.save(saveOrder);


        for(HongOrderFromCartDetailsDTO detailsDTO : hongOrderFromCartDTO.getOrders()){
            // 2. 상품 정보 찾기
            HongProduct product = hongProductService.productInfo(detailsDTO.getHongProductId());

            // ** 만약 주문개수가 상품 재고량보다 많다면 throw
            if(product.getProductStock() < detailsDTO.getOrderCnt()) {
                hongOrderRepository.delete(saveOrder);
                throw new IllegalArgumentException("stock is smaller than order count");
            }

            // 3. 주문 상세 정보 저장
            // - order from cart, user can order several product at once
            Integer orderPrice = 0;
            if(couponRate != 0) {
                if (detailsDTO.getOrderCnt() * product.getProductPrice() > couponRate) {
                    orderPrice = detailsDTO.getOrderCnt() * product.getProductPrice() - couponRate;
                    couponRate = 0;
                } else if (detailsDTO.getOrderCnt() * product.getProductPrice() <= couponRate) {
                    orderPrice = 0;
                    couponRate = couponRate - (detailsDTO.getOrderCnt() * product.getProductPrice());
                }
            } else {
                orderPrice = detailsDTO.getOrderCnt() * product.getProductPrice();
            }

            hongOrderDetailService.saveOrderDetails(saveOrder, product, detailsDTO.getOrderCnt(), orderPrice);

            // 4. update product removing stock
            hongProductService.updateStockCnt(detailsDTO.getOrderCnt(), product);

            // 5. then you need to remove prdocut from cart
            hongCartService.delete(detailsDTO.getHongCartId());
        }

        // 6. finally save deliver
        Address address = new Address(hongOrderFromCartDTO.getCity(), hongOrderFromCartDTO.getStreet(), hongOrderFromCartDTO.getZipcode());
        hongDeliverService.join(address, saveOrder);

        return saveOrder.getId();
    }

    @Override
    @Transactional(readOnly = false)
    public Long saveFromShop(HongOrderFromShopDTO hongOrderFromShopDTO) {
        HongUser hongUser = hongUserService.getHongUser(hongOrderFromShopDTO.getUserId()).get();

        // ** if user use coupon..
        HongOrder saveOrder = null;
        Integer couponRate = 0;
        if(hongOrderFromShopDTO.getHongCouponHasId() != null) {
            HongCouponHas hongCouponHas = hongCouponHasService.getHongCouponHas(hongOrderFromShopDTO.getHongCouponHasId());
            couponRate = hongCouponHasService.useCoupon(hongOrderFromShopDTO.getHongCouponHasId());
            saveOrder = HongOrder.hongOrderInsertBuilder()
                    .hongUser(hongUser)
                    .orderStatus(OrderStatus.CHARGED)
                    .orderDate(TimeUtil.nowDate())
                    .hongCouponHas(hongCouponHas)
                    .build();
        }else {
            saveOrder = HongOrder.hongOrderInsertBuilder()
                    .hongUser(hongUser)
                    .orderStatus(OrderStatus.CHARGED)
                    .orderDate(TimeUtil.nowDate())
                    .build();
        }

        // 1. first you need to make order
        saveOrder = hongOrderRepository.save(saveOrder);


        // 2. find product by productId first
        HongProduct product = hongProductService.productInfo(hongOrderFromShopDTO.getHongProductId());

        // ** if orderCnt is larger then stock throw error
        if(product.getProductStock() < hongOrderFromShopDTO.getOrderCnt()) {
            hongOrderRepository.delete(saveOrder);
            throw new IllegalArgumentException("stock is smaller than order count");
        }

        // 3. then save order details
        // -> order from shop, user can only order 1 product at once
        Integer orderPrice = hongOrderFromShopDTO.getOrderCnt() * product.getProductPrice() - couponRate;
        hongOrderDetailService.saveOrderDetails(saveOrder, product, hongOrderFromShopDTO.getOrderCnt(), orderPrice);

        // 4. update product removing stock
        hongProductService.updateStockCnt(hongOrderFromShopDTO.getOrderCnt(), product);

        // 5. finally save deliver
        Address address = new Address(hongOrderFromShopDTO.getCity(), hongOrderFromShopDTO.getStreet(), hongOrderFromShopDTO.getZipcode());
        hongDeliverService.join(address, saveOrder);

        return saveOrder.getId();
    }

    @Override
    public List<HongManagerOrderReviewVO> listWithChkReview() {
        List<HongOrder> orders = hongOrderRepository.findAll();
        return orders.stream().map(order -> {
            List<HongOrderDetailVO> orderDetails = hongOrderDetailService.listOfDetailOrders(order.getId());
            Long userId = order.getHongUser().getId();
            /* 주문건 상세 주문 상품들에 대해 리뷰가 1건이라도 있으면 해당 주문건은 상태값 변경 못하도록..  */
            boolean reviewEmpty = true;
            for (HongOrderDetailVO orderDetailvo: orderDetails) {
                HongReview hongReview = hongReviewRepository.findByHongUserIdAndHongOrderDetailIdAndDeleteYnIs(userId, orderDetailvo.getOrderDetailId(), "N");
                if(hongReview != null) {
                    reviewEmpty = false;
                    break;
                }
            }
            return new HongManagerOrderReviewVO(order, orderDetails, reviewEmpty);
        }).toList();
    }

    @Override
    @Transactional(readOnly = false)
    public void updateStatus(Long id, HongOrderStatusDTO hongOrderStatusDTO) {
        HongOrder hongOrder = hongOrderRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("there is no order"));
        hongOrder.updateStatus(hongOrderStatusDTO.getOrderStatus());

        DeliverStatus deliverStatus = null;
        if(hongOrderStatusDTO.getOrderStatus().equals(OrderStatus.DELIVER_SUCCESS)) deliverStatus = DeliverStatus.DELIVERED;
        else if(hongOrderStatusDTO.getOrderStatus().equals(OrderStatus.DELIVER_ING)) deliverStatus = DeliverStatus.DELIVERING;
        else if(hongOrderStatusDTO.getOrderStatus().equals(OrderStatus.CHARGED)) deliverStatus = DeliverStatus.AWAIT;
        else if(hongOrderStatusDTO.getOrderStatus().equals(OrderStatus.CANCEL)) deliverStatus = DeliverStatus.CANCEL;


        HongDeliver hongDeliver = hongDeliverService.getHongDeliverByOrderId(id);
        hongDeliver.updateStatus(deliverStatus);
    }

    @Override
    public List<HongOrderDeliverVO> getOrderAndDeliverByUserId(Long id) {
        List<HongOrder> hongOrders = hongOrderRepository.findAllByHongUserId(id);
        return hongOrders.stream().map(order -> {
            HongDeliverVO byOrderId = hongDeliverService.getByOrderId(order.getId());
            return new HongOrderDeliverVO(order, byOrderId);
        }).toList();
    }

    @Override
    public List<HongUserOrderReviewVO> getOrderDetailReviews(Long orderId, HongUser hongUser) {
        List<HongOrderDetailVO> orderDetailVOS = hongOrderDetailService.listOfDetailOrders(orderId);
        return orderDetailVOS.stream().map(orderDevailVO -> {
            boolean isEmpty = true;
            HongReview hongReview = hongReviewRepository.findByHongUserIdAndHongOrderDetailIdAndDeleteYnIs(hongUser.getId(), orderDevailVO.getOrderDetailId(), "N");
            if(hongReview != null) isEmpty = false;
            return new HongUserOrderReviewVO(orderDevailVO.getProductName(), orderDevailVO.getOrderDetailId(), isEmpty);
        }).toList();
    }
}
