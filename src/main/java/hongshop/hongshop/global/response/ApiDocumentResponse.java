package hongshop.hongshop.global.response;


import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;

import java.lang.annotation.*;

/**
 * @fileName ApiDocumentResponse
 * @author dahyeon
 * @version 1.0.0
 * @date 2023-04-28
 * @summary    @ApiDocumentResponse : 중복되는 ApiResponse를 하나에 모아서 정리
 **/

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
@ApiResponses(value = {


        @ApiResponse(
                responseCode = "200",
                description = "조회 성공",
                content = @Content(schema = @Schema(implementation = Response.class))
        ),

        @ApiResponse(
                responseCode = "400",
                description = "잘못된 Method 요청",
                content = @Content(schema = @Schema(implementation = Response.class))
        ),

        @ApiResponse(
                responseCode = "403",
                description = "권한 없음",
                content = @Content(schema = @Schema(implementation = Response.class))
        ),

        @ApiResponse(
                responseCode = "404",
                description = "존재하지 않는 API",
                content = @Content(schema = @Schema(implementation = Response.class))
        ),

        @ApiResponse(
                responseCode = "500",
                description = "내부 서버 오류",
                content = @Content(schema = @Schema(implementation = Response.class))
        ),
})
public @interface ApiDocumentResponse {
}
