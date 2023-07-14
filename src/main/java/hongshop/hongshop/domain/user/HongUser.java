package hongshop.hongshop.domain.user;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @Column(name="user_id")
    private String userId;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private HongRoleType role;

    @Column(name = "address")
    @Embedded
    private Address address;


    @Builder(builderMethodName = "hongUserInsertBuilder")
    public HongUser(String userId, String password, HongRoleType role, Address address){
        this.userId = userId;
        this.password = password;
        this.role = role;
        this.address = address;
    }
}
