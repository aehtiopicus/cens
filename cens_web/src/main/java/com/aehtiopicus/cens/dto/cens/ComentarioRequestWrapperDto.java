package com.aehtiopicus.cens.dto.cens;

import com.aehtiopicus.cens.util.Utils;

public class ComentarioRequestWrapperDto {
	private ComentarioRequestDto dto;
	
	
	
	public ComentarioRequestWrapperDto (String data){		
			dto = Utils.getSon().fromJson(data, ComentarioRequestDto.class);		
	}
	
	public ComentarioRequestDto getDto(){
		return dto;
	}
}
