package com.aehtiopicus.cens.dto.cens;

import com.aehtiopicus.cens.util.Utils;

public class RestRequestDtoWrapper {

	private RestRequestDto dto;
	
	
	
	public RestRequestDtoWrapper (String data){		
			dto = Utils.getSon().fromJson(data, RestRequestDto.class);		
	}
	
	public RestRequestDto getDto(){
		return dto;
	}
}
