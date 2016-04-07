package ar.com.midasconsultores.catalogodefiltros.service;

import ar.com.midasconsultores.catalogodefiltros.utils.LastUpdatedVersionType;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class SqlDumpServiceImpl implements SqlDumpService {

	private static final String PARAMETRO_FECHA_DUMP = "=fecha";

	private static final String EXCLUDE_TABLES_OPTION = "-T";

	private static final String PERSISTENCE_PROPERTIES_ENCRIPTATION_PASSWORD = "#{persistenceProperties['encription_password']}";

	private static final String EXCLUDE_TABLE = "pedido";

	private static final String INCLUDE_SQL_DDL = "DROP TABLE IF EXISTS";

	private static final String SCHEMA_SQL = "schema.sql";

	private static final String COMILLAS = "\"";

	private static final String FILE_OPTION = "-f";

	private static final String WINDOWS_1252 = "windows-1252";

	private static final int BUFFER_IN_WAIT_TIME = 3000;

	private static final String USER_NAME_OPTION = "-U";

	private static final String SERVER_PORT_OPTION = "-p";

	private static final String SERVER_HOST_OPTION = "-h";

	private static final String DUMP_NAME = "dump";

	private static final String DUMP_EXTENSION = ".dump";

	private static final String DUMP_BATCH_PATH = "bin/pg_dump.exe\"";

	private static final String DUMP_RESTORE_BATCH_PATH = "bin/psql.exe\"";

	private static final String PASSWORD_REQUEST_OPTION = "PGPASSWORD";

	private static final String PERSISTENCE_PROPERTIES_SERVER_HOST = "#{persistenceProperties['host']}";

	private static final String PERSISTENCE_PROPERTIES_SERVER_PORT = "#{persistenceProperties['port']}";

	private static final String PERSISTENCE_PROPERTIES_DB_NAME = "#{persistenceProperties['db_name']}";

	private static final String PERSISTENCE_PROPERTIES_USER_NAME = "#{persistenceProperties['user']}";

	private static final String PERSISTENCE_PROPERTIES_PASSWORD_REQUEST = "#{persistenceProperties['password']}";
	
	private static final String PERSISTENCE_PROPERTIES_DUMP_ERROR_PATH = "#{persistenceProperties['dump_error_file']}";
        
        @Autowired
        private FileService fileService;
	@Autowired
	private FileSystemLocationService fileSystemLocationService;
        
        @Autowired
        private EncryptorDecriptorService encryptorDecriptorService;
        
	@Value(PERSISTENCE_PROPERTIES_ENCRIPTATION_PASSWORD)
	private String encriptationPassword;

	@Value(PERSISTENCE_PROPERTIES_SERVER_HOST)
	private String host;

	@Value(PERSISTENCE_PROPERTIES_SERVER_PORT)
	private String port;

	@Value(PERSISTENCE_PROPERTIES_DB_NAME)
	private String dbName;

	@Value(PERSISTENCE_PROPERTIES_USER_NAME)
	private String userName;

	@Value(PERSISTENCE_PROPERTIES_PASSWORD_REQUEST)
	private String passwordRequest;

	@Value(PERSISTENCE_PROPERTIES_DUMP_ERROR_PATH)
	private String dumpErrorPath;
        
        private static final Logger log = LoggerFactory.getLogger(SqlDumpServiceImpl.class);
        @Override
         public void obtenerSqlDump(OutputStream os) throws Exception{
             ProcessBuilder builder = new ProcessBuilder("\""+obtenerRuta()
				+ DUMP_BATCH_PATH, USER_NAME_OPTION, userName,
				SERVER_HOST_OPTION, host.trim(), SERVER_PORT_OPTION, port.trim(),
				EXCLUDE_TABLES_OPTION, "authorities", EXCLUDE_TABLES_OPTION,
				"users", EXCLUDE_TABLES_OPTION, "referencias", dbName.trim());

		// agrega el parametro de password para que no lo pida cuando ejecute el
		// proceso
		Map<String, String> env = builder.environment();
		env.put(PASSWORD_REQUEST_OPTION, passwordRequest);

		// ejecucion del proceso
		Process proc = builder.start();
		// tiempo de espera hasta que el proceso comienze a colocar datos en el
		// inputstream
		Thread.sleep(BUFFER_IN_WAIT_TIME);
			
		IOUtils.copy(proc.getInputStream(), os);
         }
//	@Override
//	public byte[] codificarSQLDump() throws Exception {
//
//		// crear un proceso que realize el dump de catalogodefiltros db
//		ProcessBuilder builder = new ProcessBuilder("\""+obtenerRuta()
//				+ DUMP_BATCH_PATH, USER_NAME_OPTION, userName,
//				SERVER_HOST_OPTION, host.trim(), SERVER_PORT_OPTION, port.trim(),
//				EXCLUDE_TABLES_OPTION, "authorities", EXCLUDE_TABLES_OPTION,
//				"users", EXCLUDE_TABLES_OPTION, "referencias", dbName.trim());
//
//		// agrega el parametro de password para que no lo pida cuando ejecute el
//		// proceso
//		Map<String, String> env = builder.environment();
//		env.put(PASSWORD_REQUEST_OPTION, passwordRequest);
//
//		// ejecucion del proceso
//		Process proc = builder.start();
//		// tiempo de espera hasta que el proceso comienze a colocar datos en el
//		// inputstream
//		Thread.sleep(BUFFER_IN_WAIT_TIME);
//
//		// archivo temporal que va a contener el dump
//		File archivoDumpTemporal = File.createTempFile(DUMP_NAME,
//				DUMP_EXTENSION);
//		archivoDumpTemporal.deleteOnExit();
//
//		FileOutputStream fos = new FileOutputStream(archivoDumpTemporal);
//		IOUtils.copy(proc.getInputStream(), fos);
//
//		byte[] encoded = Files.readAllBytes(archivoDumpTemporal.toPath());
//
//		String fecha = new SimpleDateFormat("ddMMyyyyhhmmss").format(new java.util.Date());
//		// decodificacion del archivo dump
//		String sqlDump =fecha+PARAMETRO_FECHA_DUMP+"\r\n"+  Charset.forName(WINDOWS_1252)
//				.decode(ByteBuffer.wrap(encoded)).toString();
//
//	
//		// encriptacion del archivo dump
//		String sqlDumpEncoded =encryptorDecriptorService.getEncryptorDescriptor(encriptationPassword).encrypt(sqlDump);
//		byte[] array = sqlDumpEncoded.getBytes(Charset.forName(WINDOWS_1252));
//
//		fos.close();
//		FileDeleteStrategy.FORCE.delete(archivoDumpTemporal);
//		// se devuleve un byte array que corresponde a los caracters del archivo
//		// dump encriptados codificados con windows-1252
//		return array;
//
//	}

	private String obtenerRuta() throws Exception {
		// Obtener la ruta de postgres
		String ruta = fileSystemLocationService.getDBFileSystemLocation();
		return ruta.substring(0, ruta.lastIndexOf("/") + 1);

	}

	

//	@Override
//	public String realizarVolcadoDB(String path) throws Exception {
//		String sqlSentence = encryptorDecriptorService.getEncryptorDescriptor(encriptationPassword).decrypt(path);
//		String fecha = sqlSentence.substring(0,sqlSentence.indexOf(PARAMETRO_FECHA_DUMP));
//		sqlSentence = sqlSentence.substring(sqlSentence.indexOf(PARAMETRO_FECHA_DUMP)+6,sqlSentence.length());
//		// int indexBeforeAdd = sqlSentence.indexOf(STRING_FOR_SPLIT);
//		StringBuilder sb = new StringBuilder(eliminarTablasExistentes());
//		// read schema sql file in order to get all the dump configuration file
//		// before the dump process begins
//		// sb.append();
//		sb.append(sqlSentence);
//
////		runPgsqlProcess(sb.toString().getBytes());
//		return fecha;
//
//	}
        
        @Override
	public void realizarVolcadoSqlDB() throws Exception {
            String warLocation = fileSystemLocationService.getWarFileSystemLocation();
            fileService.saveFile(eliminarTablasExistentes(),warLocation+"\\update\\eliminar.txt");

		runPgsqlProcess(warLocation+"\\update\\eliminar.txt");
                runPgsqlProcess(warLocation+"\\update\\"+LastUpdatedVersionType.DB_UPDATE_FILES.getUpdateDataFileName());
		

	}
        private void save(String aaa,String name)throws Exception{
            
            fileService.saveFile(aaa,name);
        }

	private String eliminarTablasExistentes() throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(this
				.getClass().getClassLoader().getResourceAsStream(SCHEMA_SQL)));
		StringBuilder sb = new StringBuilder();
		String aux = null;
		while (br.ready()) {
			aux = br.readLine();
			if (aux.contains(INCLUDE_SQL_DDL) && !aux.contains(EXCLUDE_TABLE)) {
				sb.append(aux).append("\r\n");
			}
		}
		return sb.toString();
	}

	private void runPgsqlProcess(String tempSqlFile) throws Exception {
		

		ProcessBuilder builder = new ProcessBuilder("\""+obtenerRuta()
				+ DUMP_RESTORE_BATCH_PATH, USER_NAME_OPTION, userName.trim(),
				SERVER_HOST_OPTION, host.trim(), SERVER_PORT_OPTION, port.trim(), FILE_OPTION, COMILLAS
						+ tempSqlFile + COMILLAS, dbName.trim());

		Map<String, String> env = builder.environment();
		env = builder.environment();
		env.put(PASSWORD_REQUEST_OPTION, passwordRequest);
		
		if (StringUtils.isEmpty(dumpErrorPath) || StringUtils.isEmpty(dumpErrorPath.trim())) {
			builder.redirectError(File.createTempFile("error", ".error"));
		} else {
			builder.redirectError(new File(dumpErrorPath));
		}

		Process proc = builder.start();
		proc.waitFor();
		
		System.out.println("La actualizacion ha finalizado.");
	}
        
        }
