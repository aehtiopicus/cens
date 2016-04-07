package ar.com.midasconsultores.catalogodefiltros.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
import ar.com.midasconsultores.catalogodefiltros.domain.Filtro;
import ar.com.midasconsultores.catalogodefiltros.domain.FiltroParaQueriesOptimizadas;
import ar.com.midasconsultores.catalogodefiltros.domain.PrecioVenta;
import ar.com.midasconsultores.catalogodefiltros.dto.ActivationDataDTO;
import ar.com.midasconsultores.catalogodefiltros.dto.FiltroCantidadDTO;
import ar.com.midasconsultores.catalogodefiltros.dto.FiltroDTO;
import ar.com.midasconsultores.catalogodefiltros.dto.ValoresComboDTO;
import ar.com.midasconsultores.catalogodefiltros.mappers.FiltroMode;
import ar.com.midasconsultores.catalogodefiltros.mappers.FiltrosMapper;
import ar.com.midasconsultores.catalogodefiltros.mappers.ValoresCombosMapper;
import ar.com.midasconsultores.catalogodefiltros.service.FileSystemLocationService;
import ar.com.midasconsultores.catalogodefiltros.service.FiltroService;
import ar.com.midasconsultores.utils.ExcepcionDeNegocios;
import ar.com.midasconsultores.utils.extjs.ResponseMap;

/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping(value = FiltrosController.FILTROS_PATH)
public class FiltrosController {

	private static final String FILTRO_PRECIO = "/precio/{id}";

	static final String FILTROS_PATH = "filtros";
	
	private static final String INDEX_MAP = "index";

	private static final String ROOT_PATH = "/";

	private static final String LIST_PATH = "/list";
	
	private static final String LIST_PATH_FOTOS = "/listfotos";

	private static final String ERROR_LISTAR_FILTROS = "Ocurri&oacute un error al tratar de listar los filtros.";

	private static final String FILTRO = "filtro";

	private static final String ID_APLICACION = "idAplicacion";

	private static final String APLICACIONES_LIST_PATH = "aplicaciones/list";

	private static final String VALORES_PATH = "/valores";

	private static final String ERROR_LISTAR_APLICACIONES = "Ocurri&oacute un error al tratar de listar las aplicaciones.";

	private static final String CANTIDAD = "cantidad";

	private static final String BUSQUEDA = "busqueda";

	private static final String PEDIDOS_PATH = "/pedidos";

	private static final String PEDIDOS_COMBO_PATH = "/pedidos/combo";

	private static final String ERROR_LISTAR_MARCAS = "Ocurri&oacute un error al tratar de listar las marcas.";
	
	 @Autowired
	 private FileSystemLocationService fileSystemLocationService;
	 
	 private final static String file_separator = System.getProperty("file.separator");


	@Autowired
	private ResponseMap<FiltroDTO> extJS;

	private static final Logger logger = LoggerFactory
			.getLogger(FiltrosController.class);

	@Autowired(required = true)
	FiltroService filtroService;

	@Autowired
	FiltrosMapper filtroMapper;

	@Autowired
	private ResponseMap<ValoresComboDTO> extJSValoresCombos;

	@Autowired
	private ResponseMap<FiltroCantidadDTO> extJSFiltroCantidadDTO;

	@Autowired
	ValoresCombosMapper valoresCombosMapper;

	@RequestMapping(value = ROOT_PATH, method = RequestMethod.GET)
	public ModelAndView home(Locale locale, Model model, Principal principal) {

		ModelAndView mav = new ModelAndView(INDEX_MAP);

		return mav;
	}

	@RequestMapping(value = LIST_PATH)
	public @ResponseBody
	Map<String, Object> list(Locale locale, 
			@RequestParam int page,
			@RequestParam int rows,
			@RequestParam(required = false) Long idFiltro,
			@RequestParam(required = false) String tipoAplicacion,
			@RequestParam(required = false) String marcaAplicacion,
			@RequestParam(required = false) String modeloAplicacion,
			@RequestParam(required = false) String marcaFiltro,
			@RequestParam(required = false) String tipoFiltro,
			@RequestParam(required = false) String subTipoFiltro,
			@RequestParam(required = false) String codigoFiltro)
			throws Exception {

		int start = (page-1) * rows;
		int limit = rows;
		
		if (filtroMapper.checkUpdateNeeded(idFiltro, tipoAplicacion,
				marcaAplicacion, modeloAplicacion, marcaFiltro, tipoFiltro,
				subTipoFiltro, codigoFiltro)) {
			try {

				Page<FiltroDTO> filtroDto = null;

				Page<FiltroParaQueriesOptimizadas> filtros = null;
				Page<Filtro> reemplazos = null;
				long totalElements;
				if (idFiltro != null) {
					reemplazos = filtroService.listReemplazos(page - 1, start, limit, idFiltro);
					filtroDto = new PageImpl<FiltroDTO>(filtroMapper.mapToDTOList(reemplazos.getContent()));
					totalElements = reemplazos.getTotalElements();
				} else {
					filtros = filtroService.list(page - 1, start, limit,
							tipoAplicacion, marcaAplicacion, modeloAplicacion,
							marcaFiltro, tipoFiltro, subTipoFiltro,
							codigoFiltro, true);
					filtroDto = new PageImpl<FiltroDTO>(filtroMapper.mapToDTOListFOPT(filtros.getContent(),	codigoFiltro));
					totalElements = filtros.getTotalElements();
				}

				Map<String, Object> result = extJS.mapOK(filtroDto.getContent(), totalElements, page, rows);

				return result;

			} catch (ExcepcionDeNegocios e) {

				return extJS.mapError(e.getMessage());

			} catch (Exception e) {
				logger.error(e.toString());
				e.printStackTrace();
				return extJS.mapError(ERROR_LISTAR_FILTROS);
			}
		} else {
			return extJS.mapOK(new ArrayList<FiltroDTO>(), 0, page, rows);
		}

	}
	
	@Deprecated
	//@RequestMapping(value = LIST_PATH)
	public @ResponseBody
	Map<String, ? extends Object> list_old(Locale locale, @RequestParam int page,
			@RequestParam int start, @RequestParam int limit,
			@RequestParam(required = false) Long idFiltro,
			@RequestParam(required = false) String tipoAplicacion,
			@RequestParam(required = false) String marcaAplicacion,
			@RequestParam(required = false) String modeloAplicacion,
			@RequestParam(required = false) String marcaFiltro,
			@RequestParam(required = false) String tipoFiltro,
			@RequestParam(required = false) String subTipoFiltro,
			@RequestParam(required = false) String codigoFiltro)
			throws Exception {

		if (filtroMapper.checkUpdateNeeded(idFiltro, tipoAplicacion,
				marcaAplicacion, modeloAplicacion, marcaFiltro, tipoFiltro,
				subTipoFiltro, codigoFiltro)) {
			try {

				Page<FiltroDTO> filtroDto = null;

				Page<FiltroParaQueriesOptimizadas> filtros = null;
				Page<Filtro> reemplazos = null;
				long totalElements;
				if (idFiltro != null) {
					reemplazos = filtroService.listReemplazos(page - 1, start,
							limit, idFiltro);
					filtroDto = new PageImpl<FiltroDTO>(
							filtroMapper.mapToDTOList(reemplazos.getContent()));
					totalElements = reemplazos.getTotalElements();
				} else {
					filtros = filtroService.list(page - 1, start, limit,
							tipoAplicacion, marcaAplicacion, modeloAplicacion,
							marcaFiltro, tipoFiltro, subTipoFiltro,
							codigoFiltro, true);
					filtroDto = new PageImpl<FiltroDTO>(
							filtroMapper.mapToDTOListFOPT(filtros.getContent(),
									codigoFiltro));
					totalElements = filtros.getTotalElements();
				}

				Map<String, Object> result = extJS.mapOK(
						filtroDto.getContent(), totalElements);

				return result;

			} catch (ExcepcionDeNegocios e) {

				return extJS.mapError(e.getMessage());

			} catch (Exception e) {
				logger.error(e.toString());
				e.printStackTrace();
				return extJS.mapError(ERROR_LISTAR_FILTROS);
			}
		} else {
			return extJS.mapOK(new ArrayList<FiltroDTO>(), 0);
		}

	}

