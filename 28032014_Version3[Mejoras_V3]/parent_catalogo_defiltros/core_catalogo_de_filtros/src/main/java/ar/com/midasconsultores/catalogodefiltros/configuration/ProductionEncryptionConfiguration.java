package ar.com.midasconsultores.catalogodefiltros.configuration;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.EnvironmentStringPBEConfig;
import org.jasypt.hibernate4.encryptor.HibernatePBEStringEncryptor;
import org.jasypt.salt.ZeroSaltGenerator;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ar.com.midasconsultores.profiles.Production;

@Configuration
@Production
public class ProductionEncryptionConfiguration {

	private static final String PERSISTENCE_PROPERTIES_PASSWORD = "#{persistenceProperties['encription_password']}";

	private static final String HIBERNATE_STRING_ZERO_SALT_ENCRYPTOR = "hibernateStringZeroSaltEncryptor";

	private static final String ZERO_SALT_ENCRIPTOR = "zeroSaltEncriptor";

	private static final String ZERO_SALT_ENCRYPTOR_CONFIGURATION = "zeroSaltEncryptorConfiguration";

	private static final String HIBERNATE_STRING_ENCRYPTOR = "hibernateStringEncryptor";

	private static final String ENCRYPTOR = "encryptor";

	private static final String ENCRYPTOR_CONFIGURATION = "encryptorConfiguration";

	private static final String PBE_WITH_MD5_AND_DES = "PBEWithMD5AndDES";
	
	@Value(PERSISTENCE_PROPERTIES_PASSWORD)
	private String password;

	@Bean(name = ENCRYPTOR_CONFIGURATION)
	public EnvironmentStringPBEConfig encryptorConfiguration() {

		EnvironmentStringPBEConfig result = new EnvironmentStringPBEConfig();
		result.setAlgorithm(PBE_WITH_MD5_AND_DES);
		result.setPassword(password);

		return result;

	}

	@Bean(name = ENCRYPTOR)
	public StandardPBEStringEncryptor encryptor() {

		StandardPBEStringEncryptor result = new StandardPBEStringEncryptor();
		result.setConfig(encryptorConfiguration());

		return result;

	}

	@Bean(name = HIBERNATE_STRING_ENCRYPTOR)
	public HibernatePBEStringEncryptor hibernateStringEncryptor() {

		HibernatePBEStringEncryptor result = new HibernatePBEStringEncryptor();

		result.setEncryptor(encryptor());
		result.setRegisteredName(HIBERNATE_STRING_ENCRYPTOR);

		return result;

	}

	@Bean(name = ZERO_SALT_ENCRYPTOR_CONFIGURATION)
	public EnvironmentStringPBEConfig zeroSaltEncryptorConfiguration() {

		EnvironmentStringPBEConfig result = new EnvironmentStringPBEConfig();
		result.setAlgorithm(PBE_WITH_MD5_AND_DES);
		result.setPassword(password);
		result.setSaltGenerator(new ZeroSaltGenerator());

		return result;

	}

	@Bean(name = ZERO_SALT_ENCRIPTOR)
	public StandardPBEStringEncryptor zeroSaltEncriptor() {

		StandardPBEStringEncryptor result = new StandardPBEStringEncryptor();
		result.setConfig(zeroSaltEncryptorConfiguration());

		return result;

	}

	@Bean(name = HIBERNATE_STRING_ZERO_SALT_ENCRYPTOR)
	public HibernatePBEStringEncryptor hibernateStringZeroSaltEncryptor() {

		HibernatePBEStringEncryptor result = new HibernatePBEStringEncryptor();

		result.setEncryptor(zeroSaltEncriptor());
		result.setRegisteredName(HIBERNATE_STRING_ZERO_SALT_ENCRYPTOR);

		return result;

	}

}
