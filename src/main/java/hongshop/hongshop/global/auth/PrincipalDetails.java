package hongshop.hongshop.global.auth;

import hongshop.hongshop.domain.user.HongUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class PrincipalDetails implements UserDetails, OAuth2User {

    private HongUser hongUser;
    private Map<String, Object> attributes;

    public PrincipalDetails(HongUser hongUser){
        this.hongUser = hongUser;
    }

    public PrincipalDetails(HongUser hongUser, Map<String, Object> attributes){
        this.hongUser = hongUser;
        this.attributes = attributes;
    }

    public HongUser getUser(){
        return hongUser;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(new SimpleGrantedAuthority(hongUser.getRole().toString()));
        return collection;
    }

    @Override
    public String getPassword() {
        return hongUser.getPassword();
    }

    @Override
    public String getUsername() {
        return hongUser.getUserId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getName() {
        return null;
    }
}
