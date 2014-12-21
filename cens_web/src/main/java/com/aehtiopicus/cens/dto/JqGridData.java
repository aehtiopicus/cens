package com.aehtiopicus.cens.dto;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class JqGridData<T> {
	
	private int total;
	
	private int page;

	private int records;
	
	private List<T> rows;
	
	public JqGridData(int total, int page, int records, List<T> rows) {
		this.total = total;
		this.page = page;
		this.records = records;
		this.rows = rows;
	}
	
	
	public String getJsonString(){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", page);
		map.put("total", total);
		map.put("records", records);
		map.put("rows", rows);
		ObjectMapper mapper = new ObjectMapper();
		String result = null;
		try {
			result = mapper.writeValueAsString(map);
		} catch (Exception e) {
			//TODO ADD LOGER
			
		}
		return result;
	}
	
	public Map<String, Object> getJsonObject(){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", page);
		map.put("total", total);
		map.put("records", records);
		map.put("rows", rows);
//		ObjectMapper mapper = new ObjectMapper();
//		String result = null;
		return map;
		/*try {
			result = mapper.writeValueAsString(map);
		} catch (Exception e) {
			//TODO ADD LOGER
			
		}
		return result;
		*/
	}
}
