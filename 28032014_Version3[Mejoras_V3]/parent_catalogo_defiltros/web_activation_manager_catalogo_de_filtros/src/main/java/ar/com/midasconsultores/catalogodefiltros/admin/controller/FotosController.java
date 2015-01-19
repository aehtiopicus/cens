package ar.com.midasconsultores.catalogodefiltros.admin.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import ar.com.midasconsultores.catalogodefiltros.admin.services.FotosServiceImpl;

@Controller
public class FotosController {
	
	private static final Logger logger = LoggerFactory.getLogger(FotosController.class);
	
	private FotosServiceImpl fotosServices;
	
	
	
	public FotosServiceImpl getFotosServices() {
		return fotosServices;
	}


	@Autowired
	public void setFotosServices(FotosServiceImpl fotosServices) {
		this.fotosServices = fotosServices;
	}



	@Scheduled(cron="0 15 5 * * *") 
	public void cargarFotosEnFTP() throws Exception {
		logger.info("comenzando proceso de carga de fotos");
		fotosServices.cargarFotos();
		logger.info("proceso completo");
	
	}

}
