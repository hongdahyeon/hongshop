package hongshop.hongshop.domain.fileGroup;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/manager")
public class HongFileGroupManagerController {

    private final HongFileGroupService hongFileGroupService;
    @GetMapping("/fileDownload")
    public String fileDownload(Model model){
        return "/manager/fileDownload";
    }
}
