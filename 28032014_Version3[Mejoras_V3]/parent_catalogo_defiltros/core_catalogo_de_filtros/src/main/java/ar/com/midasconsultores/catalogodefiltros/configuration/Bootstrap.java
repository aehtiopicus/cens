/*
cargarTipoVinosC2(" * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.midasconsultores.catalogodefiltros.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ar.com.midasconsultores.catalogodefiltros.repository.FiltroParaQueriesOptimizadasRepository;
import ar.com.midasconsultores.catalogodefiltros.repository.FiltroRepository;

/**
 * 
 * @author cgaia
 */
@Component
public class Bootstrap implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	HardwareInformation hardwareInformation;

	@Autowired
	FiltroRepository filtroRepository;
	
	@Autowired
	FiltroParaQueriesOptimizadasRepository filtroParaQueriesOptimizadasRepository;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void onApplicationEvent(ContextRefreshedEvent event) {
//
//		Filtro f = new Filtro();
//		
//		f.setMarca("LA_MARCA");
//		f.setCodigoCorto("LA_MARCA");
//		f.setCodigoCortoLimpio("LAMARCA");
//		filtroRepository.save(f);
//		
//		FiltroParaQueriesOptimizadas fo = new FiltroParaQueriesOptimizadas();
//		
//		fo.setMarca("LA_MARCA");
//		fo.setCodigoCorto("LA_MARCA");
//		fo.setCodigoCortoLimpio("LAMARCA");
//		filtroParaQueriesOptimizadasRepository.save(fo);
//		
//		if (!hardwareInformation.validarSerial()) {
//			System.out
//					.println("No posee licencia vigente para esta aplicacion.");
//			System.exit(1);
//		} else {
//			InterpretadorDeSerial interpretadorDeSerial = new InterpretadorDeSerial();
//			System.out.println("Id: " + interpretadorDeSerial.getId() + " - "
//					+ interpretadorDeSerial.getCaducidad());
//			System.out
//					.println("No posee licencia vigente para esta aplicacion.");
//		}

	}

}
