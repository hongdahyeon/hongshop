package hongshop.hongshop.domain.deliver;

import hongshop.hongshop.domain.base.Address;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class HongDeliverDTO {
    private String city;
    private String street;
    private String zipcode;

    public HongDeliverDTO(Address address) {
        this.city = address.getCity();
        this.street = address.getStreet();
        this.zipcode = address.getZipcode();
    }
}
