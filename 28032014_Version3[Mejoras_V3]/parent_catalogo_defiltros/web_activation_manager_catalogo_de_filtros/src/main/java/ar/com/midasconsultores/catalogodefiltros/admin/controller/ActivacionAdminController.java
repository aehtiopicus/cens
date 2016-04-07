package ar.com.midasconsultores.catalogodefiltros.admin.controller;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Principal;
import java.util.Calendar;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import ar.com.midasconsultores.appvalidation.GeneradorDeSerialConCaducidad;
import ar.com.midasconsultores.catalogodefiltros.admin.dto.SerialDTO;
import ar.com.midasconsultores.catalogodefiltros.admin.mapper.VendedorMapper;
import ar.com.midasconsultores.catalogodefiltros.service.DumpService;
import ar.com.midasconsultores.catalogodefiltros.service.FileSystemLocationService;
import ar.com.midasconsultores.catalogodefiltros.service.VendedorService;
import ar.com.midasconsultores.catalogodefiltros.utils.FTPUploaderService;
import ar.com.midasconsultores.catalogodefiltros.utils.LastUpdatedVersionType;
import org.apache.commons.io.output.ByteArrayOutputStream;

/**
 * Handles requests for the application home page.
 */
@Controller
public class ActivacionAdminController {

	

//	private static final String LAST_UPDATE_VERSION = "last_update.version";

//	private static final String UPDATE_CFS = "update.cfs";

	private static final String FILE_EXTENSION_SEPARATOR = ".";

	private static final String FILE_PATH_HEADER = "file:";

	private final static String file_separator = System
			.getProperty("file.separator");

	private final static String EXTERNAL_FILE_PATH = "c:" + file_separator
			+ "filtros" + file_separator;

	private final static String NOT_AVAILABLE_IMAGE_RESOURCE_RELATIVE_PATH = file_separator
			+ "resources"
			+ file_separator
			+ "images"
			+ file_separator
			+ "image_not_available" + FILE_EXTENSION_SEPARATOR + "jpg";

	private static final String FTP_PROPERTIES_HOST = "#{ftpProperties['host']}";
	private static final String FTP_PROPERTIES_USER = "#{ftpProperties['user']}";
	private static final String FTP_PROPERTIES_PASSWORD = "#{ftpProperties['password']}";
	private static final String FTP_PROPERTIES_PORT = "#{ftpProperties['port']}";
	private static final String FTP_PROPERTIES_HOST_FOLDER = "#{ftpProperties['hostFolder']}";

	@Value(FTP_PROPERTIES_HOST)
	private String host;

	@Value(FTP_PROPERTIES_USER)
	private String user;

	@Value(FTP_PROPERTIES_PASSWORD)
	private String password;

	@Value(FTP_PROPERTIES_PORT)
	private String port;

	@Value(FTP_PROPERTIES_HOST_FOLDER)
	private String hostFolder;
	
	
	private static final Logger logger = LoggerFactory
			.getLogger(ActivacionAdminController.class);

	
	@Autowired
	private ResourceLoader resourceLoader;

	@Autowired
	private DumpService dumpService;

	@Autowired
	private FTPUploaderService ftpUploaderService;
	
	@Autowired
	private FileSystemLocationService fileSystemLocationService;
        
        @Autowired
        private VendedorService vendedorService;
        
        @Autowired
        private VendedorMapper vendedorMapper;

	@RequestMapping(value = "/activacion", method = RequestMethod.GET)
	public ModelAndView activacion(Locale locale, Model model, Principal principal,
			HttpServletRequest request, HttpServletResponse response) {

		ModelAndView mav = new ModelAndView("activacion");

		mav.addObject("serialDTO", new SerialDTO());
                mav.addObject("vendedoresDto",vendedorMapper.convertDomainToDto(vendedorService.listSellers()));

		return mav;

	}

