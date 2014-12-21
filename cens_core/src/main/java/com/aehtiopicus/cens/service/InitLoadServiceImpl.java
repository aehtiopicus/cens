package com.aehtiopicus.cens.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aehtiopicus.cens.domain.Banco;
import com.aehtiopicus.cens.domain.Cliente;
import com.aehtiopicus.cens.domain.Empleado;
import com.aehtiopicus.cens.domain.EmpleadoViejoFecha;
import com.aehtiopicus.cens.domain.MotivoBaja;
import com.aehtiopicus.cens.domain.Nacionalidad;
import com.aehtiopicus.cens.domain.ObraSocial;
import com.aehtiopicus.cens.domain.Perfil;
import com.aehtiopicus.cens.domain.Prepaga;
import com.aehtiopicus.cens.domain.Puesto;
import com.aehtiopicus.cens.domain.RelacionLaboral;
import com.aehtiopicus.cens.domain.Sueldo;
import com.aehtiopicus.cens.domain.Usuario;
import com.aehtiopicus.cens.enumeration.EstadoCivil;
import com.aehtiopicus.cens.enumeration.EstadoClienteEnum;
import com.aehtiopicus.cens.enumeration.EstadoEmpleado;
import com.aehtiopicus.cens.enumeration.PerfilEnum;
import com.aehtiopicus.cens.repository.EmpleadoRepository;
import com.aehtiopicus.cens.repository.PerfilRepository;
import com.aehtiopicus.cens.repository.PuestoRepository;
import com.aehtiopicus.cens.repository.UsuarioRepository;

@Service
@Transactional
public class InitLoadServiceImpl implements InitLoadService {
	 private static final Logger logger = Logger.getLogger(InitLoadServiceImpl.class);
	
	
	@Autowired
	protected PerfilRepository perfilRepository;
	
	@Autowired
	protected UsuarioService usuarioService;
	
	@Autowired
	protected BancoService bancoService;
	
	@Autowired
	protected NacionalidadService nacionalidadService;
	
	@Autowired
	protected ObraSocialService obraSocialService;

	@Autowired
	protected PrepagaService prepagaService;
	
	@Autowired
	protected PuestoRepository puestoRepository;
	
	@Autowired
	protected BeneficioService beneficioService;
	
	@Autowired
	protected ClienteService clienteService;
	
	@Autowired
	EmpleadoService empleadoService;
	
	@Autowired
	RelacionLaboralService relacionLaboralService;
	
	public void setRelacionLaboralService(
			RelacionLaboralService relacionLaboralService) {
		this.relacionLaboralService = relacionLaboralService;
	}

	public void setEmpleadoService(EmpleadoService empleadoService) {
		this.empleadoService = empleadoService;
	}

	public void setClienteService(ClienteService clienteService) {
		this.clienteService = clienteService;
	}

	public void setPrepagaService(PrepagaService prepagaService) {
		this.prepagaService = prepagaService;
	}

	public void setObraSocialService(ObraSocialService obraSocialService) {
		this.obraSocialService = obraSocialService;
	}

	public void setNacionalidadService(NacionalidadService nacionalidadService) {
		this.nacionalidadService = nacionalidadService;
	}

	public void setBancoService(BancoService bancoService) {
		this.bancoService = bancoService;
	}

	public void setPerfilRepository(PerfilRepository perfilRepository) {
		this.perfilRepository = perfilRepository;
	}

