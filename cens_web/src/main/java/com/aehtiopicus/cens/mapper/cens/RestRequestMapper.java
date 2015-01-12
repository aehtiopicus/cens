package com.aehtiopicus.cens.mapper.cens;

import org.springframework.stereotype.Component;

import com.aehtiopicus.cens.domain.entities.RestRequest;
import com.aehtiopicus.cens.dto.cens.RestRequestDto;
import com.aehtiopicus.cens.util.Utils;

@Component
public class RestRequestMapper {

	public RestRequest convertRestRequestDto(RestRequestDto dto){
		
		return Utils.getMapper().map(dto, RestRequest.class);
	}
}
