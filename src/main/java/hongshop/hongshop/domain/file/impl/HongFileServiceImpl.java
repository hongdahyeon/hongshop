package hongshop.hongshop.domain.file.impl;

import hongshop.hongshop.domain.file.*;
import hongshop.hongshop.domain.fileGroup.HongFileGroup;
import hongshop.hongshop.domain.fileGroup.HongFileGroupRepository;
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
import java.util.Map;
import java.util.UUID;

/**
* @fileName HongFileServiceImpl
* @author dahyeon
* @version 1.0.0
* @date 2023-07-18
* @summary
**/

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HongFileServiceImpl implements HongFileService {

    private final HongFileRepository hongFileRepository;
    private final HongFileGroupRepository hongFileGroupRepository;
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
            if(fileGroupId != null) hongFileGroup = hongFileGroupRepository.findById(10L).get();
            else {
                 HongFileGroup fileGroup = HongFileGroup.hongFileGroupInsertBuilder()
                    .description("")
                    .build();
                hongFileGroup = hongFileGroupRepository.save(fileGroup);
            }

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
            params.put("fileGroup", hongFileGroup.getId());

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
    public HongFileVO download(Long id) {
        updateDownCnt(id);              // 1. update donwload count
        hongFileLogService.save(id);    // 2. save log of file
        return view(id);
    }
}
