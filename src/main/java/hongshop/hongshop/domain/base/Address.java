package hongshop.hongshop.domain.base;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.Embeddable;

/**
 * @fileName Address
 * @author dahyeon
 * @version 1.0.0
 * @date 2023-07-17
 * @summary  user의 경우 주소(city, street, zipcode) 컬럼을 갖는다.
 *          user 엔티티에서 city, street, zipcode에 대해 각각 컬럼으로 적는 것보다
 *          다음과 같이 address라는 객체로 하나를 묶어서 나타내면 가독성면에서 효율적일 것이다.
 *          - Embeddable : 1개로 표현하고자 하는 클래스에 대해 선언해주는 어노테이션
 *          - Embedded : 해당 클래스를 사용하는 필드에 선언해주는 어노테이션
 **/
@Getter
@Embeddable
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {

    private String city;
    private String street;
    private String zipcode;

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
