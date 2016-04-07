package ar.com.midasconsultores.catalogodefiltros.service;

import java.io.IOException;
import java.util.List;

public interface FileProcessService {
	
	public void createCodificationFile( String[] nameOfFiles, String location) throws Exception;
	
	
	public List<String> getContentLocalIndexFile(String url)	throws IOException;

	public List<String> getContentFtpIndexFile(String hostFolder,String host, String user, String password, String port,
			String diferencias) throws Exception;

	public List<String> getNameOfFilesToProcess(List<String> contentIndex,
			List<String> contentIndex2);
	

}
