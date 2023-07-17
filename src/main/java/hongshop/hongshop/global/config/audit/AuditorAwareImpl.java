package hongshop.hongshop.global.config.audit;

import hongshop.hongshop.global.auth.PrincipalDetails;
import hongshop.hongshop.global.util.UserUtil;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

/**
 * @fileName AuditorAwareImpl
 * @author dahyeon
 * @version 1.0.0
 * @date 2023-07-17
 * @summary 현재 로그인한 사용자를 찾고 -> 해당 사용자의 id 값을 optional로 반환한다.
 *          - 만약 사용자, 작성자 컬럼이 Long이 아닌 String 값이라면 AuditorAware<String>으로 implements 받으면 된다.
 **/

public class AuditorAwareImpl implements AuditorAware<Long> {
    @Override
    public Optional<Long> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(UserUtil.isAuthenticated(authentication)) {
            PrincipalDetails details = (PrincipalDetails) authentication.getPrincipal();
            return Optional.of(details.getUser().getId());
        }else {
            throw new IllegalArgumentException("you need to login");
            // redirect to login page
        }
    }
}
