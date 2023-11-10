package hongshop.hongshop.domain.review;


import hongshop.hongshop.domain.review.dto.HongReviewDTO;
import hongshop.hongshop.domain.review.vo.HongReviewVO;
import hongshop.hongshop.domain.user.HongUser;
import hongshop.hongshop.global.auth.PrincipalDetails;
import hongshop.hongshop.global.response.ApiDocumentResponse;
import hongshop.hongshop.global.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "hong review rest controller", description = "주문에 대한 리뷰 Rest 컨트롤러")
public class HongReviewRestController {

    private final HongReviewService hongReviewService;
    @PostMapping("/review")
    @Operation(summary = "insert review", description = "주문에 대한 리뷰 작성하기")
    @ApiDocumentResponse
    public Response join(@RequestBody HongReviewDTO hongReviewDTO, @AuthenticationPrincipal PrincipalDetails principalDetails){
        HongUser user = principalDetails.getUser();
        Long joinId = hongReviewService.join(hongReviewDTO, user);
        return Response.ok(joinId);
    }

    @GetMapping("/user-review")
    @Operation(summary = "현재 로그인한 자기 자신의 리뷰 리스트 보기", description = "현재 로그인한 자기 자신의 리뷰 리스트 보기")
    @ApiDocumentResponse
    public Response userReview(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        HongUser user = principalDetails.getUser();
        List<HongReviewVO> hongReviewVOS = hongReviewService.userReview(user);
        return Response.ok(hongReviewVOS);
    }

}
