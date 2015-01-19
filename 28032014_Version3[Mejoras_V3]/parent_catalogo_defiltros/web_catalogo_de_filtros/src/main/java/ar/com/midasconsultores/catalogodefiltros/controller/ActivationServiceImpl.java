package ar.com.midasconsultores.catalogodefiltros.controller;

import java.text.SimpleDateFormat;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ar.com.midasconsultores.catalogodefiltros.configuration.HardwareInformation;
import ar.com.midasconsultores.catalogodefiltros.domain.Users;
import ar.com.midasconsultores.catalogodefiltros.dto.ActivationDataDTO;
import ar.com.midasconsultores.catalogodefiltros.security.CustomAuthenticationProvider;
import ar.com.midasconsultores.catalogodefiltros.service.CFSUpdateService;
import ar.com.midasconsultores.catalogodefiltros.service.DumpService;
import ar.com.midasconsultores.catalogodefiltros.service.UsuarioService;
import ar.com.midasconsultores.catalogodefiltros.utils.UpdateType;
import ar.com.midasconsultores.catalogodefiltros.validator.CredencialesValidator;
import ar.com.midasconsultores.utils.appvalidation.InterpretadorDeSerial;

@Service(value = ActivationServiceImpl.ACTIVATION_SERVICE_NAME)
public class ActivationServiceImpl implements ActivationService {

    static final String ACTIVATION_SERVICE_NAME = "activationService";

    private static final String TXT_FILE_EXTENSION = ".txt";

    private static final String DUMP_FILE_EXTENSION = ".cfs";

    private static final String FAKE_FAKE_COM = "fake@fake.com";

    private static final String ERROR_AL_CREAR_USUARIO = "Error al crear usuario.";

    @Autowired
    private CFSUpdateService cfsUpdateService;
    
    @Autowired
    @Qualifier("authenticationManager")
    protected AuthenticationManager authenticationManager;

    @Autowired
    private CredencialesValidator validator;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private DumpService dumpService;

    @Override
    public ActivationDataDTO obtenerInformacionActivacion() {

        ActivationDataDTO adDto = new ActivationDataDTO();

        adDto.setValor(HardwareInformation.generarCodigoMaquina());

        return adDto;
    }

    @Override
    public boolean comprobarActivacion() {
        boolean access = false;
        try {
            Authentication auth = new UsernamePasswordAuthenticationToken(null,
                    null);
            authenticationManager.authenticate(auth);
            SecurityContextHolder.getContext().setAuthentication(auth);
            access = true;
        } catch (Exception e) {
            invalidarUsuario();
        }
        return access;
    }

    @Override
    public boolean validarCodigos(MultipartFile mf) {
        String fileName = mf.getOriginalFilename();
        try {
            System.out.print(fileName);
            if (fileName.contains(TXT_FILE_EXTENSION)) {

                InterpretadorDeSerial is = new InterpretadorDeSerial(mf.getBytes());

                Users usuario = usuarioService
                        .obtenerUsuario();

                usuario = copiarDatosUsuario(is, usuario);
                if (validator.validar(usuario)) {
                    usuario.setEnabled(true);
                    usuarioService.modificarUsuario(usuario);
                    return true;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private Users copiarDatosUsuario(InterpretadorDeSerial is, Users usuario)
            throws Exception {
        usuario = crearUsuario(usuario);
        usuario.setIdEquipo(is.getId());
        usuario.setFechaDeCaducidadDeLicencia(is.getCaducidad());
        usuario.setFechaDeInicioDelicencia(new java.util.Date().getTime());
        usuario.setCodigoDeUsuario(is.getCodigoDeUsuario());
        usuario.setUdateTimeStamp(0l);
        usuario.setVendedor(is.isVendedor());
        return usuario;

    }

    private Users crearUsuario(Users usuario) throws Exception {
        if (usuario == null) {
            usuario = new Users();
            usuario.setEnabled(true);
            usuario.setUsername(CustomAuthenticationProvider.DEFAULT_USER_NAME);
            usuario.setPassword(UUID.randomUUID().toString());
            usuario.setEmail(FAKE_FAKE_COM);
            if (!usuarioService.crearUsuario(usuario)) {
                throw new Exception(ERROR_AL_CREAR_USUARIO);
            }

        }
        return usuario;
    }

    private void invalidarUsuario() {
        Users usuario = usuarioService
                .obtenerUsuario();
        if (usuario != null) {
            usuario.setEnabled(false);
            usuarioService.modificarUsuario(usuario);
        }
    }

//    @Override
//    public boolean dumpCodigos(MultipartFile mf) {
//        String fileName = mf.getOriginalFilename();
//        try {
//
//            if (fileName.contains(DUMP_FILE_EXTENSION)) {
//
//                long updateTimeStamp = new SimpleDateFormat("ddMMyyyyhhmmss").parse(dumpService.realizarVolcadoDB(new String(mf.getBytes()))).getTime();
//                Users usuario = usuarioService.obtenerUsuario();
//
//                usuario.setUdateTimeStamp(updateTimeStamp);
//                usuarioService.modificarUsuario(usuario);
//                cfsUpdateService.update(usuario, updateTimeStamp, UpdateType.DATABASE);
//                return true;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
}
