package hongshop.hongshop.domain.post.impl;

import hongshop.hongshop.domain.answer.HongAnswerService;
import hongshop.hongshop.domain.answer.vo.HongAnswerUserVO;
import hongshop.hongshop.domain.file.FileState;
import hongshop.hongshop.domain.file.HongFileService;
import hongshop.hongshop.domain.fileGroup.HongFileGroupService;
import hongshop.hongshop.domain.fileGroup.vo.HongFileGroupVO;
import hongshop.hongshop.domain.post.HongPost;
import hongshop.hongshop.domain.post.HongPostRepository;
import hongshop.hongshop.domain.post.HongPostService;
import hongshop.hongshop.domain.post.dto.HongPostDTO;
import hongshop.hongshop.domain.post.vo.HongPostFileAnswerVO;
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
 *   (2) postWithFileAndAnswer : 게시글 단건 조회 with 파일 and 답변
 *   (3) postsWithFileAnswerByPostType : 게시판 ID에 따른 게시글 전체 리스트 조회 with 파일 and 답변
 *   (4) update : 게시글 단건 update
 *   (5) delete : 게시글 삭제 -> delete_Yn 변경
 *   (6) updateReadCnt : 조회수 증가
 *   (7) listByHongPostTypeId : 게시판ID에 따른 게시글 전체 리스트 조회
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
    public HongPostFileAnswerVO postWithFileAndAnswer(Long id) {
        HongPost hongPost = hongPostRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("there is no post"));
        List<HongAnswerUserVO> listOfAnswer = hongAnswerService.listByHongPostId(id);
        HongFileGroupVO list = null;
        if(hongPost.getFileGroupId() != null){
             list = hongFileGroupService.listWithDeleteYnAndFileState(hongPost.getFileGroupId(), "N", FileState.SAVED);
        }
        return new HongPostFileAnswerVO(hongPost, list, listOfAnswer);
    }


    @Override
    public List<HongPostFileAnswerVO> postsWithFileAnswerByPostType(Long postTypeId) {
        List<HongPost> hongPosts = hongPostRepository.findAllByHongPostTypeIdAndDeleteYnIs(postTypeId, "N");
        return hongPosts.stream().map(post -> {
            HongFileGroupVO list = null;
            if(post.getFileGroupId() != null) list = hongFileGroupService.listWithDeleteYnAndFileState(post.getFileGroupId(), "N", FileState.SAVED);
            List<HongAnswerUserVO> hongAnswerVOS = hongAnswerService.listByHongPostId(post.getId());
            return new HongPostFileAnswerVO(post, list, hongAnswerVOS);
        }).toList();
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
