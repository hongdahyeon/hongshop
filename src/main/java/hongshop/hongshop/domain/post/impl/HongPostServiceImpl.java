package hongshop.hongshop.domain.post.impl;

import hongshop.hongshop.domain.answer.HongAnswerService;
import hongshop.hongshop.domain.answer.vo.HongAnswerVO;
import hongshop.hongshop.domain.file.FileState;
import hongshop.hongshop.domain.file.HongFileService;
import hongshop.hongshop.domain.fileGroup.HongFileGroupService;
import hongshop.hongshop.domain.fileGroup.vo.HongFileGroupVO;
import hongshop.hongshop.domain.post.*;
import hongshop.hongshop.domain.post.dto.HongPostDTO;
import hongshop.hongshop.domain.post.vo.HongPostAnswerVO;
import hongshop.hongshop.domain.post.vo.HongPostFileAnswerVO;
import hongshop.hongshop.domain.post.vo.HongPostFileVO;
import hongshop.hongshop.domain.post.vo.HongPostVO;
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
 *   (5) postWithFileAndAnswer : 게시글 단건 조회 with 파일 and 답변
 *   (6) postsWithFileAnswerByPostType : 게시판 ID에 따른 게시글 전체 리스트 조회 with 파일 and 답변
 *   (7) show : 게시글 단건 조회
 *   (8) update : 게시글 단건 update
 *   (9) delete : 게시글 삭제 -> delete_Yn 변경
 *   (10) updateReadCnt : 조회수 증가
 *   (11) listByHongPostTypeId : 게시판ID에 따른 게시글 전체 리스트 조회
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

        // 1. delete file from list : deleteFile
        if(hongPostDTO.getDeleteFile().size() != 0){
            hongFileService.deleteFiles(hongPostDTO.getDeleteFile());
        }

        // 2. get post type
        HongPostType postType = hongPostTypeRepository.findById(hongPostDTO.getHongPostTypeId()).orElseThrow(() -> new IllegalArgumentException("there is no post type"));

        // 3-1. if fileGroupId is null -> just save it
        // 3-2. if fileGroupId is not null -> first change the file state and then save it
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
    public HongPostAnswerVO postWithAnswer(Long id) {
        HongPost hongPost = hongPostRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("there is no post"));
        List<HongAnswerVO> listOfAnswer = hongAnswerService.listByHongPostId(id);
        return new HongPostAnswerVO(hongPost, listOfAnswer);
    }

    @Override
    public HongPostFileVO postWithFile(Long id) {
        HongPost hongPost = hongPostRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("there is no post"));
        HongFileGroupVO list = hongFileGroupService.listwithDeleteYnAndFileState(hongPost.getFileGroupId(), "N", FileState.SAVED);
        return new HongPostFileVO(hongPost, list);
    }

    @Override
    public HongPostFileAnswerVO postWithFileAndAnswer(Long id) {
        HongPost hongPost = hongPostRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("there is no post"));
        List<HongAnswerVO> listOfAnswer = hongAnswerService.listByHongPostId(id);
        HongFileGroupVO list = null;
        if(hongPost.getFileGroupId() != null){
             list = hongFileGroupService.listwithDeleteYnAndFileState(hongPost.getFileGroupId(), "N", FileState.SAVED);
        }
        return new HongPostFileAnswerVO(hongPost, list, listOfAnswer);
    }


    @Override
    public List<HongPostFileAnswerVO> postsWithFileAnswerByPostType(Long postTypeId) {
        List<HongPost> hongPosts = hongPostRepository.findAllByHongPostTypeIdAndDeleteYnIs(postTypeId, "N");
        return hongPosts.stream().map(post -> {
            HongFileGroupVO list = null;
            if(post.getFileGroupId() != null) list = hongFileGroupService.listwithDeleteYnAndFileState(post.getFileGroupId(), "N", FileState.SAVED);
            List<HongAnswerVO> hongAnswerVOS = hongAnswerService.listByHongPostId(post.getId());
            return new HongPostFileAnswerVO(post, list, hongAnswerVOS);
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

        if(hongPostDTO.getDeleteFile().size() != 0){
            hongFileService.deleteFiles(hongPostDTO.getDeleteFile());
        }

        if(hongPostDTO.getFileGroupId() != null) hongFileService.updateFileState(hongPostDTO.getFileGroupId());
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
        List<HongPost> hongPosts = hongPostRepository.findAllByHongPostTypeIdAndDeleteYnIs(honPostTypeId, "N");
        return hongPosts.stream().map(HongPostVO::new).toList();
    }
}