	@RequestMapping(value = APLICACIONES_LIST_PATH)
	public @ResponseBody
	Map<String, ? extends Object> listAplicacionesParaUnFiltro(
			Locale locale,
			@RequestParam int page,
			@RequestParam int start,
			@RequestParam int limit,
			@RequestParam(value = ID_APLICACION, required = false) Long idAplicacion,
			@RequestParam(value = FILTRO, required = false) String filtro)
			throws Exception {

		try {

			Page<FiltroDTO> filtroDTO = null;
			long total = 0;
			if (idAplicacion != null) {
				Page<Filtro> filtros = filtroService.listFiltrosAplicacion(
						page - 1, start, limit, idAplicacion, filtro);

				filtroDTO = new PageImpl<FiltroDTO>(
						filtroMapper.mapToDTOList(filtros.getContent()));

				total = filtros.getTotalElements();
			} else {
				filtroDTO = new PageImpl<FiltroDTO>(new ArrayList<FiltroDTO>());
			}

			return extJS.mapOK(filtroDTO.getContent(), total);

		} catch (ExcepcionDeNegocios e) {

			return extJS.mapError(e.getMessage());

		} catch (Exception e) {
			logger.error(e.toString());
			e.printStackTrace();
			return extJS.mapError(ERROR_LISTAR_APLICACIONES);
		}

	}

	@Deprecated
//	@RequestMapping(value = VALORES_PATH)
	public @ResponseBody
	Map<String, ? extends Object> listValores(Locale locale,
			@RequestParam int page, @RequestParam int start,
			@RequestParam int limit,
			@RequestParam(required = true) String nombreCombo,
			@RequestParam(required = false) String tipoAplicacion,
			@RequestParam(required = false) String marcaAplicacion,
			@RequestParam(required = false) String modeloAplicacion,
			@RequestParam(required = false) String marcaFiltro,
			@RequestParam(required = false) String tipoFiltro,
			@RequestParam(required = false) String subTipoFiltro,
			@RequestParam(required = false) String codigoFiltro)
			throws Exception {

		try {

			Page<ValoresComboDTO> tiposDTO = null;

			tiposDTO = new PageImpl<ValoresComboDTO>(
					valoresCombosMapper.mapToDTOList(filtroService.listValores(
							nombreCombo, tipoAplicacion, marcaAplicacion,
							modeloAplicacion, marcaFiltro, tipoFiltro,
							subTipoFiltro, codigoFiltro)));

			long total = tiposDTO.getTotalElements();

			logger.debug("products : " + tiposDTO.getContent());

			return extJSValoresCombos.mapOK(tiposDTO.getContent(), total);

		} catch (ExcepcionDeNegocios e) {

			return extJS.mapError(e.getMessage());

		} catch (Exception e) {
			logger.error(e.toString());
			e.printStackTrace();
			return extJS.mapError(ERROR_LISTAR_MARCAS);
		}

	}

	@RequestMapping(value = VALORES_PATH)
	public @ResponseBody Map<String, Object> listValoresV2(Locale locale,
			@RequestParam(required = true) String nombreCombo,
			@RequestParam(required = false) String tipoAplicacion,
			@RequestParam(required = false) String marcaAplicacion,
			@RequestParam(required = false) String modeloAplicacion,
			@RequestParam(required = false) String marcaFiltro,
			@RequestParam(required = false) String tipoFiltro,
			@RequestParam(required = false) String subTipoFiltro,
			@RequestParam(required = false) String codigoFiltro)
			throws Exception {

		try {

			Page<ValoresComboDTO> tiposDTO = null;

			tiposDTO = new PageImpl<ValoresComboDTO>(
					valoresCombosMapper.mapToDTOList(filtroService.listValores(
							nombreCombo, tipoAplicacion, marcaAplicacion,
							modeloAplicacion, marcaFiltro, tipoFiltro,
							subTipoFiltro, codigoFiltro)));

			long total = tiposDTO.getTotalElements();

			logger.debug("products : " + tiposDTO.getContent());

			return extJSValoresCombos.mapOK(tiposDTO.getContent(), total);

		} catch (ExcepcionDeNegocios e) {

			return extJS.mapError(e.getMessage());

		} catch (Exception e) {
			logger.error(e.toString());
			e.printStackTrace();
			return extJS.mapError(ERROR_LISTAR_MARCAS);
		}

	}

	
	@RequestMapping(value = FILTRO_PRECIO)
	public @ResponseBody
	Map<String, ? extends Object> obtenerDatosFiltro(@PathVariable("id") long id) {
		FiltroDTO dto = filtroMapper.map(filtroService.get(id), FiltroMode.FILTRO_PRECIO);
		return extJS.mapOK(Arrays.asList(dto), 1l);
	}

