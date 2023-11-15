package hongshop.hongshop.domain.message;

import hongshop.hongshop.domain.user.HongUser;
import hongshop.hongshop.global.base.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
* @fileName HongMessage
* @author dahyeon
* @version 1.0.0
* @date 2023-11-15
* @summary 톡톡을 위한 메시지 저장 테이블
**/

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "hong_message")
public class HongMessage extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "hong_message_id")
    private Long id;

    @Column(name = "message_content")
    private String messageContent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private HongUser sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    private HongUser receiver;

    @Builder(builderMethodName = "hongMessageInsert")
    public HongMessage(HongUser sender, HongUser receiver, String messageContent) {
        this.sender = sender;
        this.receiver = receiver;
        this.messageContent = messageContent;
    }
}
