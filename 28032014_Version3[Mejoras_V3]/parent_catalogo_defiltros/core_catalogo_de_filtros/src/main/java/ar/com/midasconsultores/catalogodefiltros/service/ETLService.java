package ar.com.midasconsultores.catalogodefiltros.service;



public interface ETLService {

//	@Scheduled(cron="0 53 17 * * *") // run at 6:30 of every day. 
//	@Scheduled(fixedDelay = 30000)
	public void executeETLProcessTask();
	
}
