package hongshop.hongshop.global.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

/**
 * @fileName GlobalExceptionHandler
 * @author dahyeon
 * @version 1.0.0
 * @date 2023-07-17
 * @summary     (1) MethodArgumentNotValidException : DTO에 붙은 validation handler
 *              (2) IllegalArgumentException : 해당 exception 발생 시, BAD_REQUEST Response
 **/

@Slf4j
@RestControllerAdvice   // ControllerAdvice + ResponseBody 
                        // ControllerAdvice : Controller에 대한 전역적으로 예외를 잡아서 처리 가능
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected Response handleValidationException(MethodArgumentNotValidException exception){
        List<Error> errors = returnException(exception.getBindingResult());
        return Response.badRequest(errors);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    protected Response handleIllegalArgumentException(IllegalArgumentException exception){
        return Response.badRequest(exception.getMessage());
    }

    protected List<Error> returnException(BindingResult bindingResult){
        List<Error> errors = new ArrayList<>();
        if(bindingResult.hasErrors()){
            for(FieldError fieldError : bindingResult.getFieldErrors()){
                errors.add(new Error(fieldError.getObjectName(), fieldError.getDefaultMessage(), fieldError.getField()));
            }
        }
        return errors;
    }

    @Getter @Setter
    @AllArgsConstructor
    public static class Error {
        private String objectName;
        private String message;
        private String field;
    }
}
