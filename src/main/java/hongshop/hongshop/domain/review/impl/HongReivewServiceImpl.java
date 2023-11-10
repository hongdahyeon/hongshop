package hongshop.hongshop.domain.review.impl;

import hongshop.hongshop.domain.file.FileState;
import hongshop.hongshop.domain.fileGroup.HongFileGroupService;
import hongshop.hongshop.domain.fileGroup.vo.HongFileGroupVO;
import hongshop.hongshop.domain.order.HongOrder;
import hongshop.hongshop.domain.order.HongOrderService;
import hongshop.hongshop.domain.order.vo.HongOrderVO;
import hongshop.hongshop.domain.orderDetail.HongOrderDetailService;
import hongshop.hongshop.domain.orderDetail.vo.HongOrderDetailVO;
import hongshop.hongshop.domain.product.HongProductService;
import hongshop.hongshop.domain.review.HongReview;
import hongshop.hongshop.domain.review.HongReviewRepository;
import hongshop.hongshop.domain.review.HongReviewService;
import hongshop.hongshop.domain.review.dto.HongReviewDTO;
import hongshop.hongshop.domain.review.vo.HongReviewVO;
import hongshop.hongshop.domain.user.HongUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
* @fileName HongReivewServiceImpl
* @author dahyeon
* @version 1.0.0
* @date 2023-11-10
* @summary      (1) join : 리뷰 작성하기
 *              (2) userReview : 현재 로그인한 사용자의 리뷰 리스트 조회
**/



@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HongReivewServiceImpl implements HongReviewService {

    private final HongReviewRepository hongReviewRepository;
    private final HongOrderService hongOrderService;
    private final HongOrderDetailService hongOrderDetailService;
    private final HongFileGroupService hongFileGroupService;

    @Override
    @Transactional(readOnly = false)
    public Long join(HongReviewDTO hongReviewDTO, HongUser hongUser) {
        HongOrder hongOrder = hongOrderService.getHongOrder(hongReviewDTO.getHongOrderId());

        HongReview hongReview = HongReview.hongReviewInsertBuilder()
                .hongOrder(hongOrder)
                .hongUser(hongUser)
                .reviewContent(hongReviewDTO.getReviewContent())
                .reviewStar(hongReviewDTO.getReviewStar())
                .fileGroupId(hongReviewDTO.getFileGroupId())
                .build();

        HongReview save = hongReviewRepository.save(hongReview);
        return save.getId();
    }

    @Override
    public List<HongReviewVO> userReview(HongUser hongUser) {
        List<HongReview> hongReviews = hongReviewRepository.findAllByHongUserId(hongUser.getId());
        return hongReviews.stream().map(hongReview -> {
            Long orderId = hongReview.getHongOrder().getId();
            List<HongOrderDetailVO> orderDetails = hongOrderDetailService.listOfDetailOrders(orderId);                                              // order-details
            HongFileGroupVO fileGroupVO = hongFileGroupService.listwithDeleteYnAndFileState(hongReview.getFileGroupId(), "N", FileState.SAVED);     // file-group list
            return new HongReviewVO(hongReview, orderDetails, fileGroupVO);
        }).toList();
    }
}
