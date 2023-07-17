package hongshop.hongshop.global.base;

import hongshop.hongshop.global.util.TimeUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

/**
 * @fileName BaseTimeEntity
 * @author dahyeon
 * @version 1.0.0
 * @date 2023-07-17
 * @summary  작성일, 수정일 컬럼의 경우 많은 테이블에서 공통으로 이용된다.
 *          따로 뺴두어 공통 매핑 정보로 등록한다.
 *          * MappedSuperclass : 이렇듯 공통 매핑 정보가 필요할 때 해당 어노테이션을 공통 속성을 모아둔 클래스에 선언한다.
 *                               그리고 해당 어노테이션이 붙은 클래스를 엔티티에서 extend 받아 사용한다.
 **/

@MappedSuperclass
@Getter @Setter
public class BaseTimeEntity {

    @CreatedDate
    @Column(updatable = false)
    private String createdDate;

    @LastModifiedDate
    private String modifiedDate;

    @PrePersist
    public void onPrePersist(){
        this.createdDate = TimeUtil.nowDate();
        this.modifiedDate = this.createdDate;
    }

    @PreUpdate
    public void onPreUpdate(){
        this.modifiedDate = TimeUtil.nowDate();
    }

}
