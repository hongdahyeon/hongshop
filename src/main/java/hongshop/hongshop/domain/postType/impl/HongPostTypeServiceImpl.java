package hongshop.hongshop.domain.postType.impl;

import hongshop.hongshop.domain.post.HongPostService;
import hongshop.hongshop.domain.post.vo.HongPostVO;
import hongshop.hongshop.domain.postType.*;
import hongshop.hongshop.domain.postType.dto.HongPostTypeDTO;
import hongshop.hongshop.domain.postType.vo.HongPostTypeVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* @fileName HongPostTypeServiceImpl
* @author dahyeon
* @version 1.0.0
* @date 2023-08-08
* @summary
 *  (1) list : 전체 게시글 타입 리스트 -> 삭제여부 / 정렬
 *  (2) listForHeader : 전체 게시글 타입 리스트 FOR 헤더 -> 사용여부, 삭제여부 / 정렬
 *  (3) listWithPost : 전체 게시글 타입 리스트 with 게시글
 *  (4) join : 게시글 타입 저장
 *  (5) view : 게시글 타입 단건 조회
 *  (6) update : 게시글 타입 수정
 *  (7) delete : 게시글 타입 삭제
 *  (8) getQnaPost : 질의응답 게시글 타입 가져오기
**/

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class HongPostTypeServiceImpl implements HongPostTypeService {

    private final HongPostTypeRepository hongPostTypeRepository;
    private final HongPostService hongPostService;

    @Override
    public List<HongPostTypeVO> list() {
        List<HongPostType> all = hongPostTypeRepository.findAllByDeleteAtOrderByOrderNum("N");
        return all.stream().map(HongPostTypeVO::new).toList();
    }

    @Override
    public List<HongPostTypeVO> listForHeader() {
        List<HongPostType> all = hongPostTypeRepository.findAllByDeleteAtAndUseAtOrderByOrderNum("N", "Y");
        return all.stream().map(HongPostTypeVO::new).toList();
    }

    @Override
    public List<HongPostTypeVO> listWithPost() {
        List<HongPostType> all = hongPostTypeRepository.findAllByDeleteAtOrderByOrderNum("N");
        return all.stream().map(type -> {
            List<HongPostVO> hongPostVOS = hongPostService.listByHongPostTypeId(type.getId());
            return new HongPostTypeVO(type, hongPostVOS);
        }).toList();
    }

    @Override
    @Transactional(readOnly = false)
    public Long join(HongPostTypeDTO hongPostTypeDTO) {
        HongPostType hongPostType = HongPostType.insertPostTypeBuilder()
                .postType(hongPostTypeDTO.getPostType())
                .postName(hongPostTypeDTO.getPostName())
                .useAt(hongPostTypeDTO.getUseAt())
                .orderNum(hongPostTypeDTO.getOrderNum())
                .build();

        HongPostType save = hongPostTypeRepository.save(hongPostType);

        // if useAt is Y ... make url
        if("Y".equals(hongPostTypeDTO.getUseAt())) {
            // update
            String postUrl = "/bbs/" + save.getId();
            save.updatePostUrl(postUrl);
        }
        return save.getId();
    }

    @Override
    public HongPostTypeVO view(Long id) {
        HongPostType hongPostType = hongPostTypeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("there is no type"));
        return new HongPostTypeVO(hongPostType);
    }

    @Override
    @Transactional(readOnly = false)
    public void update(Long id, HongPostTypeDTO hongPostTypeDTO) {
        HongPostType hongPostType = hongPostTypeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("there is no type"));
        hongPostType.updatePostType(hongPostTypeDTO, id);
    }

    @Override
    @Transactional(readOnly = false)
    public void delete(Long id) {
        HongPostType hongPostType = hongPostTypeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("there is no type"));
        hongPostType.deletePostType();
    }

    @Override
    public HongPostTypeVO getQnaPost() {
        List<HongPostType> all = hongPostTypeRepository.findAllByDeleteAtAndUseAtAndPostType("N", "Y", PostType.QNA);
        if(all.size()>= 1) {
            HongPostType hongPostType = all.get(0);
            return new HongPostTypeVO(hongPostType);
        }else return null;
    }
}
