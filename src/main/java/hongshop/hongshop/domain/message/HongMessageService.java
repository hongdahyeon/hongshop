package hongshop.hongshop.domain.message;

import hongshop.hongshop.domain.message.dto.HongMessageDTO;
import hongshop.hongshop.domain.message.vo.HongMessageVO;
import hongshop.hongshop.domain.user.HongUser;

import java.util.List;

public interface HongMessageService {

    List<HongMessageVO> join(HongMessageDTO hongMessageDTO);

    List<HongMessageVO> getMessageLst(Long receiverId, Long senderId);

    List<HongMessageVO> getMessageLstByReceiver(HongUser receiver);
}
