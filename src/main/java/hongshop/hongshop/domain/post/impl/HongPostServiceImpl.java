package hongshop.hongshop.domain.post.impl;

import hongshop.hongshop.domain.answer.HongAnswer;
import hongshop.hongshop.domain.answer.HongAnswerRepository;
import hongshop.hongshop.domain.answer.HongAnswerVO;
import hongshop.hongshop.domain.post.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @fileName HongPostServiceImpl
 * @author dahyeon
 * @version 1.0.0
 * @date 2023-07-17
 * @summary
 **/

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class HongPostServiceImpl implements HongPostService {

    private final HongPostRepository hongPostRepository;
    private final HongAnswerRepository hongAnswerRepository;

    @Override
    @Transactional(readOnly = false)
    public Long join(HongPostDTO hongPostDTO) {

        HongPost hongPost = HongPost.hongPostInsertBuilder()
                .title(hongPostDTO.getTitle())
                .content(hongPostDTO.getContent())
                .build();

        HongPost save = hongPostRepository.save(hongPost);
        return save.getId();
    }

    @Override
    public List<HongPostVO> list() {
        List<HongPost> all = hongPostRepository.findAll();
        return all.stream().map(HongPostVO::new).toList();
    }

    @Override
    public HongPostVO postWithAnswer(Long id) {
        HongPost hongPost = hongPostRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("there is no post"));
        List<HongAnswer> allByHongPostId = hongAnswerRepository.findAllByHongPostId(id);
        List<HongAnswerVO> listOfAnswer = allByHongPostId.stream().map(HongAnswerVO::new).toList();
        return new HongPostVO(hongPost, listOfAnswer);
    }

    @Override
    public HongPostVO show(Long id) {
        HongPost hongPost = hongPostRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("there is no post"));
        return new HongPostVO(hongPost);
    }

    @Override
    @Transactional(readOnly = false)
    public void update(HongPostDTO hongPostDTO, Long id) {
        HongPost hongPost = hongPostRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("there is no post"));
        hongPost.updatePost(hongPostDTO);
    }

    @Override
    @Transactional(readOnly = false)
    public void delete(Long id) {
        HongPost hongPost = hongPostRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("there is no post"));
        hongPost.deletePost();
    }
}
