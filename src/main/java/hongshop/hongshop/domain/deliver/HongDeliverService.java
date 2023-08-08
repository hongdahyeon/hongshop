package hongshop.hongshop.domain.deliver;

import hongshop.hongshop.domain.base.Address;
import hongshop.hongshop.domain.order.HongOrder;

/**
* @fileName HongDeliverService
* @author dahyeon
* @version 1.0.0
* @date 2023-07-19
* @summary
**/

public interface HongDeliverService {

    Long join(Address address, HongOrder hongOrder);

    HongDeliverVO view(Long id);

    void update(HongDeliverDTO hongDeliverDTO, Long id);

}