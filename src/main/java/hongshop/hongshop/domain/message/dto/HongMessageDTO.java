package hongshop.hongshop.domain.message.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HongMessageDTO {
    private Long senderId;
    private Long receiverId;
    private String messageContent;
}