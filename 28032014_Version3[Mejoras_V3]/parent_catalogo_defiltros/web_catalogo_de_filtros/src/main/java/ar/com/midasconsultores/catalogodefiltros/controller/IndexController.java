package ar.com.midasconsultores.catalogodefiltros.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import ar.com.midasconsultores.catalogodefiltros.configuration.UrlConstants;
import ar.com.midasconsultores.catalogodefiltros.configuration.ViewConstants;
import ar.com.midasconsultores.catalogodefiltros.domain.PrecioVenta;
import ar.com.midasconsultores.catalogodefiltros.domain.Users;
import ar.com.midasconsultores.catalogodefiltros.dto.ActivationDataDTO;
import ar.com.midasconsultores.catalogodefiltros.dto.PrecioVentaDTO;
import ar.com.midasconsultores.catalogodefiltros.dto.UsuarioDTO;
import ar.com.midasconsultores.catalogodefiltros.mappers.PrecioVentaMapper;
import ar.com.midasconsultores.catalogodefiltros.mappers.UsuariosMapper;
import ar.com.midasconsultores.catalogodefiltros.service.ActualizacionService;
import ar.com.midasconsultores.catalogodefiltros.service.CFSUpdateService;
import ar.com.midasconsultores.catalogodefiltros.service.FileSystemLocationService;
import ar.com.midasconsultores.catalogodefiltros.service.PrecioVentaService;
import ar.com.midasconsultores.catalogodefiltros.service.UsuarioService;
import ar.com.midasconsultores.catalogodefiltros.utils.FTPUploaderService;
import ar.com.midasconsultores.catalogodefiltros.utils.UpdateType;
import ar.com.midasconsultores.utils.ExcepcionDeNegocios;
import ar.com.midasconsultores.utils.extjs.FilterRequest;
import ar.com.midasconsultores.utils.extjs.JsonUtils;
import ar.com.midasconsultores.utils.extjs.ResponseMap;

/**
 * Handles requests for the application home page.
 */
@Controller
public class IndexController {

    private static final Logger log = LoggerFactory.getLogger(IndexController.class);
//    private static final String LAST_UPDATE_VERSION = "last_update.version";

    private static final String DESCARGAR_VOLCADO = "descargar_volcado";

    private static final String IMAGE = "image/";

    private static final String[] IMAGE_EXTENSIONS = {"jpg", "png", "bmp", "gif"};

    private static final String FILE_EXTENSION_SEPARATOR = ".";

    private static final String FILE_PATH_HEADER = "file:";

    private static final String EMPRESA = "#{configProperties['empresa']}";

    private static final String CONFIG_PROPERTIES_EXTERNAL_FILE_PATH = "#{configProperties['external_file_path']}";

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

    @Value(EMPRESA)
    private Boolean empresa;

    @Autowired
    private ResponseMap<UsuarioDTO> extJS;

    @Autowired
    private PrecioVentaService precioVentaService;

    @Autowired
    private FTPUploaderService ftpUploaderService;

    @Autowired
    private ResponseMap<PrecioVentaDTO> extPrecioVentaJS;

    @Autowired
    private CFSUpdateService cfsUpdateService;

    @Autowired
    private ActualizacionService actualizacionService;

    private static final Logger logger = LoggerFactory
            .getLogger(IndexController.class);

    private final static String file_separator = System
            .getProperty("file.separator");

   

    private final static String NOT_AVAILABLE_IMAGE_RESOURCE_RELATIVE_PATH = file_separator
            + "resources"
            + file_separator
            + "images"
            + file_separator
            + "image_not_available" + FILE_EXTENSION_SEPARATOR + "jpg";

    @Autowired(required = true)
    private UsuarioService usuarioService;

    @Autowired
    private ActivationService activationService;

    @Autowired
    private UsuariosMapper usuarioMapper;

    @Autowired
    private PrecioVentaMapper precioVentaMapper;

    @Autowired
    private ResourceLoader resourceLoader;
    
    @Autowired
    private FileSystemLocationService fileSystemLocationService;

