package hongshop.hongshop.domain.cart;

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

    @GetMapping("/cart")
    @Operation(summary = "get login user's cart list", description = "로그인한 사용자의 장바구니 리스트 가져오기")
    @ApiDocumentResponse
    public Response listOfCartByLoginUser(@AuthenticationPrincipal PrincipalDetails principalDetails){
        List<HongCartVO> usersListOfCart = hongCartService.getUsersListOfCartByLoginUser(principalDetails.getUser());
        return Response.ok(usersListOfCart);
    }

    @GetMapping("/cart/{id}")
    @Operation(summary = "get login user's cart list", description = "로그인한 사용자의 장바구니 리스트 가져오기")
    @ApiDocumentResponse
    public Response listOfCartById(@PathVariable Long id){
        List<HongCartVO> usersListOfCartById = hongCartService.getUsersListOfCartById(id);
        return Response.ok(usersListOfCartById);
    }

    @PostMapping("/cart")
    @Operation(summary = "insert cart", description = "장바구니 저장")
    @ApiDocumentResponse
    public Response save(@RequestBody List<HongCartDTO> hongCartDTO, @AuthenticationPrincipal PrincipalDetails principalDetails){
        Integer savedCart = hongCartService.save(hongCartDTO, principalDetails.getUser());
        return Response.ok(savedCart);
    }

    @DeleteMapping("/cart/{id}")
    @Operation(summary = "delete cart", description = "장바구니 삭제")
    @ApiDocumentResponse
    public Response delete(@PathVariable Long id){
        hongCartService.delete(id);
        return Response.ok("삭제되었습니다.");
    }


}
