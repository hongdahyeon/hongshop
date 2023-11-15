package hongshop.hongshop.domain.message;

import hongshop.hongshop.domain.message.dto.HongMessageDTO;
import hongshop.hongshop.domain.message.vo.HongMessageVO;
import hongshop.hongshop.domain.user.HongUser;

import java.util.List;

public interface HongMessageService {

    Long join(HongMessageDTO hongMessageDTO);

    List<HongMessageVO> getMessageLst(HongUser receiver, HongUser sender);

    List<HongMessageVO> getMessageLstByReceiver(HongUser receiver);
}
