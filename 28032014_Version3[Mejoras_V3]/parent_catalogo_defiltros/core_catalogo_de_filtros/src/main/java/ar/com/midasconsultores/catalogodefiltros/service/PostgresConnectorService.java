package ar.com.midasconsultores.catalogodefiltros.service;

import java.sql.ResultSet;

public interface PostgresConnectorService {

	public void iniciarConexion()throws Exception;
	
	public void closeConnection() throws Exception ;
	
	public ResultSet ejecutarConsulta(String sql)throws Exception;
	
	public boolean ejecutar(String sql)throws Exception;
}
