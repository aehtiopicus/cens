package com.aehtiopicus.cens.dto;

public class ComboDto implements Comparable<ComboDto>{
	
	protected Long id;
	protected String value;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	@Override
	public int compareTo(ComboDto o) {
		return this.getValue().compareTo(o.getValue());
	}
}
