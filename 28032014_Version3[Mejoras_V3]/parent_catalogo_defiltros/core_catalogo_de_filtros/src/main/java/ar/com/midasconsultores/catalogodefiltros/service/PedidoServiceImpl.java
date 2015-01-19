package ar.com.midasconsultores.catalogodefiltros.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import ar.com.midasconsultores.catalogodefiltros.domain.ClienteLista;
import ar.com.midasconsultores.catalogodefiltros.domain.Pedido;
import ar.com.midasconsultores.catalogodefiltros.domain.PedidoDetalle;
import ar.com.midasconsultores.catalogodefiltros.domain.QClienteLista;
import ar.com.midasconsultores.catalogodefiltros.domain.Users;
import ar.com.midasconsultores.catalogodefiltros.predicates.ClienteListaPredicates;
import ar.com.midasconsultores.catalogodefiltros.predicates.FiltroParaQueriesOptimizadasPredicates;
import ar.com.midasconsultores.catalogodefiltros.repository.ClienteListaRepository;
import ar.com.midasconsultores.catalogodefiltros.repository.PedidoRepository;
import ar.com.midasconsultores.catalogodefiltros.utils.CustomComparator;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.jpa.impl.JPAQuery;

@Service
public class PedidoServiceImpl implements PedidoService {

	private static final String NO_EXISTE_EL_PEDIDO_EXCEPTION = "No existe el pedido";

	private static final String DD_MM_YYYY_MASK = "dd-MM-yyyy";

	private static final String PEDIDO_DE_FILTROS_LABEL = "Pedido de Filtros ";

	private static final String TOTAL_LABEL = "Total        ";

	private static final String PRECIO_LABEL = "Precio";

	private static final String PRECIO_UNITARIO_LABEL = "Precio Unitario";

	private static final String CANTIDAD_LABEL = "Cantidad";

	private static final String DESCRIPCION_LABEL = "Descripcion";

	private static final String COD_CORTO_LABEL = "Cod. Corto";

	private static final String CODIGO_CLIENTE_LABEL = "Cliente: ";

	private static final String EMPTY_STRING = "";

	private static final String PRICE_MASK = "$#,##0.00";

	private static final String PROTECTED_KEY = "hssfsheet";

	private static final String WHITE_SPACE_STRING = " ";
	
	private static final String SPECIAL_CHARACTER_PATTERN = "\\W";
	
	private static final String UNDEFINED = "undefined";
	
	@Autowired
	private PedidoRepository pedidoRepository;
	@Autowired
	private FiltroService filtroService;
	@Autowired
	private ClienteListaRepository clienteListaRepository;
	@Autowired
	private PrecioAjusteService precioAjuste;

	@PersistenceContext
	EntityManager entityManager;

	@Autowired
	UsuarioService usuarioService;
	
	private HSSFDataFormat cf;
	private HSSFCellStyle currencyCellStyle;

	@Override
	public void guardarPedido(Pedido pedido) throws Exception {
		Pedido pedidoActual = getPedidoActual();

		if (pedidoActual == null) {
			pedidoActual = crearPedido();
		}
		actualizaDetalle(pedidoActual, pedido);
		
		pedidoRepository.save(pedidoActual);
	}

	@Override
	public void realizarPedido() throws Exception {
		Pedido pedidoActual = getPedidoActual();
		if (pedidoActual == null) {
			throw new Exception(NO_EXISTE_EL_PEDIDO_EXCEPTION);
		}
		pedidoActual.setBaja(true);
		pedidoActual.setFechaPedidoRealizado(new java.util.Date());
		pedidoRepository.save(pedidoActual);
	}

	@Override
	public Pedido obtenerPedidoActual() {
		return getPedidoActual();
	}

	@Override
	public HSSFWorkbook generarPedido(String codigoCliente) throws Exception {
		
		Pedido pedido = getPedidoActual();

		HSSFWorkbook workbook = new HSSFWorkbook();
		cf = workbook.createDataFormat();
		currencyCellStyle = workbook.createCellStyle();
		currencyCellStyle.setDataFormat(cf.getFormat(PRICE_MASK));

		HSSFSheet sheet = crearHoja(workbook, pedido.getFechaInicioPedido());
		sheet.protectSheet(PROTECTED_KEY);
		Map<Integer, Object[]> data = setData(pedido, codigoCliente);

		completaTablaExcel(data, sheet);
		Row row = sheet.createRow(data.size() + 1);
		Cell cell = row.createCell(5);
		cell.setCellFormula("SUM(E2:E" + (data.size()) + ")");

		cell.setCellStyle(currencyCellStyle);

		sheet.autoSizeColumn(cell.getColumnIndex());
		this.realizarPedido();
		return workbook;
	}

