package ar.com.midasconsultores.catalogodefiltros.mappers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import ar.com.midasconsultores.catalogodefiltros.domain.Users;
import ar.com.midasconsultores.catalogodefiltros.dto.UsuarioDTO;

/**
 *
 * @author cgaia
 */
@Component
public class UsuariosMapper {

    private static final String FORMATO_FECHA = "dd/MM/yyyy";
	private static final String FECHA_BASE = "08/08/2013";

	public UsuarioDTO map(Users users) {

        UsuarioDTO usuarioDTO = new UsuarioDTO();

        usuarioDTO.setNombreUsuario(users.getUsername());
        usuarioDTO.setEmail(users.getEmail());
        usuarioDTO.setEnabled(users.isEnabled());
        usuarioDTO.setEliminar(false);

        return usuarioDTO;

    }

    public List<UsuarioDTO> mapToDTOList(List<Users> users) {
        List<UsuarioDTO> usuarioDTOs = new ArrayList<UsuarioDTO>();
        for (Users u : users) {
            usuarioDTOs.add(this.map(u));
        }
        return usuarioDTOs;
    }

    public Users map(UsuarioDTO usuarioDTO) {

        Users users = new Users();

        users.setUsername(usuarioDTO.getNombreUsuario());
        users.setEmail(usuarioDTO.getEmail());
        users.setEnabled(usuarioDTO.getEnabled());

        return users;

    }

    public List<Users> mapToObjList(List<UsuarioDTO> usuarioDTOs) {
        List<Users> users = new ArrayList<Users>();
        for (UsuarioDTO udto : usuarioDTOs) {
            users.add(this.map(udto));
        }
        return users;
    }

	public String mapFechaActualizacion(Users user)throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat(FORMATO_FECHA);
		Date fechaBase = sdf.parse(FECHA_BASE);		
		Date fechaActualizacion = new Date(user.getUdateTimeStamp());
		if(!fechaActualizacion.before(fechaBase)){
			return sdf.format(fechaActualizacion);
		}else{
			throw new Exception();
		}		
		
		
	}
}
