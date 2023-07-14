package hongshop.hongshop.domain.user.impl;

import hongshop.hongshop.domain.user.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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
        return new HongUserVO(hongUser.getUserId(), hongUser.getRole(), hongUser.getAddress());
    }
}
