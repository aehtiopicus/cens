package com.aehtiopicus.cens.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.aehtiopicus.cens.domain.Empleado;
import com.aehtiopicus.cens.domain.Sueldo;
import com.aehtiopicus.cens.domain.Vacaciones;
import com.aehtiopicus.cens.enumeration.EstadoEmpleado;

@Service
public interface EmpleadoService {

	public Empleado saveEmpleado(Empleado empleado);
	
	public List<Empleado> getEmpleados();

	public int getTotalEmpleados(String cuil, String apellido, String cliente, String estado);

	public Empleado getEmpleadoByEmpleadoId(Long empleadoId);

	public List<Empleado> getEmpleadosPaginado(Integer page, Integer rows,
			String cuil, String apellido, String cliente, String estado);

	public long getCountEmpleadoByDNI(String dni, Long id);

	public List<Empleado> getAllEmpleados(String cuil, String apellido,
			String cliente, String estado);

	public Sueldo getSueldoActualByEmpleadoId(Long empleadoId);

	public Sueldo saveSueldo(Sueldo sueldo, Long empleadoId);

	public List<Vacaciones> getVacacionesPaginado(Integer page, Integer rows,Long clienteId);
	
	public List<Vacaciones> getVacacionesByEmpleado(Long empleadoId);

	public int getTotalVacacionesByEmpleado(Long empleadoId);

	public void saveVacaciones(Vacaciones vacacion, Long empleadoId);

	public Vacaciones getVacacionById(Long vacacionId);

	public void deleteVacaciones(Vacaciones vacaciones);

	public Long getCountEmpleadoByLegajo(Integer legajo, Long empleadoId);

	public List<Empleado> getEmpleadosByEstado(EstadoEmpleado estado);

	public boolean isOldEmpleado(String dni);

	public Empleado reincorporarEmpleado(Empleado empleado);

	public Empleado getEmpleadoByDni(String dni);
	
}