	@RequestMapping(value = "/serial.txt", method = RequestMethod.POST)
	public ModelAndView crearSerial(Locale locale,
			@ModelAttribute("serialDTO") SerialDTO serialDTO,
			Principal principal, BindingResult result,
			HttpServletRequest request, HttpServletResponse response) {

		try {

			OutputStream output = response.getOutputStream();

			byte[] serial = null;
                        
                        if(serialDTO.getVendedorId()==null || serialDTO.equals("-1")){
                            serial = GeneradorDeSerialConCaducidad.armarSerial(
					serialDTO.getIdEquipo(), serialDTO.getCodigoDeCliente(),
					serialDTO.getMesesDeValidez());
                        }else{
                                
                              serial = GeneradorDeSerialConCaducidad.armarSerialVendedor(
					serialDTO.getIdEquipo(),
					serialDTO.getMesesDeValidez(),serialDTO.getVendedorId());
                        }
                        

			ByteArrayInputStream input = new ByteArrayInputStream(serial);

			IOUtils.copy(input, output);
			response.setContentType("text/plain");
			response.flushBuffer();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	@RequestMapping(value = "/img/{nombre}.{ext}", method = RequestMethod.GET)
	public @ResponseBody
	String imagenes(@PathVariable("nombre") String nombre,
			@PathVariable("ext") String ext, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		OutputStream out = null;
		String externalFilePath = fileSystemLocationService.getWarFileSystemLocation() + file_separator + "imagenes" + file_separator;
		try {

			Resource resource = resourceLoader.getResource(FILE_PATH_HEADER
					+ externalFilePath + nombre + FILE_EXTENSION_SEPARATOR 
					+ ext);

			out = response.getOutputStream();

			IOUtils.copy(resource.getInputStream(), out);
			response.setContentType("image/" + ext);
			response.flushBuffer();

		} catch (FileNotFoundException e) {

			try {

				Resource resource = resourceLoader.getResource(FILE_PATH_HEADER
						+ request.getSession().getServletContext()
								.getRealPath(file_separator)
						+ NOT_AVAILABLE_IMAGE_RESOURCE_RELATIVE_PATH);

				out = response.getOutputStream();

				IOUtils.copy(resource.getInputStream(), out);
				response.setContentType("image/" + ext);
				response.flushBuffer();

			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e2) {
				e.printStackTrace();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/volcado", method = RequestMethod.GET)
	public void generarVolcadoDB(HttpServletResponse response)
			throws Exception {

//		byte[] documentBody = dumpService.codificarSQLDump();
//		String fecha = new SimpleDateFormat("ddMMyyyyhhmmss").format(new java.util.Date());
//		HttpHeaders header = new HttpHeaders();
//		header.setContentType(new MediaType(APPLICATION_MEDIA_TYPE, PDF_EXTENSION));
//		header.set(CONTENT_DISPOSITION, ATTACHMENT_FILENAME_CFS+fecha+CFS_EXTENSION);
//		header.setContentLength(documentBody.length);
//
//		return new HttpEntity<byte[]>(documentBody, header);
                 generarCargaManual(response);
	}


	@Scheduled(cron="0 30 06 * * *") 
	public void generarVolcadoDBFTP()
			throws Exception {
		

		System.out.println("Ejecucion del volcado ftp automatico...");
		logger.info("Ejecucion del volcado ftp automatico...");
                
//		byte[] documentBody = dumpService.codificarSQLDump();
		
		int portNumber = 21;

		if (!StringUtils.isEmpty(port)) {

			try {
				portNumber = Integer.parseInt(port);
			} catch (NumberFormatException e) {
				portNumber = 21;
			}

		}

		String timeStamp = Long.toString(Calendar.getInstance().getTimeInMillis());
		
		// convert String into InputStream
		InputStream is = new ByteArrayInputStream(timeStamp.getBytes());
		
		logger.info("Upload File test-"+LastUpdatedVersionType.DB_UPDATE_FILES.getUpdateDataFileName());
		System.out.println("Upload File test-"+LastUpdatedVersionType.DB_UPDATE_FILES.getUpdateDataFileName());
		ftpUploaderService.uploadFile(is,
				                    LastUpdatedVersionType.DB_UPDATE_FILES.getLastUpdateName(), hostFolder, host, user, password, portNumber);
		

		logger.info("Upload File test-update.cfs");
		System.out.println("Upload File test-update.cfs");
		ftpUploaderService.uploadFileOutputStream(
				LastUpdatedVersionType.DB_UPDATE_FILES.getUpdateDataFileName(), hostFolder, host, user, password, portNumber);
	}
	

	@RequestMapping(value = "/volcadoftp", method = RequestMethod.GET)
	public void generarVolcadoDBFTPManual()
			throws Exception {
		generarVolcadoDBFTP();
		System.out.print("Ejecucion del volcado ftp manual...");
		logger.info("Ejecucion del volcado ftp manual...");
	}
        
        @RequestMapping(value = "/cargaManual", method = RequestMethod.GET)
	public void generarCargaManual(HttpServletResponse response)
			throws Exception {
OutputStream baos = response.getOutputStream();
            
            
            response.setHeader("Content-Disposition", "attachment; filename=\"update.zip\"");
            dumpService.getAllUpdates(baos);	
//		byte[] documentBody = baos.toByteArray();
//		HttpHeaders header = new HttpHeaders();
//		header.setContentType(new MediaType(APPLICATION_MEDIA_TYPE, PDF_EXTENSION));
//		header.set(CONTENT_DISPOSITION, ATTACHMENT_FILENAME_CARGA_MANUAL+CARGA_MANUAL_EXTENSION);
//		header.setContentLength(baos.size());
//
//		return new HttpEntity<byte[]>(documentBody, header);
	}


}
