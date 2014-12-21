package com.aehtiopicus.cens.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aehtiopicus.cens.domain.Empleado;
import com.aehtiopicus.cens.domain.InformeConsolidado;
import com.aehtiopicus.cens.domain.InformeConsolidadoDetalle;
import com.aehtiopicus.cens.domain.InformeConsolidadoDetalleBeneficio;
import com.aehtiopicus.cens.domain.InformeConsolidadoStub;
import com.aehtiopicus.cens.domain.InformeMensual;
import com.aehtiopicus.cens.domain.InformeMensualDetalle;
import com.aehtiopicus.cens.domain.InformeMensualDetalleBeneficio;
import com.aehtiopicus.cens.domain.RelacionLaboral;
import com.aehtiopicus.cens.domain.Sueldo;
import com.aehtiopicus.cens.enumeration.EstadoEmpleado;
import com.aehtiopicus.cens.enumeration.InformeConsolidadoEstadoEnum;
import com.aehtiopicus.cens.enumeration.InformeConsolidadoTipoEnum;
import com.aehtiopicus.cens.enumeration.InformeMensualEstadoEnum;
import com.aehtiopicus.cens.repository.InformeConsolidadoDetalleBeneficioRepository;
import com.aehtiopicus.cens.repository.InformeConsolidadoDetalleRepository;
import com.aehtiopicus.cens.repository.InformeConsolidadoRepository;
import com.aehtiopicus.cens.repository.InformeConsolidadoStubRepository;
import com.aehtiopicus.cens.repository.InformeMensualRepository;
import com.aehtiopicus.cens.specification.InformeConsolidadoSpecification;
import com.aehtiopicus.cens.utils.PeriodoUtils;
import com.aehtiopicus.cens.utils.Utils;

@Service
@Transactional
public class InformeConsolidadoServiceImpl implements InformeConsolidadoService {

	@Value("${periodo.dias}") 
	protected Integer diasPeriodo;
	
	@Value("${periodo.horas}") 
	protected Integer horasPeriodo;

	@Value("${mes.liquidacion.vacaciones}") 
	protected Integer mesLiquidacionVacaciones;

	@Value("${retenciones.tope}") 
	protected Double topeRetenciones;
	
	@Value("${retenciones.os.porcentaje}") 
	protected Double retOSPorcentaje;
	          
	@Value("${retenciones.11y3.porcentaje1}") 
	protected Double ret11Porcentaje;
	@Value("${retenciones.11y3.porcentaje2}") 
	protected Double ret3Porcentaje;

	
	@Value("${contribucion.os.porcentaje}") 
	protected Double contOSPorcentaje;
	
	@Value("${contribucion.codigos}") 
	protected String contCodigos;

	@Value("${dia.limite.transferencia}") 
	protected Integer diaLimiteTransferencia;
	
	@Autowired
	protected InformeConsolidadoRepository informeConsolidadoRepository;

	@Autowired
	protected InformeConsolidadoDetalleRepository informeConsolidadoDetalleRepository;

	@Autowired
	protected InformeMensualRepository informeMensualRepository;
	
	@Autowired
	protected InformeConsolidadoDetalleBeneficioRepository informeConsolidadoDetalleBeneficioRepository;
	
	@Autowired
	protected EmpleadoService empleadoService;

	@Autowired
	protected InformeConsolidadoStubRepository informeConsolidadoStubRepository;

	
	private static final String GALICIA = "GALICIA";
	
	public void setEmpleadoService(EmpleadoService empleadoService) {
		this.empleadoService = empleadoService;
	}

	public void setDiaLimiteTransferencia(Integer diaLimiteTransferencia) {
		this.diaLimiteTransferencia = diaLimiteTransferencia;
	}

	public void setInformeMensualRepository(InformeMensualRepository informeMensualRepository) {
		this.informeMensualRepository = informeMensualRepository;
	}

	public void setInformeConsolidadoRepository(InformeConsolidadoRepository informeConsolidadoRepository) {
		this.informeConsolidadoRepository = informeConsolidadoRepository;
	}