	@RequestMapping(value = PEDIDOS_COMBO_PATH)
	public @ResponseBody
	Map<String, ? extends Object> obtenerDatosComboPedido(
//			@RequestParam(value = BUSQUEDA, required = false) String busqueda)
			@RequestParam(value = "term", required = false) String busqueda) {

		List<Filtro> filtroList = filtroService.getFiltros(busqueda);
		List<ValoresComboDTO> valoresCombo = valoresCombosMapper
				.mapFiltroToDTOList(filtroList);
		return extJSValoresCombos.mapOK(valoresCombo, valoresCombo.size());

	}

	@RequestMapping(value = PEDIDOS_PATH)
	public @ResponseBody
	Map<String, ? extends Object> obtenerFiltroPedido(
			@RequestParam(value = CANTIDAD, required = false) String cantidad,
			@RequestParam(value = BUSQUEDA, required = false) String busqueda) {

		FiltroCantidadDTO fDto = (FiltroCantidadDTO) filtroMapper.map(
				(filtroService.get(Long.parseLong(busqueda))),
				FiltroMode.FILTRO_CANTIDAD);

		fDto.setCantidad(cantidad);

		return extJSFiltroCantidadDTO.mapOK(fDto);

	}
	
	@RequestMapping(value = LIST_PATH_FOTOS)
	public @ResponseBody
	Map<String, Object> listConImagenes(Locale locale, 
			@RequestParam int page,
			@RequestParam int rows,
			@RequestParam(required = false) Long idFiltro,
			@RequestParam(required = false) String tipoAplicacion,
			@RequestParam(required = false) String marcaAplicacion,
			@RequestParam(required = false) String modeloAplicacion,
			@RequestParam(required = false) String marcaFiltro,
			@RequestParam(required = false) String tipoFiltro,
			@RequestParam(required = false) String subTipoFiltro,
			@RequestParam(required = false) String codigoFiltro)
			throws Exception {

		int start = (page-1) * rows;
		int limit = rows;
		
		if (filtroMapper.checkUpdateNeeded(idFiltro, tipoAplicacion,
				marcaAplicacion, modeloAplicacion, marcaFiltro, tipoFiltro,
				subTipoFiltro, codigoFiltro)) {
			try {
				String externalFilePath = fileSystemLocationService.getWarFileSystemLocation() + file_separator + "imagenes" + file_separator;
				
				Page<FiltroDTO> filtroDto = null;

				Page<FiltroParaQueriesOptimizadas> filtros = null;
				Page<Filtro> reemplazos = null;
				long totalElements;
				if (idFiltro != null) {
					reemplazos = filtroService.listReemplazos(page - 1, start, limit, idFiltro);
					filtroDto = new PageImpl<FiltroDTO>(filtroMapper.mapToDTOList(reemplazos.getContent()));
					totalElements = reemplazos.getTotalElements();
				} else {
					filtros = filtroService.list(page - 1, start, limit,
							tipoAplicacion, marcaAplicacion, modeloAplicacion,
							marcaFiltro, tipoFiltro, subTipoFiltro,
							codigoFiltro, true);
					filtroDto = new PageImpl<FiltroDTO>(filtroMapper.mapToDTOListFOPT(filtros.getContent(),	codigoFiltro,externalFilePath));
					totalElements = filtros.getTotalElements();
				}

				Map<String, Object> result = extJS.mapOK(filtroDto.getContent(), totalElements, page, rows);

				return result;

			} catch (ExcepcionDeNegocios e) {

				return extJS.mapError(e.getMessage());

			} catch (Exception e) {
				logger.error(e.toString());
				
				return extJS.mapError(ERROR_LISTAR_FILTROS);
			}
		} else {
			return extJS.mapOK(new ArrayList<FiltroDTO>(), 0, page, rows);
		}

	}

}
