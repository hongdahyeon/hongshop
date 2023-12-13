package hongshop.hongshop.global.auth;

import hongshop.hongshop.domain.user.HongUser;
import hongshop.hongshop.global.util.TimeUtil;
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

    @Override   // 계정 만료 여부
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override   // 계정 잠김 여부
    public boolean isAccountNonLocked() {
        return hongUser.getUserNonLocked();
    }

    @Override   // 비밀번호 만료 여부
    public boolean isCredentialsNonExpired() {
        return TimeUtil.dateCompare(hongUser.getPwdEndDate());
    }

    @Override   // 계정 활성화 여부
    public boolean isEnabled() {
        return hongUser.getUserEnable();
    }

    @Override
    public String getName() {
        return null;
    }
}
