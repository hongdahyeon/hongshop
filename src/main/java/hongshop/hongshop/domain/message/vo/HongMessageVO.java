package hongshop.hongshop.domain.message.vo;

import hongshop.hongshop.domain.message.HongMessage;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.text.StringEscapeUtils;

@Getter
@Setter
public class HongMessageVO {

    private Long senderId;
    private String senderName;
    private String senderUserId;

    private Long receiverId;
    private String receiverName;
    private String receiverUserId;

    private String messageContent;

    public HongMessageVO(HongMessage hongMessage){
        this.senderId = hongMessage.getSender().getId();
        this.senderName = hongMessage.getSender().getUserName();
        this.senderUserId = hongMessage.getSender().getUserId();

        this.receiverId = hongMessage.getReceiver().getId();
        this.receiverName = hongMessage.getReceiver().getUserName();
        this.receiverUserId = hongMessage.getReceiver().getUserId();

        this.messageContent = StringEscapeUtils.unescapeHtml4(hongMessage.getMessageContent());
    }
}
