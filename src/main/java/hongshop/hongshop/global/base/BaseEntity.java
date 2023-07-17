package hongshop.hongshop.global.base;

import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

/**
* @fileName BaseEntity
* @author dahyeon
* @version 1.0.0
* @date 2023-07-17
* @summary  작성자, 수정자 컬럼의 경우 많은 테이블에서 공통으로 이용된다.
 *          따로 뺴두어 공통 매핑 정보로 등록한다.
**/

@Getter
@MappedSuperclass  // 공통 매핑 정보가 필요할 경우 사용 -> 부모 클래스를 상속받은 자식 클래스에서 매핑 정보만 제공
@EntityListeners(value = {AuditingEntityListener.class}) // Auditing 적용 어노테이션
public class BaseEntity extends BaseTimeEntity {

    @CreatedBy
    @Column(updatable = false)
    private Long regId;

    @LastModifiedBy
    private Long updtId;
}
