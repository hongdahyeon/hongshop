package hongshop.hongshop.domain.orderDetail.impl;

import hongshop.hongshop.domain.file.FileState;
import hongshop.hongshop.domain.fileGroup.HongFileGroupService;
import hongshop.hongshop.domain.fileGroup.vo.HongFileGroupVO;
import hongshop.hongshop.domain.order.HongOrder;
import hongshop.hongshop.domain.order.HongOrderRepository;
import hongshop.hongshop.domain.order.OrderStatus;
import hongshop.hongshop.domain.orderDetail.*;
import hongshop.hongshop.domain.orderDetail.vo.HongOrderDetailFileVO;
import hongshop.hongshop.domain.orderDetail.vo.HongOrderDetailUserVO;
import hongshop.hongshop.domain.orderDetail.vo.HongOrderDetailVO;
import hongshop.hongshop.domain.product.HongProduct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
* @fileName HongOrderDetailServiceImpl
* @author dahyeon
* @version 1.0.0
* @date 2023-07-18
* @summary  (1) saveOrderDetails : order table이 save됨에 따라 detail 정보가 저장된다.
 *          (2) listOfDetailOrders : 주문Id를 통한 주문 상세 정보 리스트를 가져온다.
 *          (3) listByProductId : 상품Id를 통해 주문 사용자 정보 리스트를 가져온다.
 *          (4) emptyChkByProductId : 상품Id를 통해 주문 사용자가 있는지를 체크한다.
 *              -> 주문 상태 : CHARGED, DELIVER_ING
 *              -> 해당 주문 상태인 주문건이 있다면 상품 삭제를 못하도록 처리
 *              -> 해당 주문 상태인 주문건이 있다면 empty is false -> if(!empty) delete button is disabeld
 *          (5) getHongOrderDetail : return HongOrderDetail entity
 *          (6) view : return HongOrderDetailVO
**/

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HongOrderDetailServiceImpl implements HongOrderDetailService {

    private final HongOrderDetailRepository hongOrderDetailRepository;
    private final HongOrderRepository hongOrderRepository;
    private final HongFileGroupService hongFileGroupService;

    @Override
    @Transactional(readOnly = false)
    public Long saveOrderDetails(HongOrder hongOrder, HongProduct hongProduct, Integer orderCnt, Integer orderPrice) {
        HongOrderDetail hongOrderDetail = HongOrderDetail.hongOrderDetailInsertBuilder()
                .hongOrder(hongOrder)
                .hongProduct(hongProduct)
                .orderCnt(orderCnt)
                .orderPrice(orderPrice)
                .build();
        HongOrderDetail save = hongOrderDetailRepository.save(hongOrderDetail);
        return save.getId();
    }

    @Override
    public List<HongOrderDetailVO> listOfDetailOrders(Long orderId) {
        List<HongOrderDetail> listofOrderDetail = hongOrderDetailRepository.findAllByHongOrderId(orderId);
        return listofOrderDetail.stream().map(HongOrderDetailVO::new).toList();
    }

    @Override
    public List<HongOrderDetailUserVO> listByProductId(Long productId) {
        List<HongOrderDetail> orderDetails = hongOrderDetailRepository.findAllByHongProduct_Id(productId);
        return orderDetails.stream().map(details -> {
            HongOrder view = hongOrderRepository.findById(details.getHongOrder().getId()).orElseThrow(() -> new IllegalArgumentException("there is no order"));
            return new HongOrderDetailUserVO(view, details);
        }).toList();
    }

    @Override
    public boolean emptyChkByProductId(Long productId) {
        List<OrderStatus> orderStatusList = new ArrayList<>();
        orderStatusList.add(OrderStatus.CHARGED); orderStatusList.add(OrderStatus.DELIVER_ING);
        List<HongOrderDetail> findOrderDetails = hongOrderDetailRepository.findAllByHongProduct_IdAndHongOrder_OrderStatusIn(productId, orderStatusList);
        return findOrderDetails.isEmpty();
    }

    @Override
    public HongOrderDetail getHongOrderDetail(Long hongOrderDetailId) {
        return hongOrderDetailRepository.findById(hongOrderDetailId).orElseThrow(() -> new IllegalArgumentException("there is no order detail"));
    }

    @Override
    public HongOrderDetailFileVO view(Long hongOrderDetailId) {
        HongOrderDetail hongOrderDetail = hongOrderDetailRepository.findById(hongOrderDetailId).orElseThrow(() -> new IllegalArgumentException("there is no order detail"));
        Long fileGroupId = hongOrderDetail.getHongProduct().getFileGroupId();
        if(fileGroupId != null) {
            HongFileGroupVO fileGroupVO = hongFileGroupService.listWithDeleteYnAndFileState(fileGroupId, "N", FileState.SAVED);
            return new HongOrderDetailFileVO(hongOrderDetail, fileGroupVO);
        }else return new HongOrderDetailFileVO(hongOrderDetail);
    }
}
