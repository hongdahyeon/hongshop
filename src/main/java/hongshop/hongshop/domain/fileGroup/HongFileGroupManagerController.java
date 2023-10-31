package hongshop.hongshop.domain.fileGroup;

import hongshop.hongshop.domain.fileGroup.vo.HongFileGroupVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/manager")
public class HongFileGroupManagerController {

    private final HongFileGroupService hongFileGroupService;
    @GetMapping("/fileDownload")
    public String fileDownload(Model model){
        List<HongFileGroupVO> list = hongFileGroupService.all();
        model.addAttribute("fileGroupList", list);
        return "/manage/fileDownload";
    }
}
