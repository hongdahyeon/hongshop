package hongshop.hongshop.domain.base;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class HomeController {

    @GetMapping("/login")
    public String login(){
        return "/login";
    }

    @GetMapping({"/", ""})
    public String index(){
        return "index";
    }

    @GetMapping("/front/initialPassword")
    public String initialPassword(){
        return "initialPassword";
    }

    @CrossOrigin
    @GetMapping("/image")
    public ResponseEntity<?> returnImage(@RequestParam String imageName) {
        String path = "D:\\hongFile\\";
        Resource resource = new FileSystemResource(path + imageName);
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }
}
