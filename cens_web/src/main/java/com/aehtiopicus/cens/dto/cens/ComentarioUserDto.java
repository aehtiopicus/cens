package com.aehtiopicus.cens.dto.cens;

public class ComentarioUserDto {

	private Long user_id;
	private String fullname;
	private String picture;
	private boolean is_logged_in = false;
	private boolean is_add_allowed;
	private boolean is_edit_allowed;
	
	public Long getUser_id() {
		return user_id;
	}
	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public boolean isIs_logged_in() {
		return is_logged_in;
	}
	public void setIs_logged_in(boolean is_logged_in) {
		this.is_logged_in = is_logged_in;
	}
	public boolean isIs_add_allowed() {
		return is_add_allowed;
	}
	public void setIs_add_allowed(boolean is_add_allowed) {
		this.is_add_allowed = is_add_allowed;
	}
	public boolean isIs_edit_allowed() {
		return is_edit_allowed;
	}
	public void setIs_edit_allowed(boolean is_edit_allowed) {
		this.is_edit_allowed = is_edit_allowed;
	}

	
}
