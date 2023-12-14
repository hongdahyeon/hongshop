package hongshop.hongshop.domain.user.impl;

import hongshop.hongshop.domain.base.Address;
import hongshop.hongshop.domain.user.HongRoleType;
import hongshop.hongshop.domain.user.HongUser;
import hongshop.hongshop.domain.user.HongUserRepository;
import hongshop.hongshop.domain.user.HongUserService;
import hongshop.hongshop.domain.user.dto.HongUserPwdDateDTO;
import hongshop.hongshop.domain.user.dto.HongUserDTO;
import hongshop.hongshop.domain.user.dto.HongUserRoleDTO;
import hongshop.hongshop.domain.user.vo.HongUserCouponVO;
import hongshop.hongshop.domain.user.vo.HongUserMessageVO;
import hongshop.hongshop.domain.user.vo.HongUserVO;
import hongshop.hongshop.global.mail.EmailService;
import hongshop.hongshop.global.util.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @fileName HongUserServiceImpl
 * @author dahyeon
 * @version 1.0.0
 * @date 2023-07-17
 * @summary  사용자 service Impl
 *           (1) joinUser : 사용자 회원가입
 *           (2) getHongUser : userId로 사용자 조회 -> return Optional (PrincipalDetailsService 에서 사용)
 *           (3) getHongUserByUserId : userId로 사용자 조회 -> return HongUserVO
 *           (4) checkUserId : userId로 사용자 있는지 조회 -> return boolean
 *           (5) checkUserEmail : 사용자 이메일 중복 확인
 *           (6) updateHongUser : 사용자 정보 수정
 *           (7) getHongUserById : id로 사용자 찾기 -> return HongUserVO
 *           (8) list : 사용자 리스트 조회
 *           (9) updateUserRole : 사용자 권한 수정
 *           (10) initialPassword : 사용자 비번 초기화 -> 이메일 전송
 *           (11) findUserId : 사용자 이름 & 이메일로 아이디 찾기 -> 이메일 전송
 *           (12) updateUserNonLocked : 사용자 계정 정지 초기화
 *           (13) getAddress : id를 통해 유저 주소 정보 가져오기
 *           (14) getUserListForCoupon : 쿠폰 발급을 위한 사용자 리스트 조회
 *           (15) getMessageCanUser : 'ROLE_SUPER' 권한을 갖는 유저 리스트 가져오기 -> 이때 내 자신이 있다면 나는 빼고
 *           (16) getUserAndEnable : return entity and change enable to disable
 *           (17) changeDisableToEnable : change disable to enable
 *           (18) changePwdEndDate : 비밀번호 변경 및 비밀번호 만료일 90일 연장하기
 *           (19) sendEmail : userId & userEmail로 사용자 찾아서, 이메일로 인증번호 발송하기
 **/

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class HongUserServiceImpl implements HongUserService {

    private final HongUserRepository hongUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    @Override
    @Transactional(readOnly = false)
    public Long joinUser(HongUserDTO hongUserDTO) {

        Optional<HongUser> byUserId = hongUserRepository.findByUserId(hongUserDTO.getUserId());
        if(byUserId.isPresent()) throw new IllegalArgumentException("id is already used");

        String encodePassword = passwordEncoder.encode(hongUserDTO.getPassword());
        HongUser hongUser = HongUser.hongUserInsertBuilder()
                .userId(hongUserDTO.getUserId())
                .password(encodePassword)
                .role(hongUserDTO.getRole())
                .address(new Address(hongUserDTO.getCity(), hongUserDTO.getStreet(), hongUserDTO.getZipcode()))
                .userName(hongUserDTO.getUserName())
                .userEmail(hongUserDTO.getUserEmail())
                .build();

        HongUser save = hongUserRepository.save(hongUser);
        return save.getId();
    }

    @Override
    public Optional<HongUser> getHongUser(String userId) {
        return hongUserRepository.findByUserId(userId);
    }

    @Override
    public HongUserVO getHongUserByUserId(String userId) {
        HongUser hongUser = hongUserRepository.findByUserId(userId).orElseThrow(() -> new IllegalArgumentException("there is no user"));
        return new HongUserVO(hongUser);
    }

    @Override
    public Boolean checkUserId(String userId) {
        return hongUserRepository.findByUserId(userId).isEmpty();
    }

    @Override
    public int checkUserEmail(String userEmail) {
        int checkIt = 0;
        Optional<HongUser> byUserEmail = hongUserRepository.findByUserEmail(userEmail);
        if(byUserEmail.isPresent()) checkIt = 1;
        return checkIt;
    }

    @Override
    @Transactional(readOnly = false)
    public void updateHongUser(HongUserDTO hongUserDTO) {
        HongUser hongUser = hongUserRepository.findByUserId(hongUserDTO.getUserId()).orElseThrow(() -> new IllegalArgumentException("there is no user"));
        if(hongUserDTO.getPassword() != null) {
            hongUserDTO.setPassword(passwordEncoder.encode(hongUserDTO.getPassword()));
            hongUser.add90Days();                                                           // 비번 변경 -> 비번만료일 90연장 (오늘부터)
        }
        hongUser.updateHongUser(hongUserDTO);
    }

    @Override
    public HongUserVO getHongUserById(Long id) {
        HongUser user = hongUserRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("there is no user"));
        return new HongUserVO(user);
    }

    @Override
    public List<HongUserVO> list() {
        List<HongUser> hongUsers = hongUserRepository.findAll();
        return hongUsers.stream().map(HongUserVO::new).toList();
    }

    @Override
    @Transactional(readOnly = false)
    public void updateUserRole(Long id, HongUserRoleDTO hongUserRoleDTO) {
        HongUser user = hongUserRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("there is no user"));
        user.updateUserRole(hongUserRoleDTO.getRole());
    }

    @Override
    @Transactional(readOnly = false)
    public boolean initialPassword(String userEmail, String userName) {
        Optional<HongUser> user = hongUserRepository.findByUserEmailAndUserName(userEmail, userName);
        if(user.isEmpty()) return false;
        else {
            HongUser hongUser = user.get();

            String initialPassword = StringUtil.random(6);
            hongUser.updatePassword(passwordEncoder.encode(initialPassword));           // password 초기화
            emailService.sendInitialPwdEmail(userEmail, initialPassword);    // send initialPassword

            return true;
        }
    }

    @Override
    public boolean findUserId(String userEmail, String userName) {
        Optional<HongUser> user = hongUserRepository.findByUserEmailAndUserName(userEmail, userName);
        if(user.isEmpty()) return false;
        else {
            String userId = user.get().getUserId();
            emailService.sendUserIdEmail(userEmail, userId);
            return true;
        }
    }

    @Override
    @Transactional(readOnly = false)
    public void updateUserNonLocked(String userId) {
        HongUser hongUser = hongUserRepository.findByUserId(userId).orElseThrow(() -> new IllegalArgumentException("there is no user"));
        hongUser.resetPwdFailCntAndUserNonLocked();
    }

    @Override
    public Address getAddress(Long id) {
        HongUser hongUser = hongUserRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("there is no user"));
        return hongUser.getAddress();
    }

    @Override
    public List<HongUserCouponVO> getUserListForCoupon() {
        List<HongUser> hongUsers = hongUserRepository.findAll();
        return hongUsers.stream().map(HongUserCouponVO::new).toList();
    }

    @Override
    public List<HongUserMessageVO> getMessageCanUser(HongUser itsMe) {
        List<HongRoleType> roleTypeList = new ArrayList<>();
        roleTypeList.add(HongRoleType.ROLE_SUPER);
//        roleTypeList.add(HongRoleType.ROLE_ADMIN);
        List<HongUser> allByRoleIn = hongUserRepository.findAllByRoleIn(roleTypeList);
        return allByRoleIn.stream().filter(user -> !user.getId().equals(itsMe.getId()))
                .map(HongUserMessageVO::new).toList();
    }

    @Override
    @Transactional(readOnly = false)
    public HongUser getUserAndChangeEnable(Long id) {
        HongUser hongUser = hongUserRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("there is no user"));
        hongUser.changeEnableToDisable();
        return hongUser;
    }

    @Override
    @Transactional(readOnly = false)
    public void changeDisableToEnable(Long id) {
        HongUser hongUser = hongUserRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("there is no user"));
        hongUser.changeDisableToEnable();
    }

    @Override
    @Transactional(readOnly = false)
    public void changePwdEndDate(HongUserPwdDateDTO hongUserPwdDateDTO) {
        HongUser hongUser = hongUserRepository.findByUserId(hongUserPwdDateDTO.getUserId()).orElseThrow(() -> new IllegalArgumentException("there is no user"));
        hongUser.add90Days();

        if(hongUserPwdDateDTO.getPassword() != null) {
            String encodePassword = passwordEncoder.encode(hongUserPwdDateDTO.getPassword());
            hongUser.updatePassword(encodePassword);
        }
    }

    @Override
    public int sendEmail(String userId, String userEmail) {
        Optional<HongUser> findUser = hongUserRepository.findByUserIdAndUserEmail(userId, userEmail);
        if(findUser.isEmpty()) return 1;
        else {
            emailService.sendVerification(userEmail);
            return 2;
        }
    }
}
