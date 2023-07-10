package hongshop.hongshop.domain.user;

import java.util.Optional;

public interface HongUserService {

    Long joinUser(HongUserDTO hongUserDTO);

    Optional<HongUser> getHongUser(String userId);

}
