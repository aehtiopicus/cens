package com.aehtiopicus.cens.dto.cens;

public class NotificacionConfigDto {

	private int expireSec;
	private String user;
	private Long miembroId;
	private Long userId;
	
	
	public int getExpireSec() {
		return expireSec;
	}
	public void setExpireSec(int expireSec) {
		this.expireSec = expireSec;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public Long getMiembroId() {
		return miembroId;
	}
	public void setMiembroId(Long miembroId) {
		this.miembroId = miembroId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	
	
}
