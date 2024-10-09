package com.cos.springsecurity.config.auth;

// 시큐리티가 /login 주소 요청이 오면 낚아채서 로그인을 진행시킴.
// 로그인이 완료되면 시큐리티 session을 만듦. (Security ContextHolder)
// 시큐리티 세션에 들어갈 수 있는 오브젝트는 Authentication 객체여야만 함.
// Authentication 안에 User 정보가 있음.
// User 정보를 저장하는 오브젝트의 타입은 UserDetails 타입임.

import com.cos.springsecurity.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

// Security Session 내부에 Authentication 객체, Authentication 내부에 UserDetails 타입이 저장.
// Security Session => Authentication => UserDetails(PrincipalDetails와 같은 타입임)
// 결과적으로, PrincipalDetails 객체를 Authentication 내부에 넣을 수 있다.
public class PrincipalDetails implements UserDetails {

    private User user;

    public PrincipalDetails(User user) {
        this.user = user;
    }

    // 해당 유저의 권한을 리턴하는 곳
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add((GrantedAuthority) () -> user.getRole());
        return collect;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
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

    // 예를 들어 회원이 1년 동안 로그인을 안한 경우 -> 휴면 계정
    @Override
    public boolean isEnabled() {
        return true;
    }
}
