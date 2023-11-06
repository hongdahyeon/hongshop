package hongshop.hongshop.domain.fileGroup.impl;

import hongshop.hongshop.domain.file.FileState;
import hongshop.hongshop.domain.file.HongFile;
import hongshop.hongshop.domain.file.HongFileRepository;
import hongshop.hongshop.domain.file.vo.HongFileVO;
import hongshop.hongshop.domain.fileGroup.HongFileGroup;
import hongshop.hongshop.domain.fileGroup.HongFileGroupRepository;
import hongshop.hongshop.domain.fileGroup.HongFileGroupService;
import hongshop.hongshop.domain.fileGroup.vo.HongFileGroupVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* @fileName HongFileGroupServiceImpl
* @author dahyeon
* @version 1.0.0
* @date 2023-07-18
* @summary
**/

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HongFileGroupServiceImpl implements HongFileGroupService {

    private final HongFileGroupRepository fileGroupRepository;
    private final HongFileRepository fileRepository;

    @Override
    public List<HongFileGroupVO> all() {
        List<HongFileGroup> all = fileGroupRepository.findAll();
        return all.stream().map(group -> {
            List<HongFile> fileList = fileRepository.findAllByHongFileGroupIdAndDeleteYnAndFileState(group.getId(), "N", FileState.SAVED);
            List<HongFileVO> listOfFileVO = fileList.stream().map(HongFileVO::new).toList();
            return new HongFileGroupVO(group, listOfFileVO);
        }).filter(hongFileGroupVO -> !hongFileGroupVO.getFileList().isEmpty()).toList();
    }

    @Override
    public HongFileGroupVO listwithDeleteYnAndFileState(Long id, String deleteYn, FileState fileState) {
        HongFileGroup fileGroup = fileGroupRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("there is no file gorup"));
        List<HongFile> fileList = fileRepository.findAllByHongFileGroupIdAndDeleteYnAndFileState(id, deleteYn, fileState);
        List<HongFileVO> listOfFileVO = fileList.stream().map(HongFileVO::new).toList();
        return new HongFileGroupVO(fileGroup, listOfFileVO);
    }

    @Override
    @Transactional(readOnly = false)
    public HongFileGroup saveFileGroup() {
        HongFileGroup fileGroup = HongFileGroup.hongFileGroupInsertBuilder()
                .deleteYn("N")
                .build();
        return fileGroupRepository.save(fileGroup);
    }

    @Override
    public HongFileGroup findFileGroup(Long id) {
        return fileGroupRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("there is no file group"));
    }
}