	public void setInformeConsolidadoDetalleRepository(InformeConsolidadoDetalleRepository informeConsolidadoDetalleRepository) {
		this.informeConsolidadoDetalleRepository = informeConsolidadoDetalleRepository;
	}
	public void setDiasPeriodo(Integer diasPeriodo) {
		this.diasPeriodo = diasPeriodo;
	}

	public void setHorasPeriodo(Integer horasPeriodo) {
		this.horasPeriodo = horasPeriodo;
	}
	
	public void setInformeConsolidadoDetalleBeneficioRepository(InformeConsolidadoDetalleBeneficioRepository informeConsolidadoDetalleBeneficioRepository) {
		this.informeConsolidadoDetalleBeneficioRepository = informeConsolidadoDetalleBeneficioRepository;
	}

	public void setMesLiquidacionVacaciones(Integer mesLiquidacionVacaciones) {
		this.mesLiquidacionVacaciones = mesLiquidacionVacaciones;
	}

	public void setTopeRetenciones(Double topeRetenciones) {
		this.topeRetenciones = topeRetenciones;
	}

	public void setRetOSPorcentaje(Double retOSPorcentaje) {
		this.retOSPorcentaje = retOSPorcentaje;
	}

	public void setRet11Porcentaje(Double ret11Porcentaje) {
		this.ret11Porcentaje = ret11Porcentaje;
	}

	public void setRet3Porcentaje(Double ret3Porcentaje) {
		this.ret3Porcentaje = ret3Porcentaje;
	}
	public void setInformeConsolidadoStubRepository(
			InformeConsolidadoStubRepository informeConsolidadoStubRepository) {
		this.informeConsolidadoStubRepository = informeConsolidadoStubRepository;
	}
	public void setContOSPorcentaje(Double contOSPorcentaje) {
		this.contOSPorcentaje = contOSPorcentaje;
	}	
	public void setContCodigos(String contCodigos) {
		this.contCodigos = contCodigos;
	}

	@Override
	public List<InformeConsolidadoStub> searchInformesConsolidados(String periodoStr, Integer page, Integer rows) {
		Page<InformeConsolidadoStub> requestedPage = null; 
		
		if(page > 0){
			page = page - 1;
		}
		
		Date periodo = null;
		if(StringUtils.isNotEmpty(periodoStr)) {
			periodo = PeriodoUtils.getDateFormPeriodo(periodoStr);
		}
		
		Specifications<InformeConsolidadoStub> specs = null ;
		specs = getSpecificationsStub(periodo, specs);
		requestedPage = informeConsolidadoStubRepository.findAll(specs, constructPageSpecification(page,rows));	 
	
		return requestedPage.getContent();
	}

	@Override
	public List<InformeConsolidado> searchInformesConsolidados(String periodoStr) {
		Date periodo = null;
		if(StringUtils.isNotEmpty(periodoStr)) {
			periodo = PeriodoUtils.getDateFormPeriodo(periodoStr);
		}
		
		Specifications<InformeConsolidado> specs = null ;
		specs = getSpecifications(periodo, specs);
		List<InformeConsolidado> requestedPage = informeConsolidadoRepository.findAll(specs);	 
	
		return requestedPage;
	}
	

	@Override
	public int getNroTotalInformesConsolidados(String periodoStr) {
    	long cantUsers = 0;


		Date periodo = null;
		if(StringUtils.isNotEmpty(periodoStr)) {
			periodo = PeriodoUtils.getDateFormPeriodo(periodoStr);
		}
		
		Specifications<InformeConsolidadoStub> specs = null ;
		specs = getSpecificationsStub(periodo, specs);
    	
   		cantUsers = informeConsolidadoStubRepository.count(specs);
    	return (int) Math.ceil(cantUsers);
	}

	
	private Specifications<InformeConsolidadoStub> getSpecificationsStub(Date periodo, Specifications<InformeConsolidadoStub> specs){
		
		if(periodo != null) {
			specs = Specifications.where(InformeConsolidadoSpecification.stub_periodoEquals(periodo));

		}
		
		return specs;
	}	
	
	private Specifications<InformeConsolidado> getSpecifications(Date periodo, Specifications<InformeConsolidado> specs){
		
		if(periodo != null) {
			specs = Specifications.where(InformeConsolidadoSpecification.periodoEquals(periodo));

		}
		
		return specs;
	}
	
