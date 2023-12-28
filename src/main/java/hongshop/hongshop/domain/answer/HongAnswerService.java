package hongshop.hongshop.domain.answer;

import hongshop.hongshop.domain.answer.dto.HongAnswerDTO;
import hongshop.hongshop.domain.answer.vo.HongAnswerUserVO;
import hongshop.hongshop.domain.answer.vo.HongAnswerVO;

import java.util.List;

/**
 * @fileName HongAnswerService
 * @author dahyeon
 * @version 1.0.0
 * @date 2023-07-17
 * @summary
 **/

public interface HongAnswerService {

    Long join(HongAnswerDTO hongAnswerDTO);

    List<HongAnswerVO> list();

    List<HongAnswerUserVO> listByHongPostId(Long hongPostId);

    HongAnswerUserVO show(Long id);

    void update(HongAnswerDTO hongAnswerDTO, Long id);

    void delete(Long id);
}