	public void setUsuarioService(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	public void setPuestoRepository(PuestoRepository puestoRepository) {
		this.puestoRepository = puestoRepository;
	}
	
	public void setBeneficioService(BeneficioService beneficioService) {
		this.beneficioService = beneficioService;
	}

	@Override
	public void inicializarDataBase() {
		
		logger.info("Inicializando Base de Datos");
//
//		//Creando Perfiles
		logger.info("Creando Perfiles");
		List<Perfil> perfiles = new ArrayList<Perfil>();
		Perfil perfil;
		
		perfil = new Perfil();
		perfil.setNombre("Super Usuario");
		perfil.setPerfil(PerfilEnum.ADMINISTRADOR.getNombre());
		perfiles.add(perfil);
		
		perfil = new Perfil();
		perfil.setNombre("Recursos Humanos");
		perfil.setPerfil(PerfilEnum.RRHH.getNombre());
		perfiles.add(perfil);
		
		perfil = new Perfil();
		perfil.setNombre("Gerente de Operaciones");
		perfil.setPerfil(PerfilEnum.GTE_OPERACION.getNombre());
		perfiles.add(perfil);
		
		perfil = new Perfil();
		perfil.setNombre("Jefe de Operaciones");
		perfil.setPerfil(PerfilEnum.JEFE_OPERACION.getNombre());
		perfiles.add(perfil);
		
		perfil = new Perfil();
		perfil.setNombre("Administración");
		perfil.setPerfil(PerfilEnum.ADMINISTRACION.getNombre());
		perfiles.add(perfil);
		
		perfiles = perfilRepository.save(perfiles);
		
		//Creando usuario administrador
		logger.info("Creando Usuario Administrador");
		Usuario usuario = new Usuario();
		usuario.setEnabled(true);
		usuario.setUsername("admin");
		usuario.setPassword("21232f297a57a5a743894a0e4a801fc3");
		usuario.setNombre("admin");
		usuario.setApellido("admin");
		usuarioService.saveUsuario(usuario, perfiles.get(0));
		
		
		
		logger.info("Creando cliente novatium estructura");
		Cliente cliente = new Cliente();
		cliente.setFixed(true);
		cliente.setNombre("Novatium Estructura");
		cliente.setRazonSocial("Novatium Estructura");
		cliente.setEstadoClienteEnum(EstadoClienteEnum.Vigente);
		cliente.setGerenteOperacion(usuarioService.getUsuarioByUsername("admin"));
		
		Date d = new Date();
		d.setYear(100);
		d.setMonth(0);
		d.setDate(1);
		cliente.setFecha_alta(d);
		//cliente.setGerenteOperacion(usuario);
		clienteService.saveCliente(cliente);
		
		
		logger.info("Creando bancos");
		List<Banco> bancos = new ArrayList<Banco>();
		Banco banco = new Banco();
		banco.setNombre("Santander Rio");
		bancos.add(banco);
		Banco banco2 = new Banco();
		banco2.setNombre("HSBC");
		bancos.add(banco2);
		Banco banco3 = new Banco();
		banco3.setNombre("Patagonia");
		bancos.add(banco3);
		bancoService.saveBancos(bancos);
		Banco banco4 = new Banco();
		banco4.setNombre("Galicia");
		bancos.add(banco4);
		bancoService.saveBancos(bancos);
		
		logger.info("Creando nacionalidades");
		Nacionalidad nacionalidad = new Nacionalidad();
		nacionalidad.setNombre("Argentino");
		nacionalidadService.saveNacionalid(nacionalidad);
		Nacionalidad nacionalidad1 = new Nacionalidad();
		nacionalidad1.setNombre("Chileno");
		nacionalidadService.saveNacionalid(nacionalidad1);
		Nacionalidad nacionalidad2 = new Nacionalidad();
		nacionalidad2.setNombre("Brasilero");
		nacionalidadService.saveNacionalid(nacionalidad2);
		
		logger.info("Obra Social");
		ObraSocial obraSocial = new ObraSocial();
		obraSocial.setNombre("OSDE");
		obraSocialService.saveObraSocial(obraSocial);
		ObraSocial obraSocial1 = new ObraSocial();
		obraSocial1.setNombre("OSEP");
		obraSocialService.saveObraSocial(obraSocial1);
		
		logger.info("Prepaga");
		Prepaga prepaga = new Prepaga();
		prepaga.setNombre("Swits Medical");
		prepagaService.save(prepaga);
		
		
		logger.info("Creando Puestos de trabajo");
		List<Puesto> puestos = new ArrayList<Puesto>();
		puestos.add(crearPuesto("Data Entry"));
		puestos.add(crearPuesto("Analista Junior"));
		puestos.add(crearPuesto("Analista Senior"));
		puestos.add(crearPuesto("Diseñador Junior"));
		puestos.add(crearPuesto("Diseñador Senior"));
		puestos.add(crearPuesto("Desarrollador Junior"));
		puestos.add(crearPuesto("Desarrollador Senior"));
		puestos.add(crearPuesto("Project Manager"));
		puestos.add(crearPuesto("Lider de proyecto"));
		puestos.add(crearPuesto("Arquitecto"));
		puestos.add(crearPuesto("Gerente"));
		puestos.add(crearPuesto("Ingeniero de QA junior"));
		puestos.add(crearPuesto("Ingeniero de QA senior"));
		puestos.add(crearPuesto("Product Owner"));
		puestoRepository.save(puestos);
	
	}

	@Override
	public void updateHistorialEmpleados() {
		logger.info("Actualizando historico de empleados con esado en baja");
		List<Empleado> empleados = empleadoService.getEmpleadosByEstado(EstadoEmpleado.BAJA);
		EmpleadoViejoFecha historico;
		for (Empleado empleado : empleados) {
			
			historico = new EmpleadoViejoFecha();
			historico.setLegajo(empleado.getLegajo());
			historico.setFechaIngresoNovatium(empleado.getFechaIngresoNovatium());
			historico.setFechaEgresoNovatium(empleado.getFechaEgresoNovatium());

			
			if(empleado.getMotivoBaja() != null) {
				historico.setMotivo(empleado.getMotivoBaja());
			}
			
			empleado.addEmpleadoViejoFecha(historico);
			
			empleadoService.saveEmpleado(empleado);
			logger.info("Actualizado: leg:" + empleado.getLegajo() + " - " + empleado.getApellidos() + ", " + empleado.getNombres());
			
		}
		logger.info("Actualizacion finalizada.");

	}
	
	private Puesto crearPuesto(String nombre){
		Puesto p = new Puesto();
		p.setNombre(nombre);
		return p;
	}
	
	@Override
	public void cargarDatosPrueba(){
		cargarUsuarios();
		cargarClientes();
		cargarEmpleados();
		cargarSueldo();
	}

	private void cargarUsuarios() {
		logger.info("Creando usuarios...");
		Usuario usuario;
		List<Perfil> perfiles = new ArrayList<Perfil>();
		perfiles.add(perfilRepository.findByPerfil(PerfilEnum.GTE_OPERACION.getNombre()));
		perfiles.add(perfilRepository.findByPerfil(PerfilEnum.JEFE_OPERACION.getNombre()));
		perfiles.add(perfilRepository.findByPerfil(PerfilEnum.RRHH.getNombre()));
		perfiles.add(perfilRepository.findByPerfil(PerfilEnum.ADMINISTRACION.getNombre()));
		
		for (int i=1; i < 5 ;i++) {
			usuario = new Usuario();
			usuario.setEnabled(true);
			usuario.setUsername("usuario" + i);
			usuario.setPassword("21232f297a57a5a743894a0e4a801fc3");
			usuario.setNombre("usuario" + i);
			usuario.setApellido("usuario" + i);
			usuarioService.saveUsuario(usuario, perfiles.get(i-1));			
		}
		
		
	}
	
	private void cargarSueldo(){
		logger.info("Creando sueldos...");
		List<Empleado> empleados = empleadoService.getEmpleadosByEstado(EstadoEmpleado.ACTUAL);
		Date fechaINicio = new Date();
		fechaINicio.setMonth(1);
		Sueldo sueldo;
		for (Empleado empleado : empleados) {
			if(empleado.getSueldo() == null){
				sueldo  = new Sueldo();
				sueldo.setBasico(4500d);
				sueldo.setPresentismo(500d);
				sueldo.setFechaInicio(fechaINicio);
				empleadoService.saveSueldo(sueldo, empleado.getId());
			}
		}
		
	}
	
	private void cargarEmpleados() {
	/*	logger.info("Creando empleados...");
		Date fechaIngresoNovatium = new Date();
		fechaIngresoNovatium.setMonth(0);

		Date fechaIngresoCliente = new Date();
		fechaIngresoCliente.setMonth(1);

		
		for(Integer i=0; i<500; i++){
			//Creaacion de  empleados
			Empleado empleado = new Empleado();
			empleado.setLegajo(i+"2606"+i);
			empleado.setFechaIngresoNovatium(fechaIngresoNovatium);
			empleado.setApellidos("Ponce "+i);
			empleado.setNombres("Juan "+i);
			if(i < 10){
				empleado.setDni(i+"3085345");
				empleado.setCuil("203085345"+i+"1");
				empleado.setEstadoCivil(EstadoCivil.CASADO);
			}else{
				empleado.setDni(i.toString().substring(0,1)+"3085345");
				empleado.setCuil("203085345"+i.toString().substring(0,1)+"1");
				empleado.setEstadoCivil(EstadoCivil.SOLTERO);
			}
			
			empleado.setNacionalidad(nacionalidadService.getNacionalidadByName("Argentino"));
			empleado.setDomicilio("9 de Julio "+i);
			empleado.setTelFijo(i+"425426"+i);
			empleado.setCelular("1536244"+i);
			empleado.setTelUrgencias(i+"48754"+i);
			empleado.setContactoUrgencias("Ariel "+i);
			empleado.setMailPersonal("juan"+i+"@gmail.com");
			empleado.setMailPersonal("juan"+i+"@novatium.com");
			if(i < 100){
				empleado.setBanco(bancoService.findByNombre("Santander Rio"));	
				empleado.setCbu("1234587458765412024252");
			}
			if(i >= 100 && i < 300){
				empleado.setBanco(bancoService.findByNombre("Galicia"));
				empleado.setNroCuenta("425453214512");
				// para que no todos tengan el mismo numero de cuenta
				if(i < 10){
					empleado.setNroCuenta("42545321451"+i);	
				}else{
					empleado.setNroCuenta("42545321451"+i.toString().substring(0,1));	
				}
				if(i>=100 && i < 150){ // para tener registros del banco galicia con el mismo convenio
					empleado.setConvenio("Convenio Galicia ");	
				}else{
					empleado.setConvenio("Convenio Galicia "+i);
				}
				
			}else{
				empleado.setBanco(bancoService.findByNombre("HSBC"));	
				empleado.setCbu("1234587458765412028888");
			}
			empleado.setSucursal("Sucursal Centro");
			empleado.setObservaciones("Test"+i);
			empleado.setEstado(EstadoEmpleado.ACTUAL);
			empleadoService.saveEmpleado(empleado);
			RelacionLaboral rl = new RelacionLaboral();
			
			Cliente cliente = clienteService.getClienteByRazonSocial("Midas: "+ (i % 50));
			
			Puesto puesto = puestoRepository.findByNombreIgnoreCase("Diseñador Junior");
			rl.setCliente(cliente);
			rl.setPuesto(puesto);
			rl.setFechaInicio(fechaIngresoCliente);
			rl.setEmpleado(empleado);
			relacionLaboralService.createAndSave(rl);
			List<RelacionLaboral> relacionLaborales = new ArrayList<RelacionLaboral>();
			relacionLaborales.add(rl);
			empleado.setRelacionLaboral(relacionLaborales);
			empleadoService.saveEmpleado(empleado);
		}*/
	}

	private void cargarClientes() {
		logger.info("Creando clientes...");
		
		List<Usuario> gerentes = usuarioService.getUsuariosByPerfil(perfilRepository.findByPerfil(PerfilEnum.GTE_OPERACION.getNombre()));
		List<Usuario> jefes = usuarioService.getUsuariosByPerfil(perfilRepository.findByPerfil(PerfilEnum.JEFE_OPERACION.getNombre()));
		Date fechaAlta = new Date();
		fechaAlta.setMonth(2);
		
		for(int i=0; i<50; i++){
			Cliente cliente = new Cliente();
			cliente.setRazonSocial("Midas: "+i);
			cliente.setNombre("Midas: "+i);
			cliente.setGerenteOperacion(gerentes.get(0));
			cliente.setJefeOperacion(jefes.get(0));
			cliente.setEmail("midas"+i+"@consultores.com");
			cliente.setFecha_alta(fechaAlta);
			cliente.setDireccion("Isabel Catolica "+i);
			cliente.setTelefono(i+"425778"+i);
			cliente.setNombre_contacto("Isabel "+i);
			cliente.setEstadoClienteEnum(EstadoClienteEnum.Vigente);
			cliente.setEmailContacto("isabel"+i+"@gmail.com");
			clienteService.saveCliente(cliente);
		}
	}
}
