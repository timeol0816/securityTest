package com.cos.security1.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cos.security1.model.User;

import lombok.Data;

// 시큐리티가 /login 주소 요청이 오면 낚아채서 로그인을 진행한다.
// 로그인을 진행이 완료가 되면 시큐리티 session을 만들어줍니다.(Security ContextHolder)
// 오브젝트 => Authentication 타입 객체가 session에 들어감 
// Authentication 안에 User정보가 있어야함
// User오브젝트타입 => UserDetails 타입 객체

// 정의를 해보면 
// Security Session -> Authentication -> UserDetails

@Data
public class PrincipalDetails implements UserDetails{
	
	private User user;
	
	public PrincipalDetails(User user) {
		this.user = user;
	}
	
	// 해당 User의 권한을 리턴하는 곳!
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		//user.getRol() -> String의 타입이라 바로사용 불가능
		Collection<GrantedAuthority> collect = new ArrayList<>();
		collect.add(new GrantedAuthority() {
			
			@Override
			public String getAuthority() {
				return user.getRole();
			}
		});
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

	@Override
	public boolean isEnabled() {
		
		// 사이트에서 1년동안 회원이 로그인을 안하면 ! -> 휴먼계정으로 하기로 할때 
		// 현재시간 - 로긴시간 => 1년을 초과하면 return false; -> 예제임
		
		
		return true;
	}
	
	

}
