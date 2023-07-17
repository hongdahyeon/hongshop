package hongshop.hongshop.domain.answer;

import java.util.List;

public interface HongAnswerService {

    Long join(HongAnswerDTO hongAnswerDTO);

    List<HongAnswerVO> list();

    List<HongAnswerVO> listByHongPostId(Long hongPostId);

    HongAnswerVO show(Long id);

    void update(HongAnswerDTO hongAnswerDTO, Long id);

    void delete(Long id);
}
