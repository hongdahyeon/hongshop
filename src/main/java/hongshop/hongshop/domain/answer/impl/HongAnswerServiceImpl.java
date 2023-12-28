package hongshop.hongshop.domain.answer.impl;

import hongshop.hongshop.domain.answer.*;
import hongshop.hongshop.domain.answer.dto.HongAnswerDTO;
import hongshop.hongshop.domain.answer.vo.HongAnswerUserVO;
import hongshop.hongshop.domain.answer.vo.HongAnswerVO;
import hongshop.hongshop.domain.post.HongPost;
import hongshop.hongshop.domain.post.HongPostRepository;
import hongshop.hongshop.domain.user.HongUserService;
import hongshop.hongshop.domain.user.vo.HongUserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @fileName HongAnswerServiceImpl
 * @author dahyeon
 * @version 1.0.0
 * @date 2023-07-17
 * @summary     (1) join : 답변 저장
 *              (2) list : 답변 리스트 조회
 *              (3) listByHongPostId : 게시글 Id를 통한 답변 리스트 조회
 *              (4) show : 답변 단건 조회
 *              (5) update : 답변 수정
 *              (6) delete : 답변 삭제
 **/

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HongAnswerServiceImpl implements HongAnswerService {

    private final HongAnswerRepository hongAnswerRepository;
    private final HongPostRepository hongPostRepository;
    private final HongUserService hongUserService;

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
    public List<HongAnswerUserVO> listByHongPostId(Long hongPostId) {
        List<HongAnswer> allByHongPostId = hongAnswerRepository.findAllByHongPostIdAndDeleteYnIs(hongPostId, "N");
        return allByHongPostId.stream().map(hongAnswer -> {
            HongUserVO userVO = hongUserService.getHongUserById(hongAnswer.getRegId());
            return new HongAnswerUserVO(hongAnswer, userVO.getUserId());
        }).toList();
    }

    @Override
    public HongAnswerUserVO show(Long id) {
        HongAnswer hongAnswer = hongAnswerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("there is no post"));
        HongUserVO userVO = hongUserService.getHongUserById(hongAnswer.getRegId());
        return new HongAnswerUserVO(hongAnswer, userVO.getUserId());
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
