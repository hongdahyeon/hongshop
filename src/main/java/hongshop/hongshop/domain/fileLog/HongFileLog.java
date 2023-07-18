package hongshop.hongshop.domain.fileLog;

import hongshop.hongshop.domain.file.HongFile;
import hongshop.hongshop.global.base.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
* @fileName HongFileLog
* @author dahyeon
* @version 1.0.0
* @date 2023-07-18
* @summary
**/

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "hong_file_log")
@Getter
public class HongFileLog extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hong_file_log_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hong_file_id")
    private HongFile hongFile;

    @Builder(builderMethodName = "hongFileLogInsertBuilder")
    public HongFileLog(HongFile hongFile) {
        this.hongFile = hongFile;
    }
}