    /**
     * Simply selects the home view to render by returning its name.
     * @throws Exception 
     */
    @RequestMapping(value = "/img/{nombre}.{ext}", method = RequestMethod.GET)
    public @ResponseBody   String imagenes(@PathVariable("nombre") String nombre,
            @PathVariable("ext") String ext, HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        OutputStream out = null;

        Resource resource;
        Boolean foundImg = false;
        try {
            out = response.getOutputStream();
        	//String externalFilePath = fileSystemLocationService.getWarFileSystemLocation() + file_separator + "imagenes" + file_separator;
            for (String extension : IMAGE_EXTENSIONS) {
                try {
                    String catalinaBase = System.getProperty("catalina.base");
                    String[] urlImage =  catalinaBase.replace("\\",";").split(";");
                    String externalFilePath = "";
                    for(int i=0;i < urlImage.length-1; i++){
                    	externalFilePath = externalFilePath + urlImage[i] + file_separator;
                    }
                    externalFilePath =   externalFilePath + "war" + file_separator + "imagenes" + file_separator;
                    resource = resourceLoader.getResource(FILE_PATH_HEADER
                            + externalFilePath + nombre + FILE_EXTENSION_SEPARATOR
                            + extension);
                    IOUtils.copy(resource.getInputStream(), out);
                    response.setContentType(IMAGE + extension);
                    response.flushBuffer();
                    foundImg = true;
                    break;
                } catch (FileNotFoundException e) {
                    // Image Not found - Try next img extension
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (!foundImg) { // If no image was found then we retrieve the not available image
                resource = resourceLoader.getResource(FILE_PATH_HEADER
                        + request.getSession().getServletContext()
                        .getRealPath(file_separator)
                        + NOT_AVAILABLE_IMAGE_RESOURCE_RELATIVE_PATH);

                out = response.getOutputStream();
                IOUtils.copy(resource.getInputStream(), out);
                response.setContentType(IMAGE + ext);
                response.flushBuffer();
            }
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }

        return null;
    }

    /**
     * Simply selects the home view to render by returning its name.
     */
    @Deprecated
    @RequestMapping(value = {"/index_old"}, method = RequestMethod.GET)
    public ModelAndView homeExample(Locale locale, Model model,
            Principal principal, HttpServletRequest request) {

        ModelAndView mav = null;

        if (activationService.comprobarActivacion()) {
            mav = new ModelAndView("index_old");
            if (request.getParameter("empresa") != null) {
                empresa = Boolean.parseBoolean((String) request.getParameter("empresa"));
            }
            mav.addObject("empresa", empresa);
        } else {
            mav = new ModelAndView("credenciales");
            ActivationDataDTO adDto = activationService.obtenerInformacionActivacion();
            mav.addObject("codigo", adDto.getValor());
        }

        return mav;
    }

    @RequestMapping(value = {UrlConstants.INDEX_URL, UrlConstants.INDEX2_URL}, method = RequestMethod.GET)
    public ModelAndView inicio(Locale locale, Model model,
            Principal principal, HttpServletRequest request) {

        ModelAndView mav = null;

        if (activationService.comprobarActivacion()) {

            mav = new ModelAndView("inicio");
            if (request.getParameter("empresa") != null) {
                empresa = Boolean.parseBoolean((String) request.getParameter("empresa"));
            }
            mav.addObject("empresa", empresa);

            if (principal != null && principal.getName() != null) {
                // Agregamos la fecha de ultima actualizacion
                String userName = principal.getName();
                Users user = usuarioService.getUsuario(userName);
                if (user != null) {
                    try {
                        mav.addObject("fechaActualizacion", usuarioMapper.mapFechaActualizacion(user));
                    } catch (Exception e) {
                        mav.addObject("fechaActualizacion", "Fecha de Actualizacion no disponible.");
                    }
                } else {
                    mav.addObject("fechaActualizacion", "Fecha de Actualizacion no disponible.");
                }
            } else {
                mav.addObject("fechaActualizacion", "Fecha de Actualizacion no disponible.");
            }

            //busco el porcentaje a aplicarle a los precios
            PrecioVenta pv = precioVentaService.obtenerPrecioVenta();
            if (pv != null && empresa == false) {
                mav.addObject("porcentaje", pv.getPorcentaje());
            } else {
                mav.addObject("porcentaje", 0.0);
            }

        } else {
            mav = new ModelAndView(ViewConstants.CREDENCIALES_VIEW);
            ActivationDataDTO adDto = activationService.obtenerInformacionActivacion();
            mav.addObject("codigo", adDto.getValor());
        }

        return mav;
    }

    @RequestMapping(value = UrlConstants.BUSQUEDA_FILTRO_URL, method = RequestMethod.GET)
    public ModelAndView homeV2(Locale locale, Model model,
            Principal principal, HttpServletRequest request) {

        ModelAndView mav = null;
        mav = new ModelAndView(ViewConstants.BUSQUEDA_POR_FILTRO_VIEW);
        return mav;
    }
    
  
    @RequestMapping(value = "usuarios/list")
    public @ResponseBody
    Map<String, ? extends Object> list(Locale locale, @RequestParam int page,
            @RequestParam int start, @RequestParam int limit,
            @RequestParam(required = false) Object filter) throws Exception {

        try {

            Page<UsuarioDTO> usuariosDto = null;

            List<FilterRequest> filters = new ArrayList<FilterRequest>();

            if (filter != null) {
                logger.debug("Processing Filters!");
                filters.addAll(JsonUtils.getListFromJsonArray((String) filter));
            }

            usuariosDto = new PageImpl<UsuarioDTO>(
                    usuarioMapper.mapToDTOList(usuarioService
                            .getListaUsuarios()));

            long total = usuariosDto.getTotalElements();

            logger.debug("products : " + usuariosDto.getContent());

            return extJS.mapOK(usuariosDto.getContent(), total);

        } catch (ExcepcionDeNegocios e) {

            return extJS.mapError(e.getMessage());

        } catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
            return extJS
                    .mapError("Ocurri&oacute un error al tratar de listar los usuarios.");
        }

    }

    @RequestMapping(value = UrlConstants.PRECIO_VENTA)
    public @ResponseBody
    Map<String, Object> obtenerAjustePrecio() {
        PrecioVenta pv = precioVentaService.obtenerPrecioVenta();
        PrecioVentaDTO pvDTO = precioVentaMapper.crearDetallePrecioVentaDTO(pv);
        return extPrecioVentaJS.mapOK(pvDTO);
    }

    @RequestMapping(value = UrlConstants.PRECIO_VENTA_GUARDAR, method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> guardarAjustePrecio(String porcentaje) {

        Map<String, Object> result = new HashMap<String, Object>();
        try {
            PrecioVenta pv = precioVentaMapper.crearDetallePrecioVenta(porcentaje);
            precioVentaService.actualizarPrecioVenta(pv);
            result.put("success", true);
            result.put("nuevoPorcentaje", pv.getPorcentaje());
        } catch (Exception e) {
            result.put("success", false);
            result.put("nuevoPorcentaje", 0.0);
        }
        return result;
    }

    // @Scheduled(cron = "0 48 10 * * *")
    @RequestMapping(value = DESCARGAR_VOLCADO + "/{type}", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> descargarVolcadoDBFTP(Principal principal, @PathVariable UpdateType type) throws Exception {

        Map<String, Object> result = new HashMap<String, Object>();

        try {
            if (principal == null) {
                principal = (Principal) SecurityContextHolder.getContext()
                        .getAuthentication().getPrincipal();
            }
            String userName = principal.getName();
            int wait = actualizacionService.performUpdates(type, userName);
            result.put("success", true);
            result.put("wait", wait);
            return result;
        } catch (Exception e) {
            log.error("Error de sistema ", e);
            result.put("success", false);
            return result;
        }

    }

    // @Scheduled(cron = "0 42 11 * * *")
    @RequestMapping(value = UrlConstants.BUSCAR_ACTUALIZACIONES, method = RequestMethod.POST)
    public @ResponseBody
    Map<String, ? extends Object> verificarSiActualizacionesFTP(
            Principal principal) throws Exception {

        Map<String, Object> result = new HashMap<String, Object>();
        try {

            UpdateType updateType = actualizacionService.checkUpdates();

            result.put("success", true);
            result.put("serverError", false);
            result.put("update", updateType);

        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("serverError", true);
        }

        return result;

    }

    @RequestMapping(value = "update_date")
    public @ResponseBody
    Map<String, ? extends Object> obtenerFechaActualizacion(Principal principal) {

        Map<String, Object> result = new HashMap<String, Object>();
        try {
            if (principal == null) {
                principal = (Principal) SecurityContextHolder.getContext()
                        .getAuthentication().getPrincipal();
            }

            String userName = principal.getName();

            if (userName != null) {
                Users user = usuarioService.getUsuario(userName);
                if (user != null) {
                    result.put("success", true);
                    result.put("actualizacion",
                            usuarioMapper.mapFechaActualizacion(user));

                } else {
                    throw new Exception("Actualizacion no disponible");
                }
            } else {
                throw new Exception("Actualizacion no disponible");
            }

        } catch (Exception e) {
            result.put("success", false);
            result.put("actualizacion", "No Disponible");
        }

        return result;
    }

    @RequestMapping(value = {UrlConstants.BUSQUEDA_APLICACION_URL}, method = RequestMethod.GET)
    public ModelAndView busquedaPorApicacion(Locale locale, Model model, Principal principal, HttpServletRequest request) {

        ModelAndView mav = null;

        mav = new ModelAndView(ViewConstants.BUSQUEDA_POR_APLICACION_VIEW);
        return mav;
    }
    
    @RequestMapping(value = UrlConstants.BUSQUEDA_FOTO_URL, method = RequestMethod.GET)
    public ModelAndView homeFiltroFoto(Locale locale, Model model,
            Principal principal, HttpServletRequest request) {

        ModelAndView mav = null;
        mav = new ModelAndView(ViewConstants.BUSQUEDA_POR_FOTO_VIEW);
        return mav;
    }

}
