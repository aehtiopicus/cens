package com.aehtiopicus.cens.dto.cens;

public class SocialPostDto {

	private Long id;
	private String publishEventId;
	private String provider;
	private ProgramaDto programa;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPublishEventId() {
		return publishEventId;
	}
	public void setPublishEventId(String publishEventId) {
		this.publishEventId = publishEventId;
	}
	public String getProvider() {
		return provider;
	}
	public void setProvider(String provider) {
		this.provider = provider;
	}
	public ProgramaDto getPrograma() {
		return programa;
	}
	public void setPrograma(ProgramaDto programa) {
		this.programa = programa;
	}
	
	
	
}
