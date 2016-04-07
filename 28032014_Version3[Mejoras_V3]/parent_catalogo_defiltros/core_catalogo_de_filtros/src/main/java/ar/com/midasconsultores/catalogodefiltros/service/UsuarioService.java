package ar.com.midasconsultores.catalogodefiltros.service;

import java.util.List;

import ar.com.midasconsultores.catalogodefiltros.domain.Users;

public interface UsuarioService {

    public boolean crearUsuario(Users usuario);

    public Users obtenerUsuario();

    public void modificarUsuario(Users usuario);

    public List<Users> getListaUsuarios();

    public Users getUsuario(String id);

    public boolean bajaUsuario(Users user);

    public boolean existeUsuario();

    public boolean existeCorreo(String correo);

    public boolean isEnabled(String userName);
}
