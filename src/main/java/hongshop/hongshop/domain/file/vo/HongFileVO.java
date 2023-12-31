package hongshop.hongshop.domain.file.vo;

import hongshop.hongshop.domain.file.FileState;
import hongshop.hongshop.domain.file.HongFile;
import lombok.Getter;
import lombok.Setter;

/**
* @fileName HongFileVO
* @author dahyeon
* @version 1.0.0
* @date 2023-07-18
* @summary 파일 정보 조회 VO
**/

@Getter @Setter
public class HongFileVO {

    private Long id;
    private String fileUuid;
    private String savedFileName;
    private String originalFileName;
    private String filePath;
    private Long fileSize;
    private String fileType;
    private String extension;
    private FileState fileState;
    private String deleteYn;
    private Integer downCnt;

    public HongFileVO(HongFile hongFile) {
        this.id = hongFile.getId();
        this.fileUuid = hongFile.getFileUuid();
        this.savedFileName = hongFile.getSavedFileName();
        this.originalFileName = hongFile.getOriginalFileName();
        this.filePath = hongFile.getFilePath();
        this.fileSize = hongFile.getFileSize();
        this.fileType = hongFile.getFileType();
        this.extension = hongFile.getExtension();
        this.fileState = hongFile.getFileState();
        this.deleteYn = hongFile.getDeleteYn();
        this.downCnt = hongFile.getDownCnt();
    }
}
