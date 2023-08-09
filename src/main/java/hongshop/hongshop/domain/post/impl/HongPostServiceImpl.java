package hongshop.hongshop.domain.post.impl;

import hongshop.hongshop.domain.answer.HongAnswerService;
import hongshop.hongshop.domain.answer.HongAnswerVO;
import hongshop.hongshop.domain.file.HongFileService;
import hongshop.hongshop.domain.fileGroup.HongFileGroupService;
import hongshop.hongshop.domain.fileGroup.HongFileGroupVO;
import hongshop.hongshop.domain.post.*;
import hongshop.hongshop.domain.postType.HongPostType;
import hongshop.hongshop.domain.postType.HongPostTypeRepository;
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
 *   (1) join : 파일그룹ID의 유무에 따라 저장
 *   (2) list : 게시글 전체 리스트 조회
 *   (3) postWithAnswer : 게시글 단건 조회 with 답변
 *   (4) postWithFile : 게시글 단건 조회 with 파일
 *   (5) postWithFileAndAnswer : 게시글 다건 조회 with 파일 and 답변
 *   (5) postsWithFileByPostType : 게시판ID에 따른 게시글 전체 리스트 조회 with 파일
 *   (6) show : 게시글 단건 조회
 *   (7) update : 게시글 단건 update 
 *   (8) delete : 게시글 삭제 -> delete_Yn 변경
 *   (9) updateReadCnt : 조회수 증가
 *   (10) listByHongPostTypeId : 게시판ID에 따른 게시글 전체 리스트 조회
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
    private final HongPostTypeRepository hongPostTypeRepository;

    @Override
    @Transactional(readOnly = false)
    public Long join(HongPostDTO hongPostDTO) {

        HongPostType postType = hongPostTypeRepository.findById(hongPostDTO.getHongPostTypeId()).orElseThrow(() -> new IllegalArgumentException("there is no post type"));

        HongPost hongPost = null;
        if(hongPostDTO.getFileGroupId() == null) {

             hongPost = HongPost.hongPostInsertBuilder()
                    .title(hongPostDTO.getTitle())
                    .content(hongPostDTO.getContent())
                     .hongPostType(postType)
                    .build();
        }else {
            hongFileService.updateFileState(hongPostDTO.getFileGroupId());
            hongPost = HongPost.hongPostInsertBuilder()
                    .title(hongPostDTO.getTitle())
                    .content(hongPostDTO.getContent())
                    .fileGroupId(hongPostDTO.getFileGroupId())
                    .hongPostType(postType)
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
    public HongPostVO postWithFileAndAnswer(Long id) {
        HongPost hongPost = hongPostRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("there is no post"));
        List<HongAnswerVO> listOfAnswer = hongAnswerService.listByHongPostId(id);
        HongFileGroupVO list = null;
        if(hongPost.getFileGroupId() != null){
             list = hongFileGroupService.list(hongPost.getFileGroupId());
        }

        return new HongPostVO(hongPost, list, listOfAnswer);
    }


    @Override
    public List<HongPostVO> postsWithFileByPostType(Long postTypeId) {
        List<HongPost> hongPosts = hongPostRepository.findAllByHongPostTypeId(postTypeId);
        return hongPosts.stream().map(post -> {
            HongFileGroupVO list = null;
            if(post.getFileGroupId() != null) list = hongFileGroupService.list(post.getFileGroupId());
            return new HongPostVO(post, list);
        }).toList();
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

    @Override
    public List<HongPostVO> listByHongPostTypeId(Long honPostTypeId) {
        List<HongPost> hongPosts = hongPostRepository.findAllByHongPostTypeId(honPostTypeId);
        return hongPosts.stream().map(HongPostVO::new).toList();
    }
}
