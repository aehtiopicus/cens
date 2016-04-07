package com.aehtiopicus.cens.dto.cens;

import java.util.ArrayList;
import java.util.List;

public class ComentarioDto {
	
	private int total_comment;
	private ComentarioUserDto user = new ComentarioUserDto();
	private List<ComentarioDescriptionDto> comments = new ArrayList<ComentarioDescriptionDto>();
	
	public int getTotal_comment() {
		return total_comment;
	}
	public void setTotal_comment(int total_comment) {
		this.total_comment = total_comment;
	}
	public ComentarioUserDto getUser() {
		return user;
	}
	public void setUser(ComentarioUserDto user) {
		this.user = user;
	}
	public List<ComentarioDescriptionDto> getComments() {
		return comments;
	}
	public void setComments(List<ComentarioDescriptionDto> comments) {
		this.comments = comments;
	}
	
	
}