    private Pageable constructPageSpecification(int pageIndex, int row) {
        Pageable pageSpecification = new PageRequest(pageIndex, row, sortByPeriodoAsc());
     
        return pageSpecification;
    }
    

    private Sort sortByPeriodoAsc() {
        return new Sort(Sort.Direction.DESC, "periodo");
    }

	@Override
	public InformeConsolidado getByPeriodo(Date periodo) {
		return informeConsolidadoRepository.findByPeriodo(periodo);
	}

	private Double calcularSueldoBruto(InformeConsolidadoDetalle det) {
		Double bruto = 0d;
		if(det.getBasicoMes() != null) {
			bruto += det.getBasicoMes();
		}
				
		if(det.getAsistenciaPuntualidad() != null) {
			bruto += det.getAsistenciaPuntualidad();
		}
		if(det.getBeneficios() != null) {
			bruto += det.getTotalBeneficios();
		}
		if(det.getValorHsExtras100() != null) {
			bruto += det.getValorHsExtras100();
		}
		if(det.getValorHsExtras50() != null) {
			bruto += det.getValorHsExtras50();
		}
		
		if(det.getVacacionesValor() != null) {
			bruto += det.getVacacionesValor();
		}
		if(det.getSacValor() != null) {
			bruto += det.getSacValor();
		}
		
		return Utils.redondear2Decimales(bruto);
	}
	
	private Double calcularNeto(InformeConsolidadoDetalle det) {
		Double neto = det.getSueldoBruto();
		if(det.getRet11y3() != null) {neto -= det.getRet11y3();}
		if(det.getRetObraSocial() != null) {neto -= det.getRetObraSocial();}
		if(det.getRetGanancia() != null) {neto -= det.getRetGanancia();}
		
		return Utils.redondear2Decimales(neto);
	}
	
	private Double calcularNetoADepositar(InformeConsolidadoDetalle det) {
		Double netoADepositar = det.getNeto();

		if(det.getCelular() != null){netoADepositar -= det.getCelular();}
		if(det.getPrepaga() != null){netoADepositar -= det.getPrepaga();}
		if(det.getAdelantos() != null){netoADepositar -= det.getAdelantos();}
		if(det.getReintegros() != null){netoADepositar -= det.getReintegros();}
		
		return netoADepositar;
	}
	
