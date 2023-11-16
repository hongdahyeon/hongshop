package hongshop.hongshop.domain.message.impl;

import hongshop.hongshop.domain.message.HongMessage;
import hongshop.hongshop.domain.message.HongMessageRepository;
import hongshop.hongshop.domain.message.HongMessageService;
import hongshop.hongshop.domain.message.dto.HongMessageDTO;
import hongshop.hongshop.domain.message.vo.HongMessageVO;
import hongshop.hongshop.domain.user.HongUser;
import hongshop.hongshop.domain.user.HongUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* @fileName HongMessageServiceIml
* @author dahyeon
* @version 1.0.0
* @date 2023-11-15
* @summary  (1) join: 메시지 저장
 *          (2) getMessageLst : 받는자, 보내는자 -> 메시지 리스트 (receiver, sender 가 나눈 대화 내용 리스트 조회)
 *          (3) getMessageLstByReceiver : 받은자(receiver)입장에서 -> 자기한테 보낸 사용자 리스트 조회
**/

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HongMessageServiceIml implements HongMessageService {

    private final HongMessageRepository hongMessageRepository;
    private final HongUserRepository hongUserRepository;

    @Override
    @Transactional(readOnly = false)
    public List<HongMessageVO> join(HongMessageDTO hongMessageDTO) {
        HongUser receiver = hongUserRepository.findById(hongMessageDTO.getReceiverId()).orElseThrow(() -> new IllegalArgumentException("there is no user"));            // 톡톡을 나누는 상대 사용자 id
        HongUser sender = hongUserRepository.findById(hongMessageDTO.getSenderId()).orElseThrow(() -> new IllegalArgumentException("there is no user"));                // 로그인한 사용자 id

        HongMessage hongMessage = HongMessage.hongMessageInsert()
                                        .messageContent(hongMessageDTO.getMessageContent())
                                        .receiver(receiver)
                                        .sender(sender)
                                        .build();

        hongMessageRepository.save(hongMessage);

        return this.getMessageLst(receiver.getId(), sender.getId());    // 현재 저장된 메시지까지 해서 receiver, sender가 나눈 대화 내용 return
    }

    @Override
    public List<HongMessageVO> getMessageLst(Long receiverId, Long senderId) {
        List<HongMessage> all = hongMessageRepository.findMessagesBetweenSenderAndReceiver(receiverId, senderId);
        return all.stream()
                .map(HongMessageVO::new).toList();
    }

    @Override
    public List<HongMessageVO> getMessageLstByReceiver(HongUser receiver) {
        List<HongUser> userLst = hongMessageRepository.findDistinctSendersByReceiver(receiver.getId());
        return userLst.stream().map(HongMessageVO::new).toList();
    }
}