	private Map<Integer, Object[]> setData(Pedido pedido, String codigoCliente) {
		Map<Integer, Object[]> data = new HashMap<Integer, Object[]>();
		int fila = 1;
		data.put(fila, new Object[] {CODIGO_CLIENTE_LABEL, codigoCliente});
		fila++;
		data.put(fila, new Object[] { COD_CORTO_LABEL, DESCRIPCION_LABEL,
				CANTIDAD_LABEL, PRECIO_UNITARIO_LABEL, PRECIO_LABEL,
				TOTAL_LABEL });

		double ajuste;
		if(codigoCliente != null && !codigoCliente.isEmpty()){
			ajuste = precioAjuste.obtenerPorcentajeAjusteByCliente(codigoCliente);
		} else {
			ajuste = precioAjuste.obtenerPorcentajeAjuste();
		}
		for (PedidoDetalle pd : pedido.getDetalles()) {
			fila++;
			data.put(
					fila,
					new Object[] {
							pd.getFiltro().getCodigoCorto() + EMPTY_STRING,
							pd.getFiltro().getDescripcion(),
							pd.getCantidad(),
							precioAjuste.calcularPrecioAjustado(Double.parseDouble(pd.getPrecio()),ajuste), //El precio unitario depende del cliente
							pd.getCantidad() * (precioAjuste.calcularPrecioAjustado(Double.parseDouble(pd.getPrecio()),ajuste)) });
		}

		return data;
	}

	private double porcentajeAjuste() {
		double result = 0;
		try {

			Users user = (Users) SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal();

			ClienteLista cl = clienteListaRepository.findByCodigoClienteIgnoreCase(user
					.getCodigoDeUsuario());

			result = 1 + (Double.parseDouble(cl.getLista().getPorcentaje()) / 100);
		} catch (Exception e) {
			result = 1;
		}
		return result;
	}

	private HSSFSheet crearHoja(HSSFWorkbook workbook, Date fecha) {
		return workbook.createSheet(PEDIDO_DE_FILTROS_LABEL
				+ new SimpleDateFormat(DD_MM_YYYY_MASK).format(fecha));
	}

	private void completaTablaExcel(Map<Integer, Object[]> data, HSSFSheet sheet) {
		Set<Integer> keyset = data.keySet();
		int rownum = 0;
		for (Integer key : keyset) {
			Row row = sheet.createRow(rownum++);
			Object[] objArr = data.get(key);
			int cellnum = 0;
			for (Object obj : objArr) {
				Cell cell = row.createCell(cellnum++);
				if (obj instanceof Date) {
					cell.setCellValue((Date) obj);
				} else if (obj instanceof Boolean) {
					cell.setCellValue((Boolean) obj);
				} else if (obj instanceof String) {
					cell.setCellValue((String) obj);
				} else if (obj instanceof Double) {
					cell.setCellValue((Double) obj);
					cell.setCellStyle(currencyCellStyle);
				} else if (obj instanceof Integer) {
					cell.setCellValue((Integer) obj);
				}
				sheet.autoSizeColumn(cell.getColumnIndex());
			}

		}
	}

	private Pedido getPedidoActual() {
		Pedido pedido = pedidoRepository.findPedidoByBaja(false);
		if(pedido != null && pedido.getDetalles() != null){
			Collections.sort(pedido.getDetalles(), new CustomComparator());
		}	
		return pedido;
	}

	private Pedido crearPedido() {
		Pedido pedido = new Pedido();
		pedido.setBaja(false);
		pedido.setFechaInicioPedido(new java.util.Date());
		return pedido;
	}

	private void actualizaDetalle(Pedido actual, Pedido nuevo) {
		buscarFiltroDePedido(nuevo);
		agregarDetalles(actual, nuevo);
		removerDetalles(actual, nuevo);
	}

