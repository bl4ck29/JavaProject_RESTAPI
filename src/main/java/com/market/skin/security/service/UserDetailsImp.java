package com.market.skin.security.service;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.market.skin.model.Users;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserDetailsImp  implements UserDetails{
    private static final long serialVersionUID = 1L;
	private Long id;
	private String username;
	private String email;
	private String profile;
	private String login_type;
	@JsonIgnore
	private String password;
	private Collection<? extends GrantedAuthority> authorities;

	public UserDetailsImp(Long id, String username, String email, String password, String profile, String login_type,
			Collection<? extends GrantedAuthority> authorities) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.profile = profile;
		this.login_type = login_type;
		this.authorities = authorities;
	}

	public static UserDetailsImp build(Users user) {
		List<GrantedAuthority> authorities = user.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getRoleName().name()))
				.collect(Collectors.toList());
		return new UserDetailsImp(user.getId(), user.getUserName(), user.getEmail(), user.getPassword(), user.getProfile(), user.getLoginType(),  authorities);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public Long getId(){return id;}
	public String getEmail(){return email;}
	public String getLoginType(){return this.login_type;}
	public String getProfile(){return this.profile;}
	@Override
	public String getPassword(){return password;}
	@Override
	public String getUsername(){return username;}
	@Override
	public boolean isAccountNonExpired(){return true;}
	@Override
	public boolean isAccountNonLocked(){return true;}
	@Override
	public boolean isCredentialsNonExpired(){return true;}
	@Override
	public boolean isEnabled(){return true;}

	@Override
	public boolean equals(Object other) {
		if (this == other)
			return true;
		if (other == null || getClass() != other.getClass())
			return false;
		UserDetailsImp user = (UserDetailsImp) other;
		return Objects.equals(id, user.id);
	}
}