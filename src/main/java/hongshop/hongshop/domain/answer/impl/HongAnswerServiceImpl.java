package hongshop.hongshop.domain.answer.impl;

import hongshop.hongshop.domain.answer.*;
import hongshop.hongshop.domain.post.HongPost;
import hongshop.hongshop.domain.post.HongPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @fileName HongAnswerServiceImpl
 * @author dahyeon
 * @version 1.0.0
 * @date 2023-07-17
 * @summary
 **/

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HongAnswerServiceImpl implements HongAnswerService {

    private final HongAnswerRepository hongAnswerRepository;
    private final HongPostRepository hongPostRepository;

    @Override
    @Transactional(readOnly = false)
    public Long join(HongAnswerDTO hongAnswerDTO) {

        HongPost hongPost = hongPostRepository.findById(hongAnswerDTO.getHongPostId()).orElseThrow(() -> new IllegalArgumentException("there is no post"));

        HongAnswer hongAnswer = HongAnswer.hongAnswerInsertBuilder()
                .hongPost(hongPost)
                .content(hongAnswerDTO.getContent())
                .build();

        HongAnswer save = hongAnswerRepository.save(hongAnswer);
        return save.getId();
    }

    @Override
    public List<HongAnswerVO> list() {
        List<HongAnswer> all = hongAnswerRepository.findAll();
        return all.stream().map(HongAnswerVO::new).toList();
    }

    @Override
    public List<HongAnswerVO> listByHongPostId(Long hongPostId) {
        List<HongAnswer> allByHongPostId = hongAnswerRepository.findAllByHongPostId(hongPostId);
        return allByHongPostId.stream().map(HongAnswerVO::new).toList();
    }

    @Override
    public HongAnswerVO show(Long id) {
        HongAnswer hongAnswer = hongAnswerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("there is no post"));
        return new HongAnswerVO(hongAnswer);
    }

    @Override
    @Transactional(readOnly = false)
    public void update(HongAnswerDTO hongAnswerDTO, Long id) {
        HongAnswer hongAnswer = hongAnswerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("there is no post"));
        hongAnswer.updateAnswer(hongAnswerDTO);
    }

    @Override
    @Transactional(readOnly = false)
    public void delete(Long id) {
        HongAnswer hongAnswer = hongAnswerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("there is no post"));
        hongAnswer.deleteAnswer();
    }
}
