package hongshop.hongshop.domain.review.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class HongReviewDTO {

    private Long hongOrderId;
    private String reviewContent;
    private Integer reviewStar;
    private Long fileGroupId;
    private List<Long> deleteFile = new ArrayList<>();
}
