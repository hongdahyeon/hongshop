package hongshop.hongshop.domain.answer;

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

    List<HongAnswerVO> listByHongPostId(Long hongPostId);

    HongAnswerVO show(Long id);

    void update(HongAnswerDTO hongAnswerDTO, Long id);

    void delete(Long id);
}
