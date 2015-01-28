package com.aehtiopicus.cens.dto.cens;

import java.util.ArrayList;
import java.util.List;

public class ComentarioDescriptionDto {

	private String comment_id;
	private String parent_id;
	private String in_reply_to;
	private String element_id;
	private String created_by;
	private String fullname;
	private String picture;
	private String posted_date;
	private String text;
	private List<CommentarioAttachmentDto> attachments = null;
	private List<ComentarioDescriptionDto> childrens = new ArrayList<ComentarioDescriptionDto>();
	
	public String getComment_id() {
		return comment_id;
	}
	public void setComment_id(String comment_id) {
		this.comment_id = comment_id;
	}
	public String getParent_id() {
		return parent_id;
	}
	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}
	public String getIn_reply_to() {
		return in_reply_to;
	}
	public void setIn_reply_to(String in_reply_to) {
		this.in_reply_to = in_reply_to;
	}
	public String getElement_id() {
		return element_id;
	}
	public void setElement_id(String element_id) {
		this.element_id = element_id;
	}
	public String getCreated_by() {
		return created_by;
	}
	public void setCreated_by(String created_by) {
		this.created_by = created_by;
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
	public String getPosted_date() {
		return posted_date;
	}
	public void setPosted_date(String posted_date) {
		this.posted_date = posted_date;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public List<CommentarioAttachmentDto> getAttachments() {
		return attachments;
	}
	public void setAttachments(List<CommentarioAttachmentDto> attachments) {
		this.attachments = attachments;
	}
	public List<ComentarioDescriptionDto> getChildrens() {
		return childrens;
	}
	public void setChildrens(List<ComentarioDescriptionDto> childrens) {
		this.childrens = childrens;
	}

	

	
}
