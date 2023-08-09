package hongshop.hongshop.domain.user;

import hongshop.hongshop.domain.base.Address;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @fileName HongUser
 * @author dahyeon
 * @version 1.0.0
 * @date 2023-07-17
 * @summary  'hong_user' 엔티티
 **/

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

    public void updateHongUser(HongUserDTO hongUserDTO){
        if(hongUserDTO.getPassword() != null) this.password = hongUserDTO.getPassword();
//        this.role = hongUserDTO.getRole();
        this.address = new Address(hongUserDTO.getCity(), hongUserDTO.getStreet(), hongUserDTO.getZipcode());
    }
}
