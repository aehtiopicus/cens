package com.aehtiopicus.cens.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurer;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.web.ConnectController;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.security.AuthenticationNameUserIdSource;

@Configuration
@EnableSocial
public class FacebookConfiguration  implements SocialConfigurer{

private static final String API_KEY = "#{socialProperties['api_key']}";
private static final String API_SECRET = "#{socialProperties['api_secret']}";
private static final String API_NAME_SPACE = "#{socialProperties['api_name_space']}";
	
	@Value(API_KEY)
	private String apiKey;
	
	@Value(API_SECRET)
	private String apiSecret;
	
	@Value(API_NAME_SPACE)
	private String apiNameSpace;
	
	@Autowired
	DataSource dataSource;
	
	@Override
	public void addConnectionFactories(ConnectionFactoryConfigurer cfc,
			Environment env) {
		cfc.addConnectionFactory(new FacebookConnectionFactory(apiKey,apiSecret,apiNameSpace));						
	}

	@Override
	public UserIdSource getUserIdSource() {
		 return new AuthenticationNameUserIdSource();
	}

	@Override
	public UsersConnectionRepository getUsersConnectionRepository(
			ConnectionFactoryLocator arg0) {
		return new JdbcUsersConnectionRepository(dataSource, arg0, Encryptors.noOpText());
	}

	 @Bean
	    public ConnectController connectController(
	                ConnectionFactoryLocator connectionFactoryLocator,
	                ConnectionRepository connectionRepository) {
	        return new ConnectController(connectionFactoryLocator, connectionRepository);
	    }

}