	@Override
	public InformeConsolidado crearInformeSac(InformeConsolidado informe) {
		
		List<Empleado> empleados = empleadoService.getEmpleadosByEstado(EstadoEmpleado.ACTUAL);
		
		InformeConsolidadoDetalle icDetalle;
		for (Empleado empleado : empleados) {
			icDetalle = new InformeConsolidadoDetalle();
			icDetalle.setPeriodo(informe.getPeriodo());
			icDetalle.setEmpleado(empleado);
			
			Date fIniNovatium = icDetalle.getEmpleado().getFechaIngresoNovatium();
			Date fFinNovatium = icDetalle.getEmpleado().getFechaEgresoNovatium();
			Date fIniPeriodo = icDetalle.getPeriodo();
			Date fFinPeriodo = PeriodoUtils.getFechaFinPeriodo(informe.getPeriodo());
			

			//calcular SAC
			if(fIniPeriodo.getMonth()+1 == 6 || fIniPeriodo.getMonth()+1 == 12) {

				Sueldo mejorSueldoAnual = icDetalle.getEmpleado().getMejorSueldoAnual(icDetalle.getPeriodo().getYear(), fFinPeriodo.getTime());
				if(mejorSueldoAnual != null) {
					icDetalle.setSacBase(mejorSueldoAnual.getBasico());
				}
				
				//Obtener dias: 180 por defecto.. sino el proporcional
				if(fIniNovatium.getYear() < fIniPeriodo.getYear() && fFinNovatium == null) {
					icDetalle.setSacDias(180d);
				
				}else if(fIniPeriodo.getMonth()+1 == 12 && fIniNovatium.getYear() == fIniPeriodo.getYear() && fIniNovatium.getMonth()+1 < 7 && fFinNovatium == null) {
					icDetalle.setSacDias(180d);
				
				}else if(fFinNovatium == null) {
					Date proximoPeriodo = PeriodoUtils.getNextPeriodo(informe.getPeriodo());
					icDetalle.setSacDias(PeriodoUtils.getDiasEntreFechas(fIniNovatium, proximoPeriodo)-1d);
					if(icDetalle.getSacDias() > 180d) {icDetalle.setSacDias(180d);}
					
				}else {
					icDetalle.setSacDias(0d);
				}
				
				//calcular el SAC.
				if(icDetalle.getSacBase() != null && icDetalle.getSacDias() != null) {
					icDetalle.setSacValor(Utils.redondear2Decimales(icDetalle.getSacBase()/360 * icDetalle.getSacDias()));
				}
			}
			//------------
			
			icDetalle.setSueldoBruto(this.calcularSueldoBruto(icDetalle));
			icDetalle.setSueldoBrutoRemunerativo(icDetalle.getSueldoBruto());
			
			Double valRet;
			if(icDetalle.getSueldoBrutoRemunerativo() > topeRetenciones/2) {
				valRet = topeRetenciones/2;
			}else {
				valRet = icDetalle.getSueldoBrutoRemunerativo();
			}
			
			icDetalle.setRet11y3(Utils.redondear2Decimales(valRet * ret11Porcentaje/100) + Utils.redondear2Decimales(valRet * ret3Porcentaje/100));
			icDetalle.setRetObraSocial(Utils.redondear2Decimales(valRet * retOSPorcentaje/100));

			//calculos para columnas de cargas sociales
			icDetalle = this.calcularCargasSociales(icDetalle, icDetalle.getEmpleado());
			//----------------------------------------------------------------------------
			
			icDetalle.setNeto(this.calcularNeto(icDetalle));
			
			Double netoADepositar = this.calcularNetoADepositar(icDetalle);
			
			if(fIniNovatium.getYear()==fIniPeriodo.getYear() && fIniNovatium.getMonth()==fIniPeriodo.getMonth() && fIniNovatium.getDate() > diaLimiteTransferencia ) {
				icDetalle.setUsarCheque(true);
			}
			if(fFinNovatium != null && fFinNovatium.getYear()==fIniPeriodo.getYear() && fFinNovatium.getMonth()==fIniPeriodo.getMonth()) {
				icDetalle.setUsarCheque(true);
			}
			
			if(icDetalle.getUsarCheque()) {
				icDetalle.setCheque(netoADepositar);
			}else {
				icDetalle.setNetoADepositar(netoADepositar);
			}
			
			informe.getDetalle().add(icDetalle);
		}
		
		informeConsolidadoDetalleRepository.save(informe.getDetalle());
		informeConsolidadoRepository.save(informe);
		
		return informe;
	}
	
