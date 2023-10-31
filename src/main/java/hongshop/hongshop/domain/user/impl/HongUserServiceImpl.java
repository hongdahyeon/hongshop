package hongshop.hongshop.domain.user.impl;

import hongshop.hongshop.domain.base.Address;
import hongshop.hongshop.domain.user.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @fileName HongUserServiceImpl
 * @author dahyeon
 * @version 1.0.0
 * @date 2023-07-17
 * @summary  사용자 service Impl
 **/

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class HongUserServiceImpl implements HongUserService {

    private final HongUserRepository hongUserRepository;
    private final PasswordEncoder passwordEncoder;

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
        log.info("userId : {} ", userId);
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
    public Boolean checkUserEmail(String userEmail) {
        return hongUserRepository.findByUserEmail(userEmail).isEmpty();
    }

    @Override
    @Transactional(readOnly = false)
    public void updateHongUser(HongUserDTO hongUserDTO) {
        HongUser hongUser = hongUserRepository.findByUserId(hongUserDTO.getUserId()).orElseThrow(() -> new IllegalArgumentException("there is no user"));
        if(hongUserDTO.getPassword() != null) hongUserDTO.setPassword(passwordEncoder.encode(hongUserDTO.getPassword()));
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
}
