package hongshop.hongshop.domain.file.impl;

import hongshop.hongshop.domain.file.FileState;
import hongshop.hongshop.domain.file.HongFile;
import hongshop.hongshop.domain.file.HongFileRepository;
import hongshop.hongshop.domain.file.HongFileService;
import hongshop.hongshop.domain.file.vo.HongFileVO;
import hongshop.hongshop.domain.fileGroup.HongFileGroup;
import hongshop.hongshop.domain.fileGroup.HongFileGroupService;
import hongshop.hongshop.domain.fileLog.HongFileLogService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
* @fileName HongFileServiceImpl
* @author dahyeon
* @version 1.0.0
* @date 2023-07-18
* @summary  (1) saveFile : 파일 업로드 시 동작되는 메서드로, 파일을 저장하고 기본 정보를 저장한다.
 *                         이때 파일 상태는 'PROCESS'로 저장된다.
 *                         -> 후에 게시글 및 다른 정보와 함께 최종 저장될 때 파일 상태는 'SAVED'로 변하게 된다.
 *          (2) updateDownCnt : 해당 파일 downCnt 1 증가
 *          (3) view : 파일 단건 조회
 *          (4) download : 파일 다운로드 -> updateDownCnt, file-log-insert
 *          (5) updateFileState : 파일 최종 저장시, 'PROCESS -> SAVED' 변경
 *          (6) uploadCKImageFile : CKEdtior 이미지 업로드
 *          (7) deleteFiles : 파일 삭제
 *          (8) all : 삭제여부N, 파일 업로드 상태 SAVED인 파일 리스트 전체 조회
**/

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HongFileServiceImpl implements HongFileService {

    private final HongFileRepository hongFileRepository;
    private final HongFileGroupService hongFileGroupService;
    private final HongFileLogService hongFileLogService;

    @Override
    @Transactional(readOnly = false)
    public Map<String, Object> saveFile(MultipartFile multipartFile, Long fileGroupId) {
        Map<String, Object> params = new HashMap<>();

        String fileRoot = "D:/hongFile";
        String originalName = multipartFile.getOriginalFilename();
        String extension = originalName.substring(originalName.lastIndexOf(".")+1);
        UUID uuid = UUID.randomUUID();
        String savedFileName = uuid + "." + extension;
        String filePath = fileRoot + "/" + savedFileName;

        File targetFile = new File(String.format("%s%s%s", fileRoot, File.separator, savedFileName));

        try{
            InputStream fileStream = multipartFile.getInputStream();
            FileUtils.copyInputStreamToFile(fileStream, targetFile);
            params.put("fileUuid", uuid);

            // hong file group save
            HongFileGroup hongFileGroup = null;
            if(fileGroupId != null) hongFileGroup = hongFileGroupService.findFileGroup(fileGroupId);
            else hongFileGroup = hongFileGroupService.saveFileGroup();

            // save file
            HongFile hongFile =  HongFile.hongFileInsertBuilder()
                    .fileUuid(uuid.toString())
                    .hongFileGroup(hongFileGroup)
                    .savedFileName(savedFileName)
                    .originalFileName(originalName)
                    .filePath(filePath)
                    .fileSize(multipartFile.getSize())
                    .fileType(multipartFile.getContentType())
                    .extension(extension)
                    .build();

            HongFile save = hongFileRepository.save(hongFile);

            params.put("id", save.getId());
            params.put("fileState", save.getFileState());
            params.put("fileGroupId", hongFileGroup.getId());

        } catch (IOException e) {
            FileUtils.deleteQuietly(targetFile);
            throw new IllegalArgumentException(e);
        }

        return params;
    }

    @Override
    @Transactional(readOnly = false)
    public void updateDownCnt(Long id) {
        HongFile hongFile = hongFileRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("there is no file"));
        hongFile.updateDownCnt();
    }

    @Override
    public HongFileVO view(Long id) {
        HongFile hongFile = hongFileRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("there is no file"));
        return new HongFileVO(hongFile);
    }

    @Override
    @Transactional(readOnly = false)
    public HongFileVO download(Long id) {
        updateDownCnt(id);              // 1. update donwload count
        hongFileLogService.save(id);    // 2. save log of file
        return view(id);
    }

    @Override
    @Transactional(readOnly = false)
    public void updateFileState(Long fileGroupId) {
        List<HongFile> fileList = hongFileRepository.findAllByHongFileGroupIdAndDeleteYn(fileGroupId, "N");
        for (HongFile hongFile : fileList) {
            hongFile.updateFileState();
        }
    }

    @Override
    public Map<String, Object> uploadCKImageFile(MultipartFile multipartFile) {

        Map<String, Object> params = new HashMap<>();

        String fileRoot = "D:/hongFile/ckImage";
        String originalName = multipartFile.getOriginalFilename();
        String extension = originalName.substring(originalName.lastIndexOf(".")+1);
        UUID uuid = UUID.randomUUID();
        String savedFileName = uuid + "." + extension;
        String filePath = fileRoot + "/" + savedFileName;
        File targetFile = new File(String.format("%s%s%s", fileRoot, File.separator, savedFileName));

        try{
            InputStream fileStream = multipartFile.getInputStream();
            FileUtils.copyInputStreamToFile(fileStream, targetFile);
            params.put("url", "/ckImage/"+savedFileName);
            params.put("responseCode", "success");
        }catch(IOException e){
            FileUtils.deleteQuietly(targetFile);
            params.put("responseCode", "error");
            e.printStackTrace();
        }

        return params;
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteFiles(List<Long>  deleteFile) {
         for (Long fileId : deleteFile) {
            HongFile hongFile = hongFileRepository.findById(fileId).orElseThrow(() -> new IllegalArgumentException("there is no file"));
            hongFile.deleteFile();
        }
    }

    @Override
    public List<HongFileVO> all() {
        List<HongFile> all = hongFileRepository.findAllByDeleteYnAndFileState("N", FileState.SAVED);
        return all.stream().map(HongFileVO::new).toList();
    }
}
