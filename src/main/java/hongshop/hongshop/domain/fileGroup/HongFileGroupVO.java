package hongshop.hongshop.domain.fileGroup;

import hongshop.hongshop.domain.file.HongFileVO;
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
    private List<HongFileVO> fileVOList = new ArrayList<>();

    public HongFileGroupVO(HongFileGroup hongFileGroup, List<HongFileVO> fileVOList) {
        this.id = hongFileGroup.getId();
        this.fileVOList = fileVOList;
    }
}
