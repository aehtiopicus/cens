package com.aehtiopicus.cens.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.aehtiopicus.cens.domain.Puesto;
import com.aehtiopicus.cens.domain.RelacionLaboral;
import com.aehtiopicus.cens.domain.RelacionPuestoEmpleado;
import com.aehtiopicus.cens.domain.Sueldo;

@Service
public interface RelacionLaboralService {

	public List<RelacionLaboral> search(String clienteId, String apellido, String nombre, Integer page, Integer rows);
	
	public int getTotalRelacionesLaboralesFilterByCliente(String clienteIdStr, String apellido, String nombre);

	public List<Puesto> getPuestos();
	
	public Puesto savePuesto(Puesto puesto);

	public RelacionLaboral createAndSave(RelacionLaboral relacionLaboral);

	public RelacionLaboral update(RelacionLaboral relacionLaboral);

	public RelacionLaboral getRelacionLaboralById(Long relacionLaboralId);

	public void updateRelacionLaboralAndPuesto(RelacionPuestoEmpleado relacion);

	public RelacionPuestoEmpleado getRelacionPuestoEmpleado(Long relacionLaboralId);

	public Puesto getPuestoByNombre(String nombre);

	public List<Sueldo> aplicarIncrementoMasivo(Long clienteId, Date fechaInicio, Double incBasico, Double incPresentismo, String tipoIncremento);

	public List<RelacionLaboral> getRelacionesLaboralesVigentesByCliente(Long clienteId); 
	
	
}
