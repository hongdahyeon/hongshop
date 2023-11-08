package hongshop.hongshop.domain.deliver;

import hongshop.hongshop.domain.base.Address;
import hongshop.hongshop.domain.deliver.dto.HongDeliverAddressDTO;
import hongshop.hongshop.domain.deliver.dto.HongDeliverDTO;
import hongshop.hongshop.domain.deliver.dto.HongDeliverStatusDTO;
import hongshop.hongshop.domain.deliver.vo.HongDeliverVO;
import hongshop.hongshop.domain.order.HongOrder;

import java.util.List;

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

    void updateStatus(HongDeliverStatusDTO hongDeliverStatusDTO, Long id);

    List<HongDeliverVO> all();

    HongDeliverVO getByOrderId(Long orderId);

    void updateAddres(Long id, HongDeliverAddressDTO hongDeliverAddressDTO);

}