	private void removerDetalles(Pedido actual, Pedido nuevo) {

		boolean eliminarPedido = false;
		for (Iterator<PedidoDetalle> pedidoDetalleActual = actual.getDetalles()
				.iterator(); pedidoDetalleActual.hasNext();) {
			eliminarPedido = true;
			PedidoDetalle pdA = pedidoDetalleActual.next();
			for (PedidoDetalle pedidoDetalleNuevo : nuevo.getDetalles()) {
				if (pedidoDetalleNuevo.getFiltro().getId()
						.equals(pdA.getFiltro().getId())) {
					eliminarPedido = false;
					break;
				}
			}
			if (eliminarPedido) {
				pedidoDetalleActual.remove();
			}
		}

	}

	private void agregarDetalles(Pedido actual, Pedido nuevo) {
		boolean nuevoDetalle = true;
		for (PedidoDetalle pdNuevo : nuevo.getDetalles()) {
			nuevoDetalle = true;
			if (actual.getDetalles() != null && !actual.getDetalles().isEmpty()) {
				for (PedidoDetalle pdActual : actual.getDetalles()) {
					if (pdActual.getFiltro().getId()
							.equals(pdNuevo.getFiltro().getId())) {
						nuevoDetalle = false;
						if (pdActual.getCantidad() != pdNuevo.getCantidad()) {
							pdActual.setCantidad(pdNuevo.getCantidad());
							break;
						}
					}

				}
			}
			if (nuevoDetalle) {
				actual.getDetalles().add(pdNuevo);
			}
		}
	}

	private void buscarFiltroDePedido(Pedido pedido) {
		double ajuste = porcentajeAjuste();
		for (PedidoDetalle pedidoDetalle : pedido.getDetalles()) {
			pedidoDetalle.setFiltro(filtroService.get(pedidoDetalle.getFiltro()
					.getId()));
			
			if (pedidoDetalle != null
					&& pedidoDetalle.getFiltro() != null
					&& pedidoDetalle.getFiltro().getPrecioBase() != null
					&& !StringUtils.isEmpty(pedidoDetalle.getFiltro()
							.getPrecioBase().getPrecio())) {
				
				pedidoDetalle.setPrecio(String.valueOf(Double
						.parseDouble(pedidoDetalle.getFiltro().getPrecioBase().getPrecio()) * ajuste));
			}
			
		}
	}

	@Override
	public List<ClienteLista> getCodigoCliente(String codigoName,Users user) {
		BooleanBuilder booleanBuilder = new BooleanBuilder();
		List<ClienteLista> listaClientes = new ArrayList<ClienteLista>();
		String codigoLimpio = "";
		
		// String codigoCliente
		if (codigoName != null && !codigoName.equals(EMPTY_STRING) && !codigoName.equals(UNDEFINED)) {
			codigoLimpio = codigoName.trim()
					.replace(WHITE_SPACE_STRING, EMPTY_STRING)
					.replaceAll(SPECIAL_CHARACTER_PATTERN, EMPTY_STRING);
			
			booleanBuilder.and(ClienteListaPredicates.codigoCliente(codigoLimpio));
                        if(user.isVendedor()){
                            booleanBuilder.and(ClienteListaPredicates.vendedor(user.getCodigoDeUsuario()));
                        }
		}
		
		if (!booleanBuilder.hasValue()) {
			booleanBuilder.and(FiltroParaQueriesOptimizadasPredicates
					.forceZeroResults());
		}

		if (booleanBuilder.getValue() != null) {
			
			JPAQuery query = new JPAQuery(entityManager);

			QClienteLista qClienteLista = QClienteLista.clienteLista;

			JPAQuery queryFiltros = query.from(qClienteLista).where(booleanBuilder);

			List<String> listaClienteListaPaginadaCodigoCliente = queryFiltros
					.offset(0).limit(5)
					.orderBy(qClienteLista.codigoCliente.asc())
					.listDistinct(qClienteLista.codigoCliente);
//			List<ClienteLista> listaClienteListaPaginadaCodigoCliente2 = clienteListaRepository.findAll();  
//			listaClientes.addAll((Collection<? extends ClienteLista>) clienteListaRepository.findByCodigoCliente(listaClienteListaPaginadaCodigoCliente));
			for(String codigo : listaClienteListaPaginadaCodigoCliente){
				listaClientes.add(clienteListaRepository.findByCodigoClienteIgnoreCase(codigo));
			}
			

		} 

		return listaClientes;
	}
	
}
