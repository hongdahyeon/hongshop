package hongshop.hongshop.domain.base;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ImageController {

    @Value("${hong.img.path}")
    private String rootPath;
    @CrossOrigin
    @GetMapping("/image")
    public ResponseEntity<?> returnImage(@RequestParam String imageId) {
        Resource resource = new FileSystemResource(rootPath + imageId);
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }
}
