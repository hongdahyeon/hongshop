package hongshop.hongshop.domain.order;

import lombok.Getter;
import lombok.Setter;

/**
 * @fileName HongOrderStatusDTO
 * @author dahyeon
 * @version 1.0.0
 * @date 2023-10-30
 * @summary  주문 상태값 변경 dto
 **/

@Getter @Setter
public class HongOrderStatusDTO {

    private OrderStatus orderStatus;
}
