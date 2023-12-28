package hongshop.hongshop.domain.post;

import hongshop.hongshop.domain.post.dto.HongPostDTO;
import hongshop.hongshop.domain.post.vo.HongPostFileAnswerVO;
import hongshop.hongshop.domain.post.vo.HongPostVO;

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

    HongPostFileAnswerVO postWithFileAndAnswer(Long id);

    List<HongPostFileAnswerVO> postsWithFileAnswerByPostType(Long postTypeId);

    void update(HongPostDTO hongPostDTO, Long id);

    void delete(Long id);

    void updateReadCnt(Long id, HttpServletRequest req, HttpServletResponse res);

    List<HongPostVO>  listByHongPostTypeId(Long honPostTypeId);

}
