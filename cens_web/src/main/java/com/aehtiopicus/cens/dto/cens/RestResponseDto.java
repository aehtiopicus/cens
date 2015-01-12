package com.aehtiopicus.cens.dto.cens;

import java.util.List;

public class RestResponseDto<T> {
	
	private int page;
	private int row;
	private String sord;
	private long cantidad;
	private List<T> response;
	
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
	public long getCantidad() {
		return cantidad;
	}
	public void setCantidad(long cantidad) {
		this.cantidad = cantidad;
	}
	public List<T> getResponse() {
		return response;
	}
	public void setResponse(List<T> response) {
		this.response = response;
	}
	
	
	
}
