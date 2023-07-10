package hongshop.hongshop.domain.user;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="hong_user")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HongUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hong_user_id")
    private Long id;

    @Column(name="userId")
    private String userId;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private HongUserRole role;


    @Builder(builderMethodName = "hongUserInsertBuilder")
    public HongUser(String userId, String password, HongUserRole role){
        this.userId = userId;
        this.password = password;
        this.role = role;
    }
}
