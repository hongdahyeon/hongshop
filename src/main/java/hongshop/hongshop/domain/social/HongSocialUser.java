package hongshop.hongshop.domain.social;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "hong_social_user")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HongSocialUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hong_social_user_id")
    private Long id;

    @Column(name = "user_id")
    private String userId;

    @Builder(builderMethodName = "hongSocialUserInsertBuilder")
    public HongSocialUser(String userId) {
        this.userId = userId;
    }

}
