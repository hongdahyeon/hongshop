package hongshop.hongshop.domain.post.impl;

import hongshop.hongshop.domain.answer.HongAnswerService;
import hongshop.hongshop.domain.answer.HongAnswerVO;
import hongshop.hongshop.domain.file.HongFileService;
import hongshop.hongshop.domain.fileGroup.HongFileGroupService;
import hongshop.hongshop.domain.fileGroup.HongFileGroupVO;
import hongshop.hongshop.domain.post.*;
import hongshop.hongshop.global.util.CookieUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    private final HongAnswerService hongAnswerService;
    private final HongFileService hongFileService;
    private final HongFileGroupService hongFileGroupService;

    @Override
    @Transactional(readOnly = false)
    public Long join(HongPostDTO hongPostDTO) {

        HongPost hongPost = null;
        if(hongPostDTO.getFileGroupId() == null) {

             hongPost = HongPost.hongPostInsertBuilder()
                    .title(hongPostDTO.getTitle())
                    .content(hongPostDTO.getContent())
                    .build();
        }else {
            hongFileService.updateFileState(hongPostDTO.getFileGroupId());
            hongPost = HongPost.hongPostInsertBuilder()
                    .title(hongPostDTO.getTitle())
                    .content(hongPostDTO.getContent())
                    .fileGroupId(hongPostDTO.getFileGroupId())
                    .build();
        }
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
        List<HongAnswerVO> listOfAnswer = hongAnswerService.listByHongPostId(id);
        return new HongPostVO(hongPost, listOfAnswer);
    }

    @Override
    public HongPostVO postWithFile(Long id) {
        HongPost hongPost = hongPostRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("there is no post"));
        HongFileGroupVO list = hongFileGroupService.list(hongPost.getFileGroupId());
        return new HongPostVO(hongPost, list);
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

    @Override
    @Transactional(readOnly = false)
    public void updateReadCnt(Long id, HttpServletRequest req, HttpServletResponse res) {
        HongPost hongPost = hongPostRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("there is no post"));
        String cookie = CookieUtil.getCookie(req, id.toString());
        if(cookie == null){
            CookieUtil.createCookie(res, id.toString(), String.format("[%s]", id), 60);
            hongPost.updateReadCnt();
        }
    }
}
