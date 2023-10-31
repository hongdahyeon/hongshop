package hongshop.hongshop.domain.fileGroup.vo;

import hongshop.hongshop.domain.file.vo.HongFileVO;
import hongshop.hongshop.domain.fileGroup.HongFileGroup;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
* @fileName HongFileGroupVO
* @author dahyeon
* @version 1.0.0
* @date 2023-07-18
* @summary
**/

@Getter @Setter
public class HongFileGroupVO {

    private Long id;
    private List<HongFileVO> fileList = new ArrayList<>();

    public HongFileGroupVO(HongFileGroup hongFileGroup, List<HongFileVO> fileVOList) {
        this.id = hongFileGroup.getId();
        this.fileList = fileVOList;
    }
}
