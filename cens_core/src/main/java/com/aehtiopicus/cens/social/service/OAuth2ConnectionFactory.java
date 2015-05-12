package com.aehtiopicus.cens.social.service;

import org.springframework.social.ServiceProvider;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Operations;

import com.aehtiopicus.cens.social.SocialConnection;

public class OAuth2ConnectionFactory<A> extends ConnectionFactory<A> {

    public OAuth2ConnectionFactory(String providerId,
			ServiceProvider<A> serviceProvider, ApiAdapter<A> apiAdapter) {
		super(providerId, serviceProvider, apiAdapter);
		
		// TODO Auto-generated constructor stub
	}

	@Override
	public Connection<A> createConnection(ConnectionData data) {
		// TODO Auto-generated method stub
		return null;
	}

//	public OAuth2Operations getOAuthOperations();
//
//    public SocialConnection<A> createConnection(AccessGrant accessGrant);
//
//    public SocialConnection<A> createConnection(ConnectionData data);
//
//    public void setScope(String scope);
//
//    public String getScope();
//
//    public String generateState();
//
//    public boolean supportsStateParameter();

}