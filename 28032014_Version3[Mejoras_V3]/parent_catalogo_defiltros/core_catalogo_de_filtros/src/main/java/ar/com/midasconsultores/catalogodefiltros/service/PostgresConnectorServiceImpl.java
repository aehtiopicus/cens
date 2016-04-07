package ar.com.midasconsultores.catalogodefiltros.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PostgresConnectorServiceImpl implements PostgresConnectorService{


	private static final String PERSISTENCE_PROPERTIES_DRIVER = "#{persistenceProperties['driver']}";
	
	private static final String JDBC_POSTGRESQL_PREFIX = "jdbc:postgresql://";
	
	private Connection conn;

	private static final String PERSISTENCE_PROPERTIES_HOST = "#{persistenceProperties['host']}";
	private static final String PERSISTENCE_PROPERTIES_USER = "#{persistenceProperties['user']}";
	private static final String PERSISTENCE_PROPERTIES_PASSWORD = "#{persistenceProperties['password']}";
	private static final String PERSISTENCE_PROPERTIES_PORT = "#{persistenceProperties['port']}";
	private static final String PERSISTENCE_PROPERTIES_DB_NAME = "#{persistenceProperties['db_name']}";
	
	
	
	@Value(PERSISTENCE_PROPERTIES_HOST)
	private String host;

	@Value(PERSISTENCE_PROPERTIES_USER)
	private String user;

	@Value(PERSISTENCE_PROPERTIES_PASSWORD)
	private String password;

	@Value(PERSISTENCE_PROPERTIES_PORT)
	private String port;

	@Value(PERSISTENCE_PROPERTIES_DB_NAME)
	private String db_name;	

	@Value(PERSISTENCE_PROPERTIES_DRIVER)
	private String db_driver;	
	
	@Override
	public void iniciarConexion()throws Exception{
		this.loadDriverClass();
		
		if (conn == null || conn.isClosed()) {
			this.setConnection();
		}
	}

	private void setConnection() throws Exception {
		try {
			
			conn = DriverManager.getConnection(JDBC_POSTGRESQL_PREFIX+host+":"+port+"/"+db_name, user, password);
		} catch (SQLException e) {
			throw new Exception("Error al intentar establecer la conexion", e);
		}

	}

	@Override
	public void closeConnection() throws Exception {
		try {
			if (conn != null && !conn.isClosed()) {
				conn.close();
				conn = null;
			}
		} catch (Exception e) {
			throw new Exception("Error al intentar cerrar la conexion", e);
		}
	}

	private void loadDriverClass() throws Exception {
		try {
			Class.forName(db_driver).newInstance();
		} catch (ClassNotFoundException e) {
			throw new Exception("Error al tratar de encontrar la calse", e);
		} catch (IllegalAccessException e) {
			throw new Exception("Error de intento de accesso", e);
		} catch (InstantiationException e) {
			throw new Exception(
					"Error al intentar crear una instancia de la clase", e);
		}
	}
	
	@Override
	public ResultSet ejecutarConsulta(String sql)throws Exception{
		try{
			Statement stmt =conn.createStatement();
			stmt.executeQuery(sql);
			return stmt.getResultSet();
		}catch(SQLException e){
			try {
				conn.rollback();
			} catch (SQLException e1) {
				throw new Exception("Error ejecutando la consulta \r\n"+sql,e1);
			}
			throw new Exception("Error ejecutando la consulta \r\n"+sql,e);
		}
	}
	
	@Override
	public boolean ejecutar(String sql)throws Exception{
		try{
			Statement stmt =conn.createStatement();
			return stmt.execute(sql);			
		}catch(SQLException e){
			try {
				conn.rollback();
			} catch (SQLException e1) {
				throw new Exception("Error ejecutando la consulta \r\n"+sql,e1);
			}
			throw new Exception("Error ejecutando la consulta \r\n"+sql,e);
		}
	}
}
