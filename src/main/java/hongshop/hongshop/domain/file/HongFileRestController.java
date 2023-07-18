package hongshop.hongshop.domain.file;

import hongshop.hongshop.domain.fileLog.HongFileLogService;
import hongshop.hongshop.global.response.Response;
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
    public Response uploadFile(@RequestParam("file") MultipartFile multipartFile, Long fileGroupId){
        Map<String, Object> map = hongFileService.saveFile(multipartFile, fileGroupId);
        return Response.ok(map);
    }

    @GetMapping("/downloadFile/{id}")
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

}
