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
 *          (2) getMessageLst : 받는자, 보내는자 -> 메시지 리스트
 *          (3) getMessageLstByReceiver : 받는자 -> 메시지 리스트
**/

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HongMessageServiceIml implements HongMessageService {

    private final HongMessageRepository hongMessageRepository;
    private final HongUserRepository hongUserRepository;

    @Override
    @Transactional(readOnly = false)
    public Long join(HongMessageDTO hongMessageDTO) {
        HongUser receiver = hongUserRepository.findById(hongMessageDTO.getReceiverId()).orElseThrow(() -> new IllegalArgumentException("there is no user"));
        HongUser sender = hongUserRepository.findById(hongMessageDTO.getSenderId()).orElseThrow(() -> new IllegalArgumentException("there is no user"));

        HongMessage hongMessage = HongMessage.hongMessageInsert()
                                        .messageContent(hongMessageDTO.getMessageContent())
                                        .receiver(receiver)
                                        .sender(sender)
                                        .build();

        HongMessage save = hongMessageRepository.save(hongMessage);
        return save.getId();
    }

    @Override
    public List<HongMessageVO> getMessageLst(HongUser receiver, HongUser sender) {
        List<HongMessage> all = hongMessageRepository.findAllByReceiver_IdAndSender_IdOrderByCreatedDateDesc(receiver.getId(), sender.getId());
        return all.stream().map(HongMessageVO::new).toList();
    }

    @Override
    public List<HongMessageVO> getMessageLstByReceiver(HongUser receiver) {
        List<HongMessage> all = hongMessageRepository.findAllByReceiver_IdOrderByCreatedDateDesc(receiver.getId());
        return all.stream().map(HongMessageVO::new).toList();
    }
}
