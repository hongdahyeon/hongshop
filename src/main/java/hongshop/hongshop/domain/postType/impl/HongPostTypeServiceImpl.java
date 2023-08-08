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

}
