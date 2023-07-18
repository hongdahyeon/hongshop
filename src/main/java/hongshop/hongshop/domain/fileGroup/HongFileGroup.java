package hongshop.hongshop.domain.fileGroup;

import hongshop.hongshop.global.base.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
* @fileName HongFileGroup
* @author dahyeon
* @version 1.0.0
* @date 2023-07-18
* @summary
**/

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "hong_file_group")
public class HongFileGroup extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "hong_file_group_id")
    private Long id;

    @Column(name = "delete_yn")
    private String deleteYn;

    @Builder(builderMethodName = "hongFileGroupInsertBuilder")
    public HongFileGroup(String deleteYn){
        this.deleteYn = deleteYn;
    }
}
