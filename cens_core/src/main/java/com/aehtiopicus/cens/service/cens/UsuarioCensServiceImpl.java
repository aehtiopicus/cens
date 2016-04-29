package com.aehtiopicus.cens.service.cens;

import java.io.OutputStream;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.aehtiopicus.cens.domain.entities.FileCensInfo;
import com.aehtiopicus.cens.domain.entities.Usuarios;
import com.aehtiopicus.cens.enumeration.cens.FileCensInfoType;
import com.aehtiopicus.cens.enumeration.cens.MaterialDidacticoUbicacionType;
import com.aehtiopicus.cens.enumeration.cens.PerfilTrabajadorCensType;
import com.aehtiopicus.cens.repository.cens.UsuariosCensRepository;
import com.aehtiopicus.cens.service.cens.ftp.FTPUsuarioCensService;
import com.aehtiopicus.cens.utils.CensException;
import com.google.common.base.Function;
import com.google.common.collect.Lists;

@Service
public class UsuarioCensServiceImpl implements UsuarioCensService{
	
	private static final String DEFAUL_PASSWORD = "#{censProperties['password']}";
	private static final Logger logger = LoggerFactory.getLogger(UsuarioCensServiceImpl.class);
	
	@Value(DEFAUL_PASSWORD)
	private String password;
	
	@Autowired
	private UsuariosCensRepository usuariosCensRepository;
	
	@Autowired
	private FileCensService fileCensService;
	
	@Autowired
	private FTPUsuarioCensService ftpUsuarioCensService;
	
	@Autowired
	private ImageResizeCensService imageResizeCensService;
	
	@Override
	public Usuarios saveUsuario(Usuarios usuarios) throws CensException{
		try{	
			validate(usuarios);
			return usuariosCensRepository.save(usuarios);
		}catch(CensException e){
			throw e;
		}catch(Exception e){
			throw new CensException ("No se puede guardar el usuario ",e);
		}
	}

	private void validate(Usuarios usuarios ) throws CensException{
		if(usuarios==null){
			throw new CensException("No se puede guardar el usuario");
		}
		usuarios.setUsername(usuarios.getUsername().toLowerCase());
		Usuarios u = findUsuarioByUsername(usuarios.getUsername());
		
		if(u!=null && (usuarios.getId()==null || !u.getId().equals(usuarios.getId()))){
			throw new CensException("No se puede guardar el usuario","username","El nombre de usuario ya existe");
		}
		if(usuarios.getId()==null){
			usuarios.setPassword(password);
		}
	}
	@Override
	public void deleteUsuarioByMiembroId(Long miembroId) {
		usuariosCensRepository.softDeleteByMiembro(miembroId);
		
	}
	
	@Override
	public Usuarios findUsuarioByUsername(String username){
		return usuariosCensRepository.findByUsername(username);
	}

	@Override
	public Usuarios findUsuarioById(Long id) {
		return usuariosCensRepository.findOne(id);
	}

	@Override
	@Transactional
	public void resetPassword(Long usuarioId, String defaulPassword) {
		usuariosCensRepository.resetPassword(usuarioId,defaulPassword);
		
	}

	@Override
	@Transactional
	public void updateImage(Usuarios user, MultipartFile file)
			throws CensException {
		if(file!=null){
			user.setFileInfo(handleFtp(file, user));
			usuariosCensRepository.save(user);
		}
		
	}
	
	private FileCensInfo handleFtp(MultipartFile file, Usuarios usuario) throws CensException{
		FileCensInfo fci = null;
		if(file!=null ){
			
			String filePath = ftpUsuarioCensService.avatarPath(usuario);			
			String fileName = new Date().getTime()+file.getOriginalFilename();
			if(usuario.getFileInfo()!=null){
				fileCensService.deleteFileCensInfo(usuario.getFileInfo());
			}
			fci = fileCensService.createNewFileCensService(file,usuario.getId(),PerfilTrabajadorCensType.USUARIO,filePath,fileName, MaterialDidacticoUbicacionType.FTP,FileCensInfoType.FOTO);
			
			logger.info("iniciando ftp upload del avatar del usuario");
			ftpUsuarioCensService.guardarImagenUsuario(imageResizeCensService.resizeImage(file),(filePath+fileName));
			logger.info("avatar subido. ruta = "+filePath);						
		}
		return fci;
	}

	@Override
	public void getAvatar(String picturePath,OutputStream baos) throws CensException {
		ftpUsuarioCensService.leerAvatar(picturePath,baos);
		
	}

	@Override
	public List<Object[]> getUsuarioActivoByUserName() {
		return usuariosCensRepository.findUsernameActivos();
	}
	
	@Override
	public List<Object[]> getUsuarioActivoByUserNameWithoutEmail() {
		return usuariosCensRepository.findUsernameActivosWithoutEnamil();
	}
	@Override
	public List<Object[]> getUsuarioAsesorActivoByUserName() {
		return usuariosCensRepository.findAsesorActivos();
	}

	@Override
	public void getDefaultAvatar(OutputStream baos) throws CensException {
		try{
			ClassLoader cl = Thread.currentThread().getContextClassLoader();		
			IOUtils.copy(cl.getResourceAsStream("../.."+CensServiceConstant.DEFAULT_IMG), baos);
		}catch(Exception e){			
			logger.info("Error al cargar la imagen");
			throw new CensException(e);
		}
		
	}
	
	@Override
	public List<Long> asesoresId(){
		String allAsesores = usuariosCensRepository.findAllAsesorIds();
		List<Long> asesoresIdList = null;
		if(StringUtils.isNotEmpty(allAsesores)){
			asesoresIdList = Lists.transform(Arrays.asList(allAsesores.split(",")), new Function<String,Long>() {				
				@Override
				public Long apply(String id) {
					// TODO Auto-generated method stub
					return Long.valueOf(id);
				}
			});
			
		}
		return asesoresIdList;
	}
	

}
