package hongshop.hongshop.global.handler;

import hongshop.hongshop.domain.user.HongUser;
import hongshop.hongshop.domain.user.HongUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Optional;

/**
* @fileName CustomFailuerHandler
* @author dahyeon
* @version 1.0.0
* @date 2023-11-03
* @summary   AuthenticationFailureHandler : 인증 실패를 처리하는 곳
 *                  - 인증시도에 대한 실패의 경우 호출된다.
 *                  - 인증 실패 시, 사용자를 오류 페이지로 리디렉션하거나 실패 시도를 기록하는 등의 작업을 구현 가능하다.
 *
 *            ** BadCredentialsException : 아이디 혹은 비번이 틀린 경우 타게 됨
 *              -> UsernameNotFoundException : 사용자를 찾을 수 없음
 *            ** InternalAuthenticationServiceException : 내부 시스템 오류
 *            ** LockedException : 계정이 잠긴 경우
**/

@Component
@Slf4j
@RequiredArgsConstructor
public class CustomFailureHandler implements AuthenticationFailureHandler {
    
    private final HongUserRepository hongUserRepository;
    @Override
    @Transactional(readOnly = false)
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String userId = request.getParameter("userId");
        log.info("exception : {} ", exception.getClass().getName());

        if(exception instanceof BadCredentialsException) {
            Optional<HongUser> userFind = hongUserRepository.findByUserId(userId);
            if(userFind.isEmpty()) sendMssgAndRedirect("등록되지 않은 사용자입니다. \n 회원가입을 먼저 해주시기 바랍니다.", response);
            else {
                HongUser user = userFind.get();
                Integer failCnt = user.getPwdFailCnt() + 1;

                if(failCnt == 5) {
                    user.updatePwdFailCntAndUserNonLocked();
                    sendMssgAndRedirect("비밀번호 " + failCnt + " / 5 회 오류로 계정이 잠겼습니다. \n 관리자에게 문의 바랍니다.", response);
                }else {
                    user.updatePwdFailCnt();
                    sendMssgAndRedirect("비밀번호 " + failCnt + "/5 회 오류", response);
                }
            }
        } else if(exception instanceof DisabledException) {

            sendMssgEnableAndRedirect("비밀번호가 비활성화 되었습니다. \n 관리자에게 문의 바랍니다.", userId, response);

        } else if(exception instanceof CredentialsExpiredException) {

            sendmssgExpiredAndRedirect("비밀번호가 만료되었습니다. \n 비밀번호를 변경해주세요.", userId, response);

        } else if(exception instanceof InternalAuthenticationServiceException) sendMssgAndRedirect("내부 시스템 문제로 로그인 요청을 처리할 수 없습니다. \n 관리자에게 문의해주세요.", response);
        else if(exception instanceof LockedException)sendMssgAndRedirect("비밀번호 5회 오류로 계정이 잠겼습니다. \n 관리자에게 문의해주세요.", response);

    }

    public void sendMssgAndRedirect(String message, HttpServletResponse response) throws IOException {
        String sendMessage = URLEncoder.encode(message, "UTF-8");
        response.sendRedirect("/login?error="+sendMessage);
    }

    public void sendMssgEnableAndRedirect(String message, String userId, HttpServletResponse response) throws IOException {
        String sendMessage = URLEncoder.encode(message, "UTF-8");
        response.sendRedirect("/login?enable="+sendMessage+"&userId="+userId);
    }

    public void sendmssgExpiredAndRedirect(String message, String userId, HttpServletResponse response)throws IOException {
        String sendMessage = URLEncoder.encode(message, "UTF-8");
        response.sendRedirect("/login?expired="+sendMessage+"&userId="+userId);
    }
}
