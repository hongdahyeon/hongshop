package hongshop.hongshop.domain.postType;

import java.util.List;

/**
* @fileName HongPostTypeService
* @author dahyeon
* @version 1.0.0
* @date 2023-08-08
* @summary 
 *  - list : 전체 게시글 타입 리스트
 *  - listWithPost : 전체 게시글 타입 리스트 with 게시글
 *  - join : 게시글 타입 저장
**/

public interface HongPostTypeService {

    List<HongPostTypeVO> list();

    List<HongPostTypeVO> listWithPost();

    Long join(HongPostTypeDTO hongPostTypeDTO);

}
