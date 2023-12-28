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

    @GetMapping("/user-order-review/{id}")
    @Operation(summary = "현재 로그인한 자기 자신의 특정 주문 리뷰 있는지 확인", description = "현재 로그인한 자기 자신의 특정 주문 리뷰 있는지 확인")
    @ApiDocumentResponse
    public Response userOrderReview(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable Long id) {
        HongUser user = principalDetails.getUser();
        boolean isEmpty = hongReviewService.userOrderReviewIsEmpty(user, id);
        return Response.ok(isEmpty);
    }

    @DeleteMapping("/review/{id}")
    @Operation(summary = "리뷰 삭제", description = "리뷰 삭제")
    @ApiDocumentResponse
    public Response delete(@PathVariable Long id) {
        hongReviewService.delete(id);
        return Response.ok("해당 리뷰를 삭제했습니다.");
    }

    @GetMapping("/review/{id}")
    @Operation(summary = "단건 리뷰 조회", description = "단건 리뷰 조회")
    @ApiDocumentResponse
    public Response view(@PathVariable Long id) {
        HongReviewVO view = hongReviewService.view(id);
        return Response.ok(view);
    }

    @PutMapping("/review/{id}")
    @Operation(summary = "단건 리뷰 수정", description = "단건 리뷰 수정")
    @ApiDocumentResponse
    public Response update(@PathVariable Long id, @RequestBody HongReviewDTO hongReviewDTO) {
        hongReviewService.update(id, hongReviewDTO);
        return Response.ok("해당 리뷰가 수정되었습니다.");
    }

}