	@Override
	public InformeConsolidado crear(InformeConsolidado informeConsolidado) {
		List<InformeMensual> informesMensuales = informeMensualRepository.findByPeriodoAndEstado(informeConsolidado.getPeriodo(), InformeMensualEstadoEnum.ENVIADO);
		
		Map<Long, InformeConsolidadoDetalle> icDetalleAuxMap = new HashMap<Long, InformeConsolidadoDetalle>();
		Long idKey;
		
		Double topeRetencionesInforme = calcularTopeRetenciones(informeConsolidado);
		boolean existeInformeSac = existeInformeSac(informeConsolidado.getPeriodo());
		
		InformeConsolidadoDetalle icDetalle;
		if(informesMensuales != null && informesMensuales.size() > 0) {
			for (InformeMensual informeMensual : informesMensuales) {
				for (InformeMensualDetalle imDetalle : informeMensual.getDetalle()) {
					
					idKey = imDetalle.getRelacionLaboral().getEmpleado().getId();
					if(icDetalleAuxMap.containsKey(idKey)) {
						icDetalle = icDetalleAuxMap.get(idKey);
					}else {
						icDetalle = new InformeConsolidadoDetalle();
						informeConsolidado.getDetalle().add(icDetalle);
						icDetalleAuxMap.put(idKey, icDetalle);
					}

					icDetalle.setPeriodo(informeConsolidado.getPeriodo());
					
					if(icDetalle.getInformeMensualDetalle() != null && !icDetalle.getInformeMensualDetalle().equals(imDetalle)) {
						icDetalle.setInformeMensualDetalle(obtenerDetalleMensualMasReciente(icDetalle.getInformeMensualDetalle(), imDetalle));	
					}else {
						icDetalle.setInformeMensualDetalle(imDetalle);
					}
					
					//icDetalle.setSueldoBasico(imDetalle.getRelacionLaboral().getEmpleado().getSueldoVigente().getBasico());
					if(imDetalle.getSueldoBasico() == null) {						
						icDetalle.setSueldoBasico(imDetalle.getRelacionLaboral().getEmpleado().getSueldoEnPeriodo(informeConsolidado.getPeriodo()).getBasico());
					}else {
						icDetalle.setSueldoBasico(imDetalle.getSueldoBasico());
					}
					icDetalle.setAsistenciaPuntualidad(imDetalle.getPresentismo());
					
					icDetalle.setDiasTrabajados(this.calcularDiasTrabajados(icDetalle.getPeriodo(), imDetalle.getRelacionLaboral().getEmpleado()));
					
					icDetalle.setBasicoMes(Utils.redondear2Decimales(icDetalle.getSueldoBasico()/diasPeriodo * icDetalle.getDiasTrabajados()));
					
					/* Codigo agregado para evitar NullPointerException 
					 * Revisar logica de negocio y mapeo para ver si este 
					 * problema puede aparecer en otras partes de la aplicacion. */
					
					if(imDetalle.getHsExtrasALiquidarAl50() == null) 
						imDetalle.setHsExtrasALiquidarAl50(new Double(0.0));
					
					if(imDetalle.getHsExtrasALiquidarAl100() == null) 
						imDetalle.setHsExtrasALiquidarAl100(new Double(0.0));
					
					// Fin de codigo agregado
					
					if(icDetalle.getHsExtras50() != null) {
						icDetalle.setHsExtras50(icDetalle.getHsExtras50() + imDetalle.getHsExtrasALiquidarAl50());
					}else {
						icDetalle.setHsExtras50(imDetalle.getHsExtrasALiquidarAl50());
					}
					if(icDetalle.getHsExtras100() != null) {
						icDetalle.setHsExtras100(icDetalle.getHsExtras100() + imDetalle.getHsExtrasALiquidarAl100());						
					}else {
						icDetalle.setHsExtras100(imDetalle.getHsExtrasALiquidarAl100());												
					}
					
					
					
					if(icDetalle.getHsExtras50() != null) {
						if(icDetalle.getInformeMensualDetalle().getRelacionLaboral().getCliente().getHsExtrasConPresentismo()) {
							icDetalle.setValorHsExtras50(Utils.redondear2Decimales((icDetalle.getSueldoBasico() + icDetalle.getAsistenciaPuntualidad())/horasPeriodo * 1.5 * icDetalle.getHsExtras50()));
						}else {
							icDetalle.setValorHsExtras50(Utils.redondear2Decimales(icDetalle.getSueldoBasico()/horasPeriodo * 1.5 * icDetalle.getHsExtras50()));							
						}
							
					}
					if(icDetalle.getHsExtras100() != null) {
						if(icDetalle.getInformeMensualDetalle().getRelacionLaboral().getCliente().getHsExtrasConPresentismo()) {
							icDetalle.setValorHsExtras100(Utils.redondear2Decimales((icDetalle.getSueldoBasico() + icDetalle.getAsistenciaPuntualidad())/horasPeriodo * 2 * icDetalle.getHsExtras100()));

						}else {
							icDetalle.setValorHsExtras100(Utils.redondear2Decimales(icDetalle.getSueldoBasico()/horasPeriodo * 2 * icDetalle.getHsExtras100()));							
						}
					}
					
					if(imDetalle.getBeneficios().size() > 0) {
						InformeConsolidadoDetalleBeneficio icDetBeneficio;
						for (InformeMensualDetalleBeneficio imDetBeneficio : imDetalle.getBeneficios()) {
							if(imDetBeneficio.getBeneficio().getBeneficio().isRemunerativo()) {
								icDetBeneficio = new InformeConsolidadoDetalleBeneficio();
								icDetBeneficio.setBeneficio(imDetBeneficio.getBeneficio().getBeneficio());
								icDetBeneficio.setImporte(imDetBeneficio.getValor());
								icDetalle.getBeneficios().add(icDetBeneficio);
							}else {
								if(icDetalle.getReintegros() != null) {
									icDetalle.setReintegros(icDetalle.getReintegros() + imDetBeneficio.getValor());									
								}else {
									icDetalle.setReintegros(imDetBeneficio.getValor());
								}
							}
						}
						informeConsolidadoDetalleBeneficioRepository.save(icDetalle.getBeneficios());  
					}

					Date fIniNovatium = icDetalle.getInformeMensualDetalle().getRelacionLaboral().getEmpleado().getFechaIngresoNovatium();
					Date fFinNovatium = icDetalle.getInformeMensualDetalle().getRelacionLaboral().getEmpleado().getFechaEgresoNovatium();
					Date fIniPeriodo = icDetalle.getPeriodo();
					Date fFinPeriodo = PeriodoUtils.getFechaFinPeriodo(informeConsolidado.getPeriodo());
					
					Sueldo mejorSueldoAnual = icDetalle.getInformeMensualDetalle().getRelacionLaboral().getEmpleado().getMejorSueldoAnual(icDetalle.getPeriodo().getYear(), fFinPeriodo.getTime());
					if(mejorSueldoAnual != null) {
						icDetalle.setSacBase(mejorSueldoAnual.getBasico());
					}

					//calcular SAC
					if(fIniPeriodo.getMonth()+1 == 6 || fIniPeriodo.getMonth()+1 == 12) {	

						//si se creo el informe para sac anteriormente entonces los dias del sac de cada empleado
						//se cargan en 0 para el informe consolidado en cuestion.
						if(existeInformeSac) {
							icDetalle.setSacDias(0d);
						}else {					
							//Obtener dias: 180 por defecto.. sino el proporcional
							if(fIniNovatium.getYear() < fIniPeriodo.getYear() && fFinNovatium == null) {
								icDetalle.setSacDias(180d);
							
							}else if(fIniPeriodo.getMonth()+1 == 12 && fIniNovatium.getYear() == fIniPeriodo.getYear() && fIniNovatium.getMonth()+1 < 7 && fFinNovatium == null) {
								icDetalle.setSacDias(180d);
							
							}else if(fFinNovatium == null) {
								Date proximoPeriodo = PeriodoUtils.getNextPeriodo(informeMensual.getPeriodo());
								icDetalle.setSacDias(PeriodoUtils.getDiasEntreFechas(fIniNovatium, proximoPeriodo)-1d);
								if(icDetalle.getSacDias() > 180d) {icDetalle.setSacDias(180d);}
								
							}else {
								icDetalle.setSacDias(0d);
							}
						}
						//calcular el SAC.
						if(icDetalle.getSacBase() != null && icDetalle.getSacDias() != null) {
							icDetalle.setSacValor(Utils.redondear2Decimales(icDetalle.getSacBase()/360 * icDetalle.getSacDias()));
						}
					}
					//------------
					
					icDetalle.setSueldoBruto(this.calcularSueldoBruto(icDetalle));
					icDetalle.setSueldoBrutoRemunerativo(icDetalle.getSueldoBruto());
					
					Double valRet;
					if(icDetalle.getSueldoBrutoRemunerativo() > topeRetencionesInforme) {
						valRet = topeRetencionesInforme;
					}else {
						valRet = icDetalle.getSueldoBrutoRemunerativo();
					}
					
					icDetalle.setRet11y3(Utils.redondear2Decimales(valRet * ret11Porcentaje/100) + Utils.redondear2Decimales(valRet * ret3Porcentaje/100));
					icDetalle.setRetObraSocial(Utils.redondear2Decimales(valRet * retOSPorcentaje/100));

					//calculos para columnas de cargas sociales
					icDetalle = this.calcularCargasSociales(icDetalle, icDetalle.getInformeMensualDetalle().getRelacionLaboral().getEmpleado());
					//----------------------------------------------------------------------------
					
					icDetalle.setNeto(this.calcularNeto(icDetalle));
					
					Double netoADepositar = this.calcularNetoADepositar(icDetalle);
					
					if(fIniNovatium.getYear()==fIniPeriodo.getYear() && fIniNovatium.getMonth()==fIniPeriodo.getMonth() && fIniNovatium.getDate() > diaLimiteTransferencia ) {
						icDetalle.setUsarCheque(true);
					}
					if(fFinNovatium != null && fFinNovatium.getYear()==fIniPeriodo.getYear() && fFinNovatium.getMonth()==fIniPeriodo.getMonth()) {
						icDetalle.setUsarCheque(true);
					}
					
					if(icDetalle.getUsarCheque()) {
						icDetalle.setCheque(netoADepositar);
					}else {
						icDetalle.setNetoADepositar(netoADepositar);
					}
				}
			}
		}
		
		informeConsolidadoDetalleRepository.save(informeConsolidado.getDetalle());
		informeConsolidado = informeConsolidadoRepository.save(informeConsolidado);
		
		return informeConsolidado;
	}

