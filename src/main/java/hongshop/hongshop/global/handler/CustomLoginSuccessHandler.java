package hongshop.hongshop.global.handler;

import hongshop.hongshop.domain.user.HongUser;
import hongshop.hongshop.domain.user.HongUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
* @fileName CustomLoginSuccessHandler
* @author dahyeon
* @version 1.0.0
* @date 2023-11-03
* @summary 로그인을 성공적으로 했을 경우 타게 되는 로직
 *          -> 비번 실패 카운트 0으로 초기화
 *          -> index 페이지로 redirect
 *              (if) social login, address가 저장되지 않기 때문에 내정보 화면으로 이동
**/

@Component
@Slf4j
@RequiredArgsConstructor
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    private final HongUserRepository hongUserRepository;

    @Override
    @Transactional(readOnly = false)
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication
    ) throws IOException, ServletException {
        String userId = authentication.getName();

        HongUser hongUser = hongUserRepository.findByUserId(userId).get();
        hongUser.resetPwdFailCntAndUserNonLocked();     // 비번 실패 카운트 초기화, 사용자 계정 잠김 풀기
        hongUser.updateLastLoginDate();                 // 마지막 로그인 날짜, 오늘로 변경

        if(hongUser.getAddress().getCity().length() == 0 || hongUser.getAddress().getStreet().length() == 0 || hongUser.getAddress().getZipcode().length() == 0) response.sendRedirect("/user/myInfo");
        else response.sendRedirect("/");
    }
}
