package hongshop.hongshop.domain.file;

import hongshop.hongshop.domain.file.vo.HongFileVO;
import hongshop.hongshop.global.response.ApiDocumentResponse;
import hongshop.hongshop.global.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

/**
* @fileName HongFileRestController
* @author dahyeon
* @version 1.0.0
* @date 2023-07-18
* @summary
**/

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "hong file rest controller", description = "파일 Rest 컨트롤러")
public class HongFileRestController {

    private final HongFileService hongFileService;

    @PostMapping(value = "/uploadFile", produces = "application/json")
    @Operation(summary = "upload file", description = "파일 업로드")
    @ApiDocumentResponse
    public Response uploadFile(@RequestParam("file") MultipartFile multipartFile, Long fileGroupId){
        Map<String, Object> map = hongFileService.saveFile(multipartFile, fileGroupId);
        return Response.ok(map);
    }

    @GetMapping("/downloadFile/{id}")
    @Operation(summary = "download file", description = "파일 다운로드")
    @ApiDocumentResponse
    public ResponseEntity<ByteArrayResource> download(@PathVariable Long id) throws IOException {
        HongFileVO view = hongFileService.download(id);
        File tempFile = Paths.get(view.getFilePath()).toFile();

        ByteArrayResource resource = null;
        try{
            resource = new ByteArrayResource(Files.readAllBytes(tempFile.toPath()));
        }catch(IOException e){
            throw new IllegalArgumentException("download error");
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentDisposition(ContentDisposition
                .builder("attachment")
                .filename(view.getOriginalFileName(), StandardCharsets.UTF_8)
                .build());

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(tempFile.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }


    @PostMapping(value = "/uploadCKImageFile", produces="application/json")
    @Operation(summary = "upload ckImage", description = "ckeditor로 이미지 업로드")
    @ApiDocumentResponse
    public Map<String, Object> uploadCKImageFile(@RequestParam("file") MultipartFile multipartFile){
        System.out.println("multipartFile = " + multipartFile);
        Map<String, Object> params = hongFileService.uploadCKImageFile(multipartFile);
        return params;
    }
}
