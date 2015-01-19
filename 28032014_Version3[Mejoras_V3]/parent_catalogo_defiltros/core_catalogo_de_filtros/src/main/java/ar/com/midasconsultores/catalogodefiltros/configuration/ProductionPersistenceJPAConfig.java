package ar.com.midasconsultores.catalogodefiltros.configuration;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.hibernate4.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import ar.com.midasconsultores.profiles.Production;

@Configuration
@EnableTransactionManagement
@ImportResource("classpath*:*spring-data-config.xml")
@Production
public class ProductionPersistenceJPAConfig {


	private static final String PACKAGE_TO_SCAN = "ar.com.midasconsultores.catalogodefiltros.domain";
	static final String CLASSPATH_SPRING_DATA_CONFIG_XML = "classpath*:*spring-data-config.xml";
	private static final String JDBC_POSTGRESQL_PREFIX = "jdbc:postgresql://";
	private static final String PERSISTENCE_PROPERTIES_HOST = "#{persistenceProperties['host']}";
	private static final String PERSISTENCE_PROPERTIES_USER = "#{persistenceProperties['user']}";
	private static final String PERSISTENCE_PROPERTIES_PASSWORD = "#{persistenceProperties['password']}";
	private static final String PERSISTENCE_PROPERTIES_PORT = "#{persistenceProperties['port']}";
	private static final String PERSISTENCE_PROPERTIES_DB_NAME = "#{persistenceProperties['db_name']}";
	private static final String PERSISTENCE_PROPERTIES_HIBERNATE_HBM2DDL_AUTO = "#{persistenceProperties['hibernate_hbm2ddl_auto']}";
	private static final String PERSISTENCE_PROPERTIES_DRIVER = "#{persistenceProperties['driver']}";
	private static final String SCHEMA_SQL_FILE_NAME = "/schema.sql";
	private static final String SQL_FOR_CHECKING_IF_DB_WAS_INITIALIZED = "SELECT * FROM information_schema.tables WHERE table_schema = 'public' AND table_name = 'users'";
	private static final String USERS_TABLE_NAME = "users";
	private static final String TABLE_NAME_COLUMN_NAME = "table_name";

	@Value(PERSISTENCE_PROPERTIES_HOST)
	private String host;
	@Value(PERSISTENCE_PROPERTIES_USER)
	private String user;
	@Value(PERSISTENCE_PROPERTIES_PASSWORD)
	private String password;
	@Value(PERSISTENCE_PROPERTIES_PORT)
	private String port;
	@Value(PERSISTENCE_PROPERTIES_DB_NAME)
	private String dbName;
	@Value(PERSISTENCE_PROPERTIES_HIBERNATE_HBM2DDL_AUTO)
	private String hibernateHbm2ddlAuto;
	@Value(PERSISTENCE_PROPERTIES_DRIVER)
	private String driver;
	
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() {
		LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
		factoryBean.setDataSource(restDataSource());
		
		factoryBean
				.setPackagesToScan(new String[] { PACKAGE_TO_SCAN });

		JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter() {

			{
				// JPA properties
				setShowSql(false);
				setDatabase(Database.POSTGRESQL);
				setGenerateDdl(true);
			}
		};
		factoryBean.setJpaVendorAdapter(vendorAdapter);
		factoryBean.setJpaProperties(additionalProperties());

		return factoryBean;
	}

	@Bean(name = "dataSource")
	public DataSource restDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(driver);
		dataSource.setUrl(JDBC_POSTGRESQL_PREFIX + host + ":" + port + "/"
				+ dbName);
		dataSource.setUsername(user);
		dataSource.setPassword(password);
		return dataSource;
	}

	@Bean
	public PlatformTransactionManager transactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactoryBean()
				.getObject());

		return transactionManager;
	}

	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}

	@Bean
	public HibernateExceptionTranslator hibernateExceptionTranslator() {
		return new HibernateExceptionTranslator();
	}

	final Properties additionalProperties() {
		return new Properties() {

			private static final long serialVersionUID = -2786293645119644347L;
			
			{
				// use this to inject additional properties in the EntityManager
				setProperty("hibernate.hbm2ddl.auto", hibernateHbm2ddlAuto);
			}
		};
	}
	/**
	 * 
	 * This is used to setup the database. It will load the schema.sql file
	 * which does a create table so we have a table to work with in the project
	 */
	@Bean
	public DataSourceInitializer dataSourceInitializer(DataSource dataSource) {
		ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
		resourceDatabasePopulator.addScript(new ClassPathResource(
				SCHEMA_SQL_FILE_NAME));

		DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
		dataSourceInitializer.setDataSource(dataSource);
		dataSourceInitializer.setDatabasePopulator(resourceDatabasePopulator);

		// declare object of Statement interface that uses for executing sql
		// statements.
		Statement statement;
		// declare a resultset that uses as a table for output data from the
		// table.
		ResultSet rs;

		Connection connection;

		try {
			connection = dataSource.getConnection();
			statement = connection.createStatement();
			// sql query of string type to create a data base.
			// sql query to retrieve values from the specified table.
			String queryString = SQL_FOR_CHECKING_IF_DB_WAS_INITIALIZED;
			rs = statement.executeQuery(queryString);
			if (rs.next()) {
				
				String tableName = rs.getString(TABLE_NAME_COLUMN_NAME);
				if (tableName != null && tableName.equals(USERS_TABLE_NAME)) {
					dataSourceInitializer.setEnabled(false);
				}
			}
			// close all the connections.
			rs.close();
			statement.close();
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return dataSourceInitializer;
	}
}
