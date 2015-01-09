package com.aehtiopicus.cens.dto.cens;

import java.util.List;

public class RestRequestDto {

	private int page;
	private int row;
	private String sord;
	private List<FilterDto> filters;
	
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
	public List<FilterDto> getFilters() {
		return filters;
	}
	public void setFilters(List<FilterDto> filters) {
		this.filters = filters;
	}
	
	
	
	
}
