package hongshop.hongshop.domain.cart;

import hongshop.hongshop.domain.cart.dto.HongCartDTO;
import hongshop.hongshop.domain.cart.vo.HongCartWithProductVO;
import hongshop.hongshop.global.auth.PrincipalDetails;
import hongshop.hongshop.global.response.ApiDocumentResponse;
import hongshop.hongshop.global.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
* @fileName HongCartRestController
* @author dahyeon
* @version 1.0.0
* @date 2023-07-18
* @summary
**/

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "hong cart rest controller", description = "사용자의 장바구니 Rest 컨트롤러")
public class HongCartRestController {

    private final HongCartService hongCartService;

    @GetMapping("/cart/{id}")
    @Operation(summary = "get login user's cart list", description = "로그인한 사용자의 장바구니 리스트 가져오기")
    @ApiDocumentResponse
    public Response listOfCartById(@PathVariable Long id){
        List<HongCartWithProductVO> usersListOfCartById = hongCartService.getUsersListOfCartById(id);
        return Response.ok(usersListOfCartById);
    }

    @PostMapping("/cart")
    @Operation(summary = "insert cart", description = "장바구니 저장")
    @ApiDocumentResponse
    public Response save(@RequestBody HongCartDTO hongCartDTO, @AuthenticationPrincipal PrincipalDetails principalDetails){
        Long saveId = hongCartService.save(hongCartDTO, principalDetails.getUser());
        return Response.ok(saveId);
    }

    @DeleteMapping("/cart/{id}")
    @Operation(summary = "delete cart one", description = "장바구니 단건 삭제")
    @ApiDocumentResponse
    public Response delete(@PathVariable Long id){
        hongCartService.delete(id);
        return Response.ok("삭제되었습니다.");
    }

    @DeleteMapping("/cart")
    @Operation(summary = "delete cart several", description = "장바구니 여러개 삭제")
    @ApiDocumentResponse
    public Response deleteSeveral(Long[] ids) {
        hongCartService.deleteSeveral(ids);
        return Response.ok("해당 장바구니들을 정상적으로 삭제하였습니다.");
    }

    @PutMapping("/cart/{id}")
    @Operation(summary = "update cart", description = "장바구니 정보 변경 - 개수 변경")
    @ApiDocumentResponse
    public Response update(@PathVariable Long id, @RequestBody HongCartDTO hongCartDTO) {
        hongCartService.updateCnt(id, hongCartDTO);
        return Response.ok("해당 장바구니 정보를 변경하였습니다.");
    }

    @GetMapping("/find-cart/{id}")
    @Operation(summary = "find if has it", description = "이미 사용자 장바구니에 있는 상품인지 확인")
    @ApiDocumentResponse
    public Response findCart(@PathVariable Long id, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        Long userId = principalDetails.getUser().getId();
        boolean ifEmpty = hongCartService.findIfEmpty(userId, id);
        return Response.ok(ifEmpty);
    }
}
