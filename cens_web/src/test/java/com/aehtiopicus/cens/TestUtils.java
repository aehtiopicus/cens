package com.aehtiopicus.cens;

import java.lang.reflect.Type;
import java.util.Date;

import com.aehtiopicus.cens.dto.cens.PerfilDto;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class TestUtils {

	public static Gson getSon() {
		GsonBuilder builder = new GsonBuilder();

		builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
			public Date deserialize(JsonElement json, Type typeOfT,
					JsonDeserializationContext context)
					throws JsonParseException {
				return new Date(json.getAsJsonPrimitive().getAsLong());
			}
		});
		
//		builder.registerTypeAdapter(PerfilDto.class, new JsonDeserializer<PerfilDto>() {
//			public PerfilDto deserialize(JsonElement json, Type typeOfT,
//					JsonDeserializationContext context)
//					throws JsonParseException {
//				PerfilDto perfilDto pDto
//				return null;
//			}
//		});

		builder.setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
		return builder.create();
	}
}
