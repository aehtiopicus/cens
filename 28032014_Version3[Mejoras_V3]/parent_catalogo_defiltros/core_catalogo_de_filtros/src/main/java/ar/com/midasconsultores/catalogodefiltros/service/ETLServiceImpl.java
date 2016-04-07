package ar.com.midasconsultores.catalogodefiltros.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ETLServiceImpl implements ETLService {

	private static final Logger logger = LoggerFactory
            .getLogger(ETLServiceImpl.class);
	
	@Value("${pdi.path}") private String pdiPath;
    @Value("${repository.name}") private String repoName;
    @Value("${repository.directory}") private String repoDir;
    @Value("${job.file.name}") private String jobName;
    @Value("${repository.user}") private String repoUser;
    @Value("${repository.pass}") private String repoPass;
    @Value("${propertie.file.path}") private String propertieFile;
    
    @Override
//    @Scheduled(cron="0 30 6 * * *") // run at 6:30 of every day. 
	public void executeETLProcessTask() {
    	String cmd = "cmd /C "+pdiPath+"\\kitchen.bat" +
				" -rep:"+repoName+
				" -dir:"+repoDir+
				" -job:"+jobName+
				" -user:"+repoUser+
				" -pass:"+repoPass+
				" -level:Basic"+
				" -param:"+propertieFile;

		try {
			Process p = Runtime.getRuntime().exec(cmd);
			BufferedReader in = new BufferedReader(new InputStreamReader(
					p.getInputStream()));
			String line = null;
			while ((line = in.readLine()) != null) {
				logger.info(line);
//				System.out.println(line);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		
    }

}
