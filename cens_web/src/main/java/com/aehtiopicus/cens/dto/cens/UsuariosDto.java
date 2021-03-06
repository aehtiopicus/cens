package com.aehtiopicus.cens.dto.cens;

import java.util.List;

public class UsuariosDto {

	protected Long id;

	protected String username;
	protected String password;
	protected String passwordConfirm;
	private String avatarImg;
	protected Boolean enabled = true;		
	private List<PerfilDto> perfil ;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	public List<PerfilDto> getPerfil() {
		return perfil;
	}
	public void setPerfil(List<PerfilDto> perfil) {
		this.perfil = perfil;
	}
	public String getPasswordConfirm() {
		return passwordConfirm;
	}
	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}
	public String getAvatarImg() {
		return avatarImg;
	}
	public void setAvatarImg(String avatarImg) {
		this.avatarImg = avatarImg;
	}
	
	
}
