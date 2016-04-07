package ar.com.midasconsultores.catalogodefiltros.validator;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import ar.com.midasconsultores.catalogodefiltros.configuration.HardwareInformation;
import ar.com.midasconsultores.catalogodefiltros.domain.Users;

@Component
public class CredencialesValidator {

	public boolean validar(Users usuario) {
		boolean result = false;
		if (StringUtils.isNotEmpty(usuario.getIdEquipo())
				&& StringUtils.isNotEmpty(usuario.getCodigoDeUsuario())
				&& StringUtils.isNotEmpty(usuario
						.getFechaDeCaducidadDeLicencia() + "")
				&& StringUtils.isNotEmpty(usuario.getFechaDeInicioDelicencia()
						+ "")) {
			try {
				//esta horrible pero parece que resuelve el problema
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (HardwareInformation.generarCodigoMaquina().equals(
					usuario.getIdEquipo())) {
				
				Date fechaInicio = new Date(
						usuario.getFechaDeInicioDelicencia());
				Date fechaFin = new Date(
						usuario.getFechaDeCaducidadDeLicencia());
				Date fechaActual = new Date();
				if (fechaInicio.before(fechaFin) && fechaFin.after(fechaActual) && fechaInicio.before(fechaActual)) {
					result = true;
				}
			}
		}
		
		return result;
	}
}