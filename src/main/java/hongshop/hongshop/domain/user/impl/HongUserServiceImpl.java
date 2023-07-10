package hongshop.hongshop.domain.user.impl;

import hongshop.hongshop.domain.user.HongUser;
import hongshop.hongshop.domain.user.HongUserDTO;
import hongshop.hongshop.domain.user.HongUserRepository;
import hongshop.hongshop.domain.user.HongUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HongUserServiceImpl implements HongUserService {

    private final HongUserRepository hongUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = false)
    public Long joinUser(HongUserDTO hongUserDTO) {

        String encodePassword = passwordEncoder.encode(hongUserDTO.getPassword());
        HongUser hongUser = HongUser.hongUserInsertBuilder()
                .userId(hongUserDTO.getUserId())
                .password(encodePassword)
                .role(hongUserDTO.getRole())
                .build();

        HongUser save = hongUserRepository.save(hongUser);
        return save.getId();
    }

    @Override
    public Optional<HongUser> getHongUser(String userId) {
        System.out.println("userId = " + userId);
        return hongUserRepository.findByUserId(userId);
    }
}