	@Override
	public InformeConsolidadoDetalle calcularCargasSociales(InformeConsolidadoDetalle detalle, Empleado empleado) {
		Map<Integer, Double> codContratacionMap = getCodigosContratacionMap();
		detalle.setCodigoContratacionEmpleado(empleado.getCodigoContratacion());
		if(detalle.getCodigoContratacionEmpleado() != null) {
			detalle.setCont176510(detalle.getSueldoBrutoRemunerativo() * codContratacionMap.get(detalle.getCodigoContratacionEmpleado())/100);
			detalle.setContOS(detalle.getSueldoBrutoRemunerativo() * contOSPorcentaje/100);
		}else{
			detalle.setCont176510(null);
			detalle.setContOS(null);			
		}
		return detalle;
	}
	
	
	
	private Map<Integer, Double> getCodigosContratacionMap() {
		Map<Integer, Double> map = new HashMap<Integer, Double>();
		
		String[] codigos = contCodigos.split(";");
		String[] codDetalle;
		for (String codigo : codigos) {
			codDetalle = codigo.split("-");
			map.put(Integer.valueOf(codDetalle[0]), Double.valueOf(codDetalle[1]));
		}
		return map;
	}

	private InformeMensualDetalle obtenerDetalleMensualMasReciente(
			InformeMensualDetalle d1,
			InformeMensualDetalle d2) {
		
		RelacionLaboral rl1 = d1.getRelacionLaboral();
		RelacionLaboral rl2 = d2.getRelacionLaboral();
		
		if(rl1 != null && rl2 != null) {
			if(rl1.getFechaFin() == null && rl2.getFechaFin() != null) {
				return d1;
			}else if(rl2.getFechaFin() == null && rl1.getFechaFin() != null) {
				return d2;
			}else{
				if(rl1.getFechaFin().before(rl2.getFechaFin())) {
					return d2;
				}else {
					return d1;
				}
			}
		}else if(rl1 != null) {
			return d1;
		}else if(rl2 != null) {
			return d2;
		}
		
		return null;
	}

