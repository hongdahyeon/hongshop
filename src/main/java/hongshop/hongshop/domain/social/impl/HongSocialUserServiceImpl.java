package hongshop.hongshop.domain.social.impl;

import hongshop.hongshop.domain.base.Address;
import hongshop.hongshop.domain.social.HongSocialUser;
import hongshop.hongshop.domain.social.HongSocialUserRepository;
import hongshop.hongshop.domain.social.HongSocialUserService;
import hongshop.hongshop.domain.user.HongRoleType;
import hongshop.hongshop.domain.user.HongUser;
import hongshop.hongshop.domain.user.HongUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;


/**
* @fileName HongSocialUserServiceImpl
* @author dahyeon
* @version 1.0.0
* @date 2023-11-01
* @summary  (1) findUser : 쇼셜 로그인한 사용자 정보 찾기
*                  -> 있으면 HongUser로 해당 사용자 반환
*                  -> 없으면 null 반환 -> 사용자 회원가입 절차 진행
*
*           (2) join : 소셜 로그인 테이블에 insert -> 회원 정보 테이블에 insert
**/

@Service
@Transactional
@RequiredArgsConstructor
public class HongSocialUserServiceImpl implements HongSocialUserService {

    private final HongSocialUserRepository hongSocialUserRepository;
    private final HongUserRepository hongUserRepository;
    @Override
    public HongUser findUser(String userId) {
        HongSocialUser socialUser = hongSocialUserRepository.findByUserId(userId);
        if(socialUser == null) return null;
        else {
            HongUser hongUser = hongUserRepository.findByUserId(userId).get();
            return hongUser;
        }
    }

    @Override
    @Transactional(readOnly = false)
    public HongUser join(String userId, String name, String email) {

        // (1) 소셜 로그인 테이블 insert
        HongSocialUser user = HongSocialUser.hongSocialUserInsertBuilder()
                .userId(userId)
                .build();

        HongSocialUser hongSocialUser = hongSocialUserRepository.save(user);

        String password = new BCryptPasswordEncoder().encode(UUID.randomUUID().toString());

        // (2) 회원 정보 테이블 insert
        HongUser hongUser = HongUser.hongUserInsertBuilder()
                .userId(userId)
                .password(password)
                .role(HongRoleType.ROLE_USER)
                .address(new Address("", "", ""))
                .userName(name)
                .userEmail(email)
                .hongSocialUser(hongSocialUser)
                .build();

        HongUser saveUser = hongUserRepository.save(hongUser);
        return saveUser;
    }
}
