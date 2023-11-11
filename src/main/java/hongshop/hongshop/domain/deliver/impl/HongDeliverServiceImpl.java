package hongshop.hongshop.domain.deliver.impl;

import hongshop.hongshop.domain.base.Address;
import hongshop.hongshop.domain.deliver.DeliverStatus;
import hongshop.hongshop.domain.deliver.HongDeliver;
import hongshop.hongshop.domain.deliver.HongDeliverRepository;
import hongshop.hongshop.domain.deliver.HongDeliverService;
import hongshop.hongshop.domain.deliver.dto.HongDeliverAddressDTO;
import hongshop.hongshop.domain.deliver.dto.HongDeliverDTO;
import hongshop.hongshop.domain.deliver.dto.HongDeliverStatusDTO;
import hongshop.hongshop.domain.deliver.vo.HongDeliverVO;
import hongshop.hongshop.domain.order.HongOrder;
import hongshop.hongshop.domain.order.HongOrderRepository;
import hongshop.hongshop.domain.order.OrderStatus;
import hongshop.hongshop.domain.orderDetail.HongOrderDetailService;
import hongshop.hongshop.domain.orderDetail.vo.HongOrderDetailVO;
import hongshop.hongshop.domain.review.HongReview;
import hongshop.hongshop.domain.review.HongReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* @fileName HongDeliverServiceImpl
* @author dahyeon
* @version 1.0.0
* @date 2023-07-19
* @summary  (1) join : 배송 정보 저장
 *          (2) view : 배송 정보 단건 조회
 *          (3) update : 배송 정보 수정
 *          (4) updateStatus : 배송 상태 정보 수정   ->  배송 상태에 따른 주문 상태도 변경
 *          (5) all : 배송 정보 전체 조회
 *          (6) getByOrderId : 주문 Id로 배송 정보 조회 -> return vo
 *          (7) getHongDeliverByOrderId : 주문 Id로 배송 정보 조회 -> return entity
 *          (8) updateAddres : 배송 주소 정보 수정
 *          (9) allWithChkReview : 배송 정보 전체 조회 with review write boolean
**/

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HongDeliverServiceImpl implements HongDeliverService {

    private final HongDeliverRepository hongDeliverRepository;
    private final HongOrderRepository hongOrderRepository;
    private final HongReviewRepository hongReviewRepository;
    private final HongOrderDetailService hongOrderDetailService;

    @Override
    @Transactional(readOnly = false)
    public Long join(Address address, HongOrder hongOrder) {
        HongDeliver hongDeliver = HongDeliver.hongDeliverInsertBuilder()
                .hongOrder(hongOrder)
                .deliverStatus(DeliverStatus.AWAIT)
                .address(address)
                .build();

        HongDeliver save = hongDeliverRepository.save(hongDeliver);
        return save.getId();
    }

    @Override
    public HongDeliverVO view(Long id) {
        HongDeliver hongDeliver = hongDeliverRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("there is no deliver"));
        return new HongDeliverVO(hongDeliver);
    }

    @Override
    @Transactional(readOnly = false)
    public void update(HongDeliverDTO hongDeliverDTO, Long id) {
        HongDeliver hongDeliver = hongDeliverRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("there is no deliver"));
        hongDeliver.updateDeliver(hongDeliverDTO);
    }

    @Override
    @Transactional(readOnly = false)
    public void updateStatus(HongDeliverStatusDTO hongDeliverStatusDTO, Long id) {
        HongDeliver hongDeliver = hongDeliverRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("there is no deliver"));
        hongDeliver.updateStatus(hongDeliverStatusDTO.getStatus());

        OrderStatus orderStatus = null;
        if(hongDeliverStatusDTO.getStatus().equals(DeliverStatus.DELIVERED)) orderStatus = OrderStatus.DELIVER_SUCCESS;
        else if(hongDeliverStatusDTO.getStatus().equals(DeliverStatus.DELIVERING)) orderStatus = OrderStatus.DELIVER_ING;
        else if(hongDeliverStatusDTO.getStatus().equals(DeliverStatus.AWAIT)) orderStatus = OrderStatus.CHARGED;
        else if(hongDeliverStatusDTO.getStatus().equals(DeliverStatus.CANCEL)) orderStatus = OrderStatus.CANCEL;

        Long orderId = hongDeliver.getHongOrder().getId();
        HongOrder hongOrder = hongOrderRepository.findById(orderId).orElseThrow(() -> new IllegalArgumentException("there is no order"));
        hongOrder.updateStatus(orderStatus);
    }

    @Override
    public List<HongDeliverVO> all() {
        List<HongDeliver> all = hongDeliverRepository.findAll();
        return all.stream().map(HongDeliverVO::new).toList();
    }

    @Override
    public List<HongDeliverVO> allWithChkReview() {
        List<HongDeliver> all = hongDeliverRepository.findAll();
        return all.stream().map(hongDeliver -> {
            Long orderId = hongDeliver.getHongOrder().getId();
            Long userId = hongDeliver.getHongOrder().getHongUser().getId();

            /* 주문건 상세 주문 상품들에 대해 리뷰가 1건이라도 있으면 해당 주문건은 상태값 변경 못하도록..  */
            List<HongOrderDetailVO> orderDetailVOS = hongOrderDetailService.listOfDetailOrders(orderId);
            boolean reviewEmpty = true;
            for (HongOrderDetailVO orderDetailvo: orderDetailVOS) {
                HongReview hongReview = hongReviewRepository.findByHongUserIdAndHongOrderDetailIdAndDeleteYnIs(userId, orderDetailvo.getOrderDetailId(), "N");
                if(hongReview != null) {
                    reviewEmpty = false;
                    break;
                }
            }
            return new HongDeliverVO(hongDeliver, reviewEmpty);
        }).toList();
    }

    @Override
    public HongDeliverVO getByOrderId(Long orderId) {
        HongDeliver hongDeliver = hongDeliverRepository.findByHongOrder_Id(orderId);
        return new HongDeliverVO(hongDeliver);
    }

    @Override
    public HongDeliver getHongDeliverByOrderId(Long orderId) {
        return hongDeliverRepository.findByHongOrder_Id(orderId);
    }

    @Override
    @Transactional(readOnly = false)
    public void updateAddres(Long id, HongDeliverAddressDTO hongDeliverAddressDTO) {
        HongDeliver hongDeliver = hongDeliverRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("there is no deliver"));
        if(!hongDeliver.getDeliverStatus().equals(DeliverStatus.AWAIT)) throw new IllegalArgumentException("cannot change address");            // 배송주소의 경우 배송상태가 AWAIT일 경우에만 가능
        Address address = new Address(hongDeliverAddressDTO.getCity(), hongDeliverAddressDTO.getStreet(), hongDeliverAddressDTO.getZipcode());
        hongDeliver.updateAddress(address);
    }
}
