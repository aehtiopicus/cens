package ar.com.midasconsultores.catalogodefiltros.service;

public interface DBLocationService{

	public String obtenerRutaInstalacion()throws Exception;

	public void restaurarDump(String decrypt)throws Exception;
}
