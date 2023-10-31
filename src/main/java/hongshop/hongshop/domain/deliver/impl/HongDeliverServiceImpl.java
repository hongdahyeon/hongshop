package hongshop.hongshop.domain.deliver.impl;

import hongshop.hongshop.domain.base.Address;
import hongshop.hongshop.domain.deliver.*;
import hongshop.hongshop.domain.deliver.dto.HongDeliverDTO;
import hongshop.hongshop.domain.deliver.vo.HongDeliverVO;
import hongshop.hongshop.domain.order.HongOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* @fileName HongDeliverServiceImpl
* @author dahyeon
* @version 1.0.0
* @date 2023-07-19
* @summary
**/

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HongDeliverServiceImpl implements HongDeliverService {

    private final HongDeliverRepository hongDeliverRepository;

    @Override
    @Transactional(readOnly = false)
    public Long join(Address address, HongOrder hongOrder) {
        HongDeliver hongDeliver = HongDeliver.hongDeliverInsertBuilder()
                .hongOrder(hongOrder)
                .deliverStatus(DeliverStatus.AWAIT)
                .address(address)
                .build();

        HongDeliver save = hongDeliverRepository.save(hongDeliver);
        return save.getId();
    }

    @Override
    public HongDeliverVO view(Long id) {
        HongDeliver hongDeliver = hongDeliverRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("there is no deliver"));
        return new HongDeliverVO(hongDeliver);
    }

    @Override
    @Transactional(readOnly = false)
    public void update(HongDeliverDTO hongDeliverDTO, Long id) {
        HongDeliver hongDeliver = hongDeliverRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("there is no deliver"));
        hongDeliver.updateDeliver(hongDeliverDTO);
    }
}
