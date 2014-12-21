package com.aehtiopicus.cens.dto;

public class ExcelColumnTypeDto {

	protected String type = "text"; //valores posibles: text|numeric|date|checkbox|autocomplete|handsontable
	protected Boolean readOnly = false;
	protected String data;
	protected String format;		//si el tipo es 'numeric' se puede definir un formato en este campo, por ejemplo: $ 0,0.00
	protected String dateFormat;	//si el tipo es 'date' se puede definir un formato en este campo, por ejemplo: dd/mm/yy
	
	protected Boolean showTotal = false;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Boolean getReadOnly() {
		return readOnly;
	}
	public void setReadOnly(Boolean readOnly) {
		this.readOnly = readOnly;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public String getDateFormat() {
		return dateFormat;
	}
	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}
	public Boolean getShowTotal() {
		return showTotal;
	}
	public void setShowTotal(Boolean showTotal) {
		this.showTotal = showTotal;
	}
}
