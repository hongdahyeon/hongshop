package hongshop.hongshop.global.util;

import org.springframework.security.core.Authentication;

public class UserUtil {

    public static boolean isAuthenticated(Authentication authentication){
        return authentication != null && authentication.getPrincipal() != "anonymousUser";
    }
}
