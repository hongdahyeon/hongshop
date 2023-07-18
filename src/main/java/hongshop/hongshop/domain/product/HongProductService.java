package hongshop.hongshop.domain.product;

import java.util.List;

public interface HongProductService {

    Long save(HongProductDTO hongProductDTO);

    List<HongProductVO> list();

    HongProductVO view(Long id);

    void update(HongProductDTO hongProductDTO, Long id);

}
