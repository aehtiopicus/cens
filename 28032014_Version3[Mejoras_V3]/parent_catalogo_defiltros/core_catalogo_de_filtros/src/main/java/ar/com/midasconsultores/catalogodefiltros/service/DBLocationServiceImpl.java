package ar.com.midasconsultores.catalogodefiltros.service;

import java.sql.ResultSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DBLocationServiceImpl implements DBLocationService {

	@Autowired
	private PostgresConnectorService pgConnectorService;

	@Override
	public String obtenerRutaInstalacion() throws Exception {
		String result = null;
		try {
			pgConnectorService.iniciarConexion();
			ResultSet rs = pgConnectorService
					.ejecutarConsulta("SELECT setting FROM pg_settings WHERE name = 'data_directory'");
			if (rs.next()) {
				result = rs.getString("setting");
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error al leer parametros de la base de datos");
		} finally {
			pgConnectorService.closeConnection();
		}
		return result;
	}

	@Override
	public void restaurarDump(String sqlSentence) throws Exception {
		try {
			pgConnectorService.iniciarConexion();
			if (!pgConnectorService.ejecutar(sqlSentence)) {
				throw new Exception("Error al ejecutar el dump");
			}
		} finally {
			pgConnectorService.closeConnection();
		}

	}

}
