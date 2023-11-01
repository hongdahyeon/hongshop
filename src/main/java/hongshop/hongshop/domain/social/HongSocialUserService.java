package hongshop.hongshop.domain.social;

import hongshop.hongshop.domain.user.HongUser;

public interface HongSocialUserService {

    HongUser findUser(String userId);

    HongUser join(String userId, String name, String email);
}