	private Double calcularTopeRetenciones(InformeConsolidado informe) {

		if(informe.getPeriodo().getMonth()+1 == 6 || informe.getPeriodo().getMonth()+1 == 12) {
			if(informe.getTipo().equals(InformeConsolidadoTipoEnum.SAC)) {
				return topeRetenciones/2;
			}else {
				if(existeInformeSac(informe.getPeriodo())){
					return topeRetenciones;
				}else {
					return topeRetenciones * 1.5;
				}
			}
		}
		
		return topeRetenciones;
	}
	
	private boolean existeInformeSac(Date periodo) {
		boolean existe = false;
		
		if(informeConsolidadoRepository.findByPeriodoAndTipo(periodo, InformeConsolidadoTipoEnum.SAC) != null) {
			existe = true;
		}
		
		return existe;
	}
	
	private Double calcularDiasTrabajados(Date periodo, Empleado empleado) {
		Date proximoPeriodo = PeriodoUtils.getNextPeriodo(periodo);
		
		Double dias = 30d;
		Double diasARestar = 0d;
		if(empleado.getFechaIngresoNovatium() != null && PeriodoUtils.fechaEnRangoFechas(empleado.getFechaIngresoNovatium(), periodo, proximoPeriodo)) {
			diasARestar = PeriodoUtils.getDiasEntreFechas(periodo, empleado.getFechaIngresoNovatium()).doubleValue() -1;
		}
		if(empleado.getFechaEgresoNovatium() != null && PeriodoUtils.fechaEnRangoFechas(empleado.getFechaEgresoNovatium(), periodo, proximoPeriodo)) {
			diasARestar += PeriodoUtils.getDiasEntreFechas(empleado.getFechaEgresoNovatium(), proximoPeriodo).doubleValue() -1;
		}
		
		return dias - diasARestar;
	}

