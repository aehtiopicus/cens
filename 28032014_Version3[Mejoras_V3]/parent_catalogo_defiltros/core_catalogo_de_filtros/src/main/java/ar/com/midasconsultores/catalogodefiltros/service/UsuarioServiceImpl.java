package ar.com.midasconsultores.catalogodefiltros.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ar.com.midasconsultores.catalogodefiltros.domain.Authorities;
import ar.com.midasconsultores.catalogodefiltros.domain.Users;
import ar.com.midasconsultores.catalogodefiltros.repository.UsuariosRepository;
import ar.com.midasconsultores.catalogodefiltros.utils.AuthoritiesEnum;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuariosRepository usuariosRepository;
    
    private static final Logger logger = LoggerFactory.getLogger(UsuarioServiceImpl.class);

    @Override
    public boolean crearUsuario(Users usuario) {
        Authorities defaultAuthority = AuthoritiesEnum.USER.getAuthorityObject();
        usuario.getAuthorities().add(defaultAuthority);
        logger.info("userName=" + usuario.getUsername());
        usuariosRepository.save(usuario);
        return true;
    }

    @Override
    public Users obtenerUsuario() {
    	List<Users> users = usuariosRepository.findAll();
    	Users user = null;
    	if(users!=null && !users.isEmpty()){
            user= users.get(0);	
    	}
    	return user;
    }

    @Override
    public List<Users> getListaUsuarios() {
        List<Users> listaUsuarios = usuariosRepository.findAll();
        return listaUsuarios;
    }


      @Override
    public Users getUsuario(String id) {
        Users usuario = usuariosRepository.findByUsername(id);
        return usuario;
    }

    @Override
    public void modificarUsuario(Users usuario) {
        usuariosRepository.save(usuario);
        logger.info("USUARIO MODIFICADO");
    }

    @Override
    public boolean bajaUsuario(Users user) {
        try {
            user.setEnabled(false);
            usuariosRepository.save(user);
            logger.info("USUARIO DADO DE BAJA");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean existeUsuario() {
        try {
            Users usuario = usuariosRepository.findAll().get(0);
            if (usuario == null) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            System.out.println("Error: UsuarioService.existeUsuario - "
                    + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean existeCorreo(String correo) {
        try {
            if (usuariosRepository.findAllByEmail(correo).isEmpty()) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            System.out.println("Error: UsuarioService.existeCorreo - "
                    + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean isEnabled(String userName) {
        try {
            Users usuario = usuariosRepository.findByUsername(userName);
            if (usuario.isEnabled()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println("Error: UsuarioService.existeUsuario - "
                    + e.getMessage());
            return false;
        }
    }


}
