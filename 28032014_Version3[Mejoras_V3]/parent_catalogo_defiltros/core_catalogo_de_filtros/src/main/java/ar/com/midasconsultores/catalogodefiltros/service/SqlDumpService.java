package ar.com.midasconsultores.catalogodefiltros.service;

import java.io.OutputStream;


public interface SqlDumpService {

//	public byte[] codificarSQLDump()throws Exception;
//	public String realizarVolcadoDB(String path) throws Exception;

    public void obtenerSqlDump(OutputStream os)throws Exception;

    public void realizarVolcadoSqlDB()throws Exception;
}
