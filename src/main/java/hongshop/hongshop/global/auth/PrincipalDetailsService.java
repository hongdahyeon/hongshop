package hongshop.hongshop.global.auth;

import hongshop.hongshop.domain.user.HongUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final HongUserService hongUserService;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {

        return hongUserService.getHongUser(userId)
                .map(PrincipalDetails::new)
                .orElseThrow(() -> new NoSuchElementException("there is no user"));
    }
}
