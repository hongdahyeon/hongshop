package hongshop.hongshop.domain.post;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @fileName HongPostService
 * @author dahyeon
 * @version 1.0.0
 * @date 2023-07-17
 * @summary
 **/

public interface HongPostService {

    Long join(HongPostDTO hongPostDTO);

    List<HongPostVO> list();

    HongPostVO postWithAnswer(Long id);

    HongPostVO postWithFile(Long id);

    List<HongPostVO> postsWithFileByPostType(Long postTypeId);

    HongPostVO show(Long id);

    void update(HongPostDTO hongPostDTO, Long id);

    void delete(Long id);

    void updateReadCnt(Long id, HttpServletRequest req, HttpServletResponse res);

    List<HongPostVO>  listByHongPostTypeId(Long honPostTypeId);

}
