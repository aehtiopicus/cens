package ar.com.midasconsultores.catalogodefiltros.mappers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ar.com.midasconsultores.catalogodefiltros.domain.Filtro;
import ar.com.midasconsultores.catalogodefiltros.domain.Pedido;
import ar.com.midasconsultores.catalogodefiltros.domain.PedidoDetalle;
import ar.com.midasconsultores.catalogodefiltros.dto.PedidoDTO;
import ar.com.midasconsultores.catalogodefiltros.dto.PedidoListDTO;
import ar.com.midasconsultores.catalogodefiltros.service.PrecioAjusteService;

@Component
public class PedidoMapper {

	@Autowired
	private PrecioAjusteService precioAjuste;
	
	public Pedido convertirPedidoDTO(PedidoListDTO pedidoList){
		Pedido pedido = new Pedido();
		PedidoDetalle pd = null;
		Filtro f  = null;
		for(PedidoDTO pedidoDTO : pedidoList.getPedidoList()){
			pd = new PedidoDetalle();
			f = new Filtro();
			pd.setCantidad(pedidoDTO.getCantidad());
			f.setId(pedidoDTO.getId());
			pd.setFiltro(f);
			pedido.getDetalles().add(pd);
		}
		return pedido;
	}

	public List<PedidoDTO> convertirPedido(Pedido pedido, String codigoCliente) {
		List<PedidoDTO> listPedidoDTO = new ArrayList<PedidoDTO>();
		PedidoDTO dto = null;
		if(pedido != null && pedido.getDetalles()!=null && !pedido.getDetalles().isEmpty()){
			double ajuste;
			if(codigoCliente != null && !codigoCliente.isEmpty()){
				ajuste = precioAjuste.obtenerPorcentajeAjusteByCliente(codigoCliente);
			} else {
				ajuste = precioAjuste.obtenerPorcentajeAjuste();
			}
			
			for(PedidoDetalle detalle : pedido.getDetalles()){
				dto = new PedidoDTO();
				dto.setCantidad(detalle.getCantidad());
				dto.setCodigo(detalle.getFiltro().getCodigoCorto());
				dto.setDesc(detalle.getFiltro().getDescripcion());
				dto.setId(detalle.getFiltro().getId());
				
				dto.setPrecioUnitario(precioAjuste.calcularPrecioAjustado(Double.parseDouble(detalle.getPrecio()),ajuste));	
				
				dto.setPrecio(dto.getPrecioUnitario()*dto.getCantidad());
				listPedidoDTO.add(dto);
			}
		}
		return listPedidoDTO;
	}
	
	public List<PedidoDTO> convertirPedido(Pedido pedido) {
		return convertirPedido(pedido, null);
	}
	
	public Map<String, Object> map(List<PedidoDTO> pedidoList) {
		Map<String, Object> modelMap = new HashMap<String, Object>(2);		
		modelMap.put("data", pedidoList);
		modelMap.put("success", true);

		return modelMap;
	}

	public Pedido convertirPedidoDTO(PedidoDTO pedidoDto, Pedido pedido) {
		if(pedido==null){
			pedido = new Pedido();
		}
		PedidoDetalle pd = new PedidoDetalle();
		Filtro f = new Filtro();
		pd.setCantidad(pedidoDto.getCantidad());
		f.setId(pedidoDto.getId());
		pd.setFiltro(f);
		pedido.getDetalles().add(pd);
		return pedido;
	}

	public int getCantidad(Pedido pedido, long filtroId) {
	
		int cantidad = 0;
		if(pedido== null || pedido.getDetalles()== null || pedido.getDetalles().isEmpty()){
			cantidad =0;
		}else{
			for(PedidoDetalle pd : pedido.getDetalles()){
				if(pd.getFiltro().getId() == filtroId){
					cantidad = pd.getCantidad();
				}
			}
		}
		return cantidad;
		
	}

	public Pedido removerDetallePedidoDTO(PedidoDTO pedidoDto,
			Pedido pedido) {
		if(pedido!=null){
			Iterator<PedidoDetalle> iterator =pedido.getDetalles().iterator();
			PedidoDetalle detalle = null;
			while(iterator.hasNext()){
				detalle = iterator.next();
				if(detalle.getFiltro().getId().equals(pedidoDto.getId())){
					iterator.remove();
					break;
				}
			}
		}
		return pedido;
		
	}
}
