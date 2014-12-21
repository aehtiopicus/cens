package com.aehtiopicus.cens.service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.aehtiopicus.cens.domain.BeneficioCliente;
import com.aehtiopicus.cens.domain.Cliente;
import com.aehtiopicus.cens.domain.InformeMensual;
import com.aehtiopicus.cens.domain.InformeMensualDetalle;
import com.aehtiopicus.cens.domain.RelacionLaboral;
import com.aehtiopicus.cens.domain.Sueldo;
import com.aehtiopicus.cens.domain.Usuario;

@Service
public interface InformeMensualService {

	public List<InformeMensual> searchInformesMensuales(Usuario usuario, String cliente, String periodo, Integer page, Integer rows);

	public int getNroTotalInformesMensuales(Usuario usuario, String cliente, String periodo);

	public InformeMensual crear(InformeMensual informeMensual, Boolean usarInformeAnterior);

	public InformeMensual getByClienteAndPeriodo(Cliente cliente, Date periodo);

	public InformeMensual getById(Long informeMensualId);

	public List<InformeMensual> getInformesNoConsolidadosPaginado(String cliente, String periodo, String gerente,
			String estado, List<Cliente> clientes) throws ParseException;

	public InformeMensualDetalle getDetalleById(Long id);

	public List<InformeMensualDetalle> updateDetalles(List<InformeMensualDetalle> detalles);

	public List<Cliente> getClientesSinInformes(List<Cliente> clientes, String periodo);

	public boolean enviarInforme(Long informeMensuyalId);

	public boolean rechazarInforme(Long informeMensualId);

	public boolean eliminarInforme(Long informeMensualId);
	
	/*
	 * Metodos llamados desde InformeMensualAspect cuando se agrega o elimina una relacion laboral 
	 * para que los InformesMensuales que estan abiertos aun se actualizen con los cambios
	 * en las relaciones laborales
	 */
	public void addInformeMensualDetalle(RelacionLaboral relacionLaboral);
	public void updateInformeMensualDetalle(RelacionLaboral relacionLaboral);

	/*
	 * Metodos llamados desde InformeMensualAspect cuando se agrega o elimina un beneficio 
	 * para que los InformesMensuales que estan abiertos aun se actualizen con los cambios
	 * en los beneficios
	 */
	public void addBeneficioToInformeMensualDetalles(BeneficioCliente beneficioCliente);
	public void updateBeneficioInInformeMensualDetalles(BeneficioCliente beneficioCliente);

	/*
	 * Metodo llamado desde InformeMensualAspect cuando se modifica el sueldo de un empleado
	 * que que los informesMensuales que estan abiertos aun se actualizen con el nuevo sueldo
	 */
	public void updateSalarioInInformeMensualDetalles(Sueldo sueldo);
	
	
}
