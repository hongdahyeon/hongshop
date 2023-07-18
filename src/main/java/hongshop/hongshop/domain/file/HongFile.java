package hongshop.hongshop.domain.file;

import hongshop.hongshop.domain.fileGroup.HongFileGroup;
import hongshop.hongshop.global.base.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
* @fileName HongFile
* @author dahyeon
* @version 1.0.0
* @date 2023-07-18
* @summary
**/

@Entity
@Table(name = "hong_file")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class HongFile extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "hong_file_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hong_file_group_id")
    private HongFileGroup hongFileGroup;

    @Column(name = "file_uuid", unique = true)
    private String fileUuid;

    @Column(name = "saved_file_name")
    private String savedFileName;

    @Column(name = "original_file_name")
    private String originalFileName;

    @Column(name = "file_path")
    private String filePath;

    @Column(name = "file_size")
    private Long fileSize;

    @Column(name = "file_type")
    private String fileType;

    @Column(name = "extension")
    private String extension;

    @Enumerated(EnumType.STRING)
    private FileState fileState;

    @Column(name = "delete_yn")
    private String deleteYn;

    @Column(name = "down_cnt")
    private Integer downCnt;

    @Builder(builderMethodName = "hongFileInsertBuilder")
    public HongFile(String fileUuid, HongFileGroup hongFileGroup ,String savedFileName, String originalFileName, String filePath, Long fileSize, String fileType, String extension) {
        this.fileUuid = fileUuid;
        this.hongFileGroup = hongFileGroup;
        this.savedFileName = savedFileName;
        this.originalFileName = originalFileName;
        this.filePath = filePath;
        this.fileSize = fileSize;
        this.fileType = fileType;
        this.extension = extension;
        this.fileState = FileState.PROCESS;
        this.deleteYn = "N";
        this.downCnt = 0;
    }

    public void updateDownCnt(){
        this.downCnt = this.downCnt + 1;
    }

}
