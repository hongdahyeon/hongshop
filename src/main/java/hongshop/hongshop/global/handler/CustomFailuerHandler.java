package hongshop.hongshop.global.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

@Component
@Slf4j
public class CustomFailuerHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        log.info(" CustomFailuerHandler :: onAuthenticationFailure ");
        String simpleName = exception.getClass().getSimpleName();
        String userId = request.getParameter("userId");
        log.info("simpleName : {} ", simpleName);

        // 아이디 오류, 비번 오류를 분리하면 보안성면에서 좋지 않음
        if(exception instanceof BadCredentialsException) {
            String message = URLEncoder.encode("아이디 또는 비밀번호가 맞지 않습니다.", "UTF-8");
            response.sendRedirect("/login?error="+message);
        }else if(exception instanceof InternalAuthenticationServiceException) {
            String message = URLEncoder.encode("내부 시스템 문제로 로그인 요청을 처리할 수 없습니다. \n 관리자에게 문의해주세요.", "UTF-8");
            response.sendRedirect("/login?error="+message);
        }
    }
}
