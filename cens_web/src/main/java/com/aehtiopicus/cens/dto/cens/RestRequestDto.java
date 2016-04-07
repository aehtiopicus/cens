package com.aehtiopicus.cens.dto.cens;

import java.util.Map;

public class RestRequestDto {

	
	private int page;
	private int row;
	private String sord;
	private Map<String,String> filters;
	
	
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public String getSord() {
		return sord;
	}
	public void setSord(String sord) {
		this.sord = sord;
	}
	public Map<String, String> getFilters() {
		return filters;
	}
	public void setFilters(Map<String, String> filters) {
		this.filters = filters;
	}
	
	
	
	
	
	
	
}
