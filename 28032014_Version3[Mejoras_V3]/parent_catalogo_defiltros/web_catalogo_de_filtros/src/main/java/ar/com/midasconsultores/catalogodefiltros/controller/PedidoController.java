package ar.com.midasconsultores.catalogodefiltros.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.security.Principal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import ar.com.midasconsultores.catalogodefiltros.configuration.UrlConstants;
import ar.com.midasconsultores.catalogodefiltros.domain.ClienteLista;
import ar.com.midasconsultores.catalogodefiltros.domain.Filtro;
import ar.com.midasconsultores.catalogodefiltros.domain.Pedido;
import ar.com.midasconsultores.catalogodefiltros.domain.Users;
import ar.com.midasconsultores.catalogodefiltros.dto.FiltroDTO;
import ar.com.midasconsultores.catalogodefiltros.dto.PedidoCantidadDTO;
import ar.com.midasconsultores.catalogodefiltros.dto.PedidoDTO;
import ar.com.midasconsultores.catalogodefiltros.dto.PedidoListDTO;
import ar.com.midasconsultores.catalogodefiltros.dto.ValoresComboDTO;
import ar.com.midasconsultores.catalogodefiltros.mappers.PedidoMapper;
import ar.com.midasconsultores.catalogodefiltros.mappers.ValoresCombosMapper;
import ar.com.midasconsultores.catalogodefiltros.service.PedidoService;
import ar.com.midasconsultores.catalogodefiltros.service.UsuarioService;
import ar.com.midasconsultores.utils.extjs.ResponseMap;

@Controller
@RequestMapping(value = UrlConstants.PEDIDO)
public class PedidoController {

	private static final String VENDEDOR = "#{configProperties['vendedor']}";
	
	@Value(VENDEDOR)
	private Boolean vendedor;
	
	private static final String CONTENT_TYPE_APPLICATION_JSON = "content-type=application/json";
	
	@Autowired
	private PedidoService pedidoService;
	@Autowired
	private PedidoMapper pedidoMapper;
	@Autowired
	private ResponseMap<PedidoCantidadDTO> extJSFiltroCantidad;
	@Autowired
	private ResponseMap<PedidoDTO> extJSPedido;
	@Autowired
	private ResponseMap<ValoresComboDTO> extJSValoresCombos;
	@Autowired
	private ValoresCombosMapper valoresCombosMapper;
	@Autowired
	private UsuarioService usuarioService;
	
	private static final String BUSQUEDA = "term";
	private static final String CODIGO_CLIENTE_PARAM = "codigo";
	
	private static final String TEXT_PLAIN = "application/x-msdownload";
//	private static final String TEXT_PLAIN = "application/vnd.ms-excel";

