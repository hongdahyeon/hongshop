package hongshop.hongshop.domain.orderDetail.impl;

import hongshop.hongshop.domain.order.HongOrder;
import hongshop.hongshop.domain.orderDetail.HongOrderDetail;
import hongshop.hongshop.domain.orderDetail.HongOrderDetailRepository;
import hongshop.hongshop.domain.orderDetail.HongOrderDetailService;
import hongshop.hongshop.domain.orderDetail.HongOrderDetailVO;
import hongshop.hongshop.domain.product.HongProduct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* @fileName HongOrderDetailServiceImpl
* @author dahyeon
* @version 1.0.0
* @date 2023-07-18
* @summary  (1) saveOrderDetails : order table이 save됨에 따라 detail 정보가 저장된다.
 *          (2) listOfDetailOrders : order table의 상세 데이터들이 나온다.
**/

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HongOrderDetailServiceImpl implements HongOrderDetailService {

    private final HongOrderDetailRepository hongOrderDetailRepository;

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
}
