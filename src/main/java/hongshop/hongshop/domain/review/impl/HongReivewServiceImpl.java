package hongshop.hongshop.domain.review.impl;

import hongshop.hongshop.domain.file.FileState;
import hongshop.hongshop.domain.file.HongFileService;
import hongshop.hongshop.domain.fileGroup.HongFileGroupService;
import hongshop.hongshop.domain.fileGroup.vo.HongFileGroupVO;
import hongshop.hongshop.domain.orderDetail.HongOrderDetail;
import hongshop.hongshop.domain.orderDetail.HongOrderDetailService;
import hongshop.hongshop.domain.orderDetail.vo.HongOrderDetailVO;
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
 *              (3) userOrderReviewIsEmpty : 사용자가 어떤 주문건에 대해 리뷰를 남겼는지 확인
 *                  -> 1개의 주문건 상세 주문상품들에 대해 리뷰가 달린 개수를 확인한다.
 *                  -> 이때 모든 상세 주문상품들에 대해 리뷰가 달렸다면 : FALSE -> 리뷰 작성폼이 안열린다.
 *                  -> 이때 모든 상세 주문상품들에 대해 리뷰가 달리지 않았다면 : TRUE -> 리뷰 작성폼이 열린다.
**/



@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HongReivewServiceImpl implements HongReviewService {

    private final HongReviewRepository hongReviewRepository;
    private final HongOrderDetailService hongOrderDetailService;
    private final HongFileGroupService hongFileGroupService;
    private final HongFileService hongFileService;

    @Override
    @Transactional(readOnly = false)
    public Long join(HongReviewDTO hongReviewDTO, HongUser hongUser) {
        HongOrderDetail hongOrderDetail = hongOrderDetailService.getHongOrderDetail(hongReviewDTO.getHongOrderDetailId());

        // 1. delete file from list : deleteFile
        if(hongReviewDTO.getDeleteFile().size() != 0){
            hongFileService.deleteFiles(hongReviewDTO.getDeleteFile());
        }

        HongReview hongReview = null;
        if(hongReviewDTO.getFileGroupId() == null) {
            hongReview = HongReview.hongReviewInsertBuilder()
                    .hongOrderDetail(hongOrderDetail)
                    .hongUser(hongUser)
                    .reviewContent(hongReviewDTO.getReviewContent())
                    .reviewStar(hongReviewDTO.getReviewStar())
                    .build();
        }else {
            hongFileService.updateFileState(hongReviewDTO.getFileGroupId());
            hongReview = HongReview.hongReviewInsertBuilder()
                    .hongOrderDetail(hongOrderDetail)
                    .hongUser(hongUser)
                    .reviewContent(hongReviewDTO.getReviewContent())
                    .reviewStar(hongReviewDTO.getReviewStar())
                    .fileGroupId(hongReviewDTO.getFileGroupId())
                    .build();
        }

        HongReview save = hongReviewRepository.save(hongReview);
        return save.getId();
    }

    @Override
    public List<HongReviewVO> userReview(HongUser hongUser) {
        List<HongReview> hongReviews = hongReviewRepository.findAllByHongUserIdAndDeleteYnIs(hongUser.getId(), "N");
        return hongReviews.stream().map(hongReview -> {
            Long orderDetailId = hongReview.getHongOrderDetail().getId();
            HongOrderDetailVO orderDetailVO = hongOrderDetailService.view(orderDetailId);
            if(hongReview.getFileGroupId() != null) {
                HongFileGroupVO fileGroupVO = hongFileGroupService.listwithDeleteYnAndFileState(hongReview.getFileGroupId(), "N", FileState.SAVED);     // file-group list
                return new HongReviewVO(hongReview, orderDetailVO, fileGroupVO);
            }else return new HongReviewVO(hongReview, orderDetailVO);
        }).toList();
    }

    @Override
    public boolean userOrderReviewIsEmpty(HongUser hongUser, Long orderId) {
        List<HongOrderDetailVO> orderDetailVOS = hongOrderDetailService.listOfDetailOrders(orderId);

        /* 1개의 주문건 상세 주문상품들에 대해 리뷰가 달린 개수를 확인한다.
        *  -> 이때 모든 상세 주문상품들에 대해 리뷰가 달렸다면 : FALSE -> 리뷰 작성폼이 안열린다.
        *  -> 이때 모든 상세 주문상품들에 대해 리뷰가 달리지 않았다면 : TRUE -> 리뷰 작성폼이 열린다.
        * */
        boolean reviewEmpty = true;
        Integer orderDetailsCnt = 0;
        for (HongOrderDetailVO orderDetailvo: orderDetailVOS) {
            HongReview hongReview = hongReviewRepository.findByHongUserIdAndHongOrderDetailIdAndDeleteYnIs(hongUser.getId(), orderDetailvo.getOrderDetailId(), "N");
            if(hongReview != null) orderDetailsCnt = orderDetailsCnt + 1;
        }

        if(orderDetailsCnt == orderDetailVOS.size()) reviewEmpty = false;

        return reviewEmpty;
    }
}
