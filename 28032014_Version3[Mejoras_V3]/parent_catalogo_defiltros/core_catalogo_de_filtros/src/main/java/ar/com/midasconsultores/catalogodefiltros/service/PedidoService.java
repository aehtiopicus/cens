package ar.com.midasconsultores.catalogodefiltros.service;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import ar.com.midasconsultores.catalogodefiltros.domain.ClienteLista;
import ar.com.midasconsultores.catalogodefiltros.domain.Pedido;
import ar.com.midasconsultores.catalogodefiltros.domain.Users;

public interface PedidoService {

	public void guardarPedido(Pedido p) throws Exception;
	public void realizarPedido() throws Exception;
	public Pedido obtenerPedidoActual();
	public HSSFWorkbook generarPedido(String codigoCliente) throws Exception;
	public List<ClienteLista> getCodigoCliente(String codigoName,Users user);
}