	@Override
	public InformeConsolidado getById(Long informeConsolidadoId) {
		return informeConsolidadoRepository.findOne(informeConsolidadoId);
	}

	@Override
	public List<InformeConsolidadoDetalle> searchInformesConsolidadosByCliente(
			String periodo1, Long clienteId) {
		Date periodo = null;
		if(StringUtils.isNotEmpty(periodo1)) {
			periodo = PeriodoUtils.getDateFormPeriodo(periodo1);
		}
		return informeConsolidadoDetalleRepository.findByClienteAndPeriodo(periodo,clienteId);	 
	}

	@Override
	public InformeConsolidadoDetalle getDetalleById(Long detalleInformeId) {
		return informeConsolidadoDetalleRepository.findOne(detalleInformeId);
	}

	@Override
	public List<InformeConsolidadoDetalle> updateDetalles(List<InformeConsolidadoDetalle> detalles) {
		for (InformeConsolidadoDetalle detalle : detalles) {
			if(detalle.getBeneficios().size() > 0) {
				informeConsolidadoDetalleBeneficioRepository.save(detalle.getBeneficios());
			}
		}
		return informeConsolidadoDetalleRepository.save(detalles);
	}

	@Override
	public boolean finalizarInforme(Long informeConsolidadoId) {
		if(informeConsolidadoId != null) {
			InformeConsolidado informe = informeConsolidadoRepository.findOne(informeConsolidadoId);
			if(informe != null){
				informe.setEstado(InformeConsolidadoEstadoEnum.CONSOLIDADO);
				informeConsolidadoRepository.save(informe);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean eliminarInforme(Long informeConsolidadoId) {
		InformeConsolidado ic = informeConsolidadoRepository.findOne(informeConsolidadoId);
		if(ic == null) {
			return false;
		}
		//elimino los detalles
		for (InformeConsolidadoDetalle det : ic.getDetalle()) {
			informeConsolidadoDetalleRepository.delete(det);
		}
		//elimino el informe		
		informeConsolidadoRepository.delete(informeConsolidadoId);
		return true;
	}

	@Override
	public List<InformeConsolidadoDetalle> getDetallesByPeriodoAndBancoDistintoGalicia(
			String periodo1) {
		Date periodo = null;
		if(StringUtils.isNotEmpty(periodo1)) {
			periodo = PeriodoUtils.getDateFormPeriodo(periodo1);
		}
		return informeConsolidadoDetalleRepository.findByPeriodoAndClienteDistintoGalicia(periodo,GALICIA.toLowerCase());	 
	}	

	

	@Override
	public InformeConsolidado getByPeriodoAndTipo(Date periodo,	InformeConsolidadoTipoEnum tipo) {
		return informeConsolidadoRepository.findByPeriodoAndTipo(periodo, tipo);
	}

	@Override
	public List<InformeConsolidado> searchInformesConsolidados(InformeConsolidadoEstadoEnum estado) {
		return informeConsolidadoRepository.findByEstado(estado);
	}

	@Override
	public List<InformeConsolidadoDetalle> getDetallesAbiertosByEmpleado(Long empleadoId) {
		return informeConsolidadoDetalleRepository.obtenerDetallesAbiertosPorEmpleado(empleadoId);
	}

}
