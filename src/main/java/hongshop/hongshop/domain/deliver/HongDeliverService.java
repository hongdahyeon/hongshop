package hongshop.hongshop.domain.deliver;

import hongshop.hongshop.domain.order.HongOrder;

public interface HongDeliverService {

    Long join(HongDeliverDTO hongDeliverDTO, HongOrder hongOrder);

    HongDeliverVO view(Long id);

    void update(HongDeliverDTO hongDeliverDTO);

}
