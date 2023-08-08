package hongshop.hongshop.domain.postType.impl;

import hongshop.hongshop.domain.post.HongPostService;
import hongshop.hongshop.domain.post.HongPostVO;
import hongshop.hongshop.domain.postType.*;
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
 *  (1) list : 전체 게시글 타입 리스트
 *  (2) listWithPost : 전체 게시글 타입 리스트 with 게시글
 *  (3) join : 게시글 타입 저장
 *  (4) view : 게시글 타입 단건 조회
**/

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class HongPostTypeServiceImpl implements HongPostTypeService {

    private final HongPostTypeRepository hongPostTypeRepository;
    private final HongPostService hongPostService;

    @Override
    public List<HongPostTypeVO> list() {
        List<HongPostType> all = hongPostTypeRepository.findAll();
        return all.stream().map(HongPostTypeVO::new).toList();
    }

    @Override
    public List<HongPostTypeVO> listWithPost() {
        List<HongPostType> all = hongPostTypeRepository.findAll();
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
                .build();

        HongPostType save = hongPostTypeRepository.save(hongPostType);
        return save.getId();
    }

    @Override
    public HongPostTypeVO view(Long id) {
        HongPostType hongPostType = hongPostTypeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("there is no type"));
        return new HongPostTypeVO(hongPostType);
    }

}
