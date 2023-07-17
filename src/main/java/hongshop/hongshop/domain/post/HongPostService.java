package hongshop.hongshop.domain.post;

import hongshop.hongshop.domain.answer.HongAnswerVO;

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

    HongPostVO show(Long id);

    void update(HongPostDTO hongPostDTO, Long id);

    void delete(Long id);

}