	/**
	 * Metodo principal para llamar a pedidos. Abre pedidos.jsp
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "", "/" }, method = RequestMethod.GET)
	public ModelAndView abrirPedido(HttpServletRequest request, Principal principal,
			HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("pedido_v2");
                Users user = usuarioService.getUsuario(principal.getName());
		vendedor = user.isVendedor();
		String codigoCliente = "-";
		codigoCliente = usuarioService.getUsuario(principal.getName()).getCodigoDeUsuario();
		mav.addObject("vendedor",vendedor);
                if(vendedor){
                    mav.addObject("codigoCliente","");
                }else{
                    mav.addObject("codigoCliente",codigoCliente);
                }

		return mav;
	}

	@RequestMapping(value = UrlConstants.PEDIDO_ACTUAL)
	public @ResponseBody
	Map<String, Object> obtenerPedidoActual(HttpServletRequest request,
			@RequestParam int page,
			@RequestParam int rows,
			@RequestParam(value = CODIGO_CLIENTE_PARAM, required = false) String codigoCliente,
			HttpServletResponse response) {

		Page<PedidoDTO> pedidoDTO = null;
		List<PedidoDTO> pedidoList = pedidoMapper.convertirPedido(pedidoService.obtenerPedidoActual(), codigoCliente);
		pedidoDTO = new PageImpl<PedidoDTO>(pedidoList);		
		return extJSPedido.mapOK(pedidoDTO.getContent(), pedidoDTO.getTotalElements(), page, rows);

	}

	@RequestMapping(value = UrlConstants.PEDIDO_GUARDAR, method = RequestMethod.POST, headers = { CONTENT_TYPE_APPLICATION_JSON })
	public @ResponseBody
	Map<String, ? extends Object> guardarPedidoActual(
			HttpServletRequest request, HttpServletResponse response,
			@RequestBody PedidoListDTO pedidoList) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Pedido pedido = pedidoMapper.convertirPedidoDTO(pedidoList);
			pedidoService.guardarPedido(pedido);
			result.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", false);
		}
		return result;
	}
	
	@RequestMapping(value = UrlConstants.PEDIDO_AGREGAR, method = RequestMethod.POST, headers = { CONTENT_TYPE_APPLICATION_JSON })
	public @ResponseBody
	Map<String, ? extends Object> agregarPedidoActual(
			HttpServletRequest request, HttpServletResponse response,
			@RequestBody PedidoDTO pedidoDto) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Pedido pedido = pedidoService.obtenerPedidoActual();
			pedido = pedidoMapper.convertirPedidoDTO(pedidoDto, pedido);
			pedidoService.guardarPedido(pedido);
			result.put("success", true);
			result.put("cantidad", pedidoDto.getCantidad());
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", false);
		}
		return result;
	}
	
	@RequestMapping(value = UrlConstants.PEDIDO_GENERAR, method = RequestMethod.GET)
	public @ResponseBody
	Map<String, ? extends Object> generarPedido(
			@RequestParam(value = CODIGO_CLIENTE_PARAM, required = false) String codigoCliente,
			Model model,	
			Principal principal,
			HttpServletResponse response, 
			HttpServletRequest request) {	
			
		String codigo;
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			if(vendedor){
				codigo = codigoCliente;
			} else {
				codigo = usuarioService.getUsuario(principal.getName()).getCodigoDeUsuario();
			}
			HSSFWorkbook excel=pedidoService.generarPedido(codigo);

			OutputStream output = response.getOutputStream();
			excel.write(output);			
			response.setContentType(TEXT_PLAIN);
			response.setHeader("Content-Disposition", "attachment; filename=\""+"pedido.xls"+"\"");

			response.flushBuffer();
           result.put("success", true);
		} catch (FileNotFoundException e) {
			result.put("success", false);
			e.printStackTrace();
		} catch (IOException e) {
			result.put("success", false);
			e.printStackTrace();

		}catch (Exception e){
			result.put("success", false);
		}
		
		return result;
	}
	@RequestMapping(value = UrlConstants.PEDIDO_CANTIDAD)
	public @ResponseBody Map<String, ? extends Object> obtenerCantidadPedida(@RequestParam("filtroId") String filtro){
		int cantidad = pedidoMapper.getCantidad(pedidoService.obtenerPedidoActual(), Long.parseLong(filtro));
			PedidoCantidadDTO dto = new PedidoCantidadDTO();
			dto.setCantidad(cantidad);
		return extJSFiltroCantidad.mapOK(Arrays.asList(dto));
	}
	
	@RequestMapping(value = UrlConstants.PEDIDO_ELIMINAR_DETALLE, method = RequestMethod.POST, headers = { CONTENT_TYPE_APPLICATION_JSON })
	public @ResponseBody Map<String, ? extends Object> eliminarDetallePedido(
			HttpServletRequest request, HttpServletResponse response,
			@RequestBody PedidoDTO pedidoDto) {
		Map<String,Object> result = new HashMap<String, Object>();
		try{
			Pedido pedido = pedidoMapper.removerDetallePedidoDTO(pedidoDto, pedidoService.obtenerPedidoActual());
			pedidoService.guardarPedido(pedido);
			result.put("success", true);
			result.put("cantidad", 0);
		}catch(Exception e){
			e.printStackTrace();
			result.put("success", false);
		}
		return result;
	}

	@RequestMapping(value = UrlConstants.PEDIDO_CLIENTES_COMBO_PATH)
	public @ResponseBody
	Map<String, ? extends Object> obtenerDatosComboCliente(
			@RequestParam(value = BUSQUEDA, required = true) String busqueda,Principal principal) {                
		List<ClienteLista> clienteList = pedidoService.getCodigoCliente(busqueda,usuarioService.getUsuario(principal.getName()));
		List<ValoresComboDTO> valoresCombo = valoresCombosMapper.mapClienteToDTOList(clienteList);
		return extJSValoresCombos.mapOK(valoresCombo, valoresCombo.size());

	}
	
	@RequestMapping(value = UrlConstants.PEDIDO_CODIGO_CLIENTE)
	public @ResponseBody Map<String, ? extends Object> obtenerCodigoCliente(Principal principal){
		Users clienteActual= usuarioService.getUsuario(principal.getName());
		Map<String, Object> modelMap = new HashMap<String, Object>(3);
		modelMap.put("codigo", clienteActual.getCodigoDeUsuario());
		modelMap.put("success", true);
		return modelMap;
	}
	
}
