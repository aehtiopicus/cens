package ar.com.midasconsultores.catalogodefiltros.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ar.com.midasconsultores.catalogodefiltros.domain.Referencias;
import ar.com.midasconsultores.catalogodefiltros.repository.ReferenciasRepository;


@Component
public class DataBaseStrategy {
	
	@Autowired
	ReferenciasRepository referenciasRepository;
	
	public DataBaseStrategy(){
		

	} 
	
	public final String getValueFromDB() {
		Referencias referencias = referenciasRepository.findOne(1l);

		return referencias == null ? "" : referencias.getValor();
	}
	
}
