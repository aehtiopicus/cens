package com.aehtiopicus.cens.enumeration.cens;

public enum SocialType {

	FACEBOOK("facebook");
	
	private String social;

	public String getSocial() {
		return social;
	}

	private SocialType(String social) {
		this.social = social;
	}

	
	
}
