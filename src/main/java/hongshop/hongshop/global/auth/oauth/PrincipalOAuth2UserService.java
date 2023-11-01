package hongshop.hongshop.global.auth.oauth;


import hongshop.hongshop.domain.social.HongSocialUserService;
import hongshop.hongshop.domain.user.HongUser;
import hongshop.hongshop.global.auth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class PrincipalOAuth2UserService extends DefaultOAuth2UserService {

    private final HongSocialUserService hongSocialUserService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(userRequest);
        String provider = userRequest.getClientRegistration().getRegistrationId();
        Map<String, Object> userInfo = user.getAttributes();

        Map<String, Object> kakaoAccount = user.getAttribute("kakao_account");
        String userId = provider + "_" + userInfo.get("id");
        String email = (String) kakaoAccount.get("email");
        String name = (String) ((Map<String, Object>) kakaoAccount.get("profile")).get("nickname");

        log.info("provider : {} ", provider);           // kakao
        log.info("userId : {} ", userId);               // kakao_3142662278
        log.info("name : {} ", name);                   // 다현
        log.info("email : {} ", email);                 // julie0427@kakao.com

        HongUser hongUser = hongSocialUserService.findUser(userId);
        if(hongUser == null) {
            // 회원가입 로직
            hongUser = hongSocialUserService.join(userId, name, email);
            log.info("joinId : {} ", hongUser.getUserId());
        }else {
            // 로그인 로직
            log.info("not null : {}", hongUser.getId());
        }

        return new PrincipalDetails(hongUser, userInfo);
    }

}