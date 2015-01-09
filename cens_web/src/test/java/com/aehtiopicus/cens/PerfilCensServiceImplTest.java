package com.aehtiopicus.cens;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.aehtiopicus.cens.domain.entities.Perfil;
import com.aehtiopicus.cens.domain.entities.Usuarios;
import com.aehtiopicus.cens.enumeration.PerfilTrabajadorCensType;
import com.aehtiopicus.cens.service.cens.PerfilCensService;
import com.aehtiopicus.cens.service.cens.UsuarioCensService;
import com.aehtiopicus.cens.utils.CensException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestContextConfig.class)
@TransactionConfiguration(defaultRollback = true)
@Transactional
@ActiveProfiles("development")
public class PerfilCensServiceImplTest {

	@Autowired
	@Qualifier("perfilCensServiceImpl")
	private PerfilCensService service;
	@Autowired
	private UsuarioCensService usuarioCensService; 
	private Usuarios usuario;
	
	@Before
	public void setUp(){
		MockitoAnnotations.initMocks(this);
		usuario = new Usuarios();
		usuario.setId(Mockito.anyLong());
	}
	
	@Test
	public void testCreation() throws Exception{
		
		Perfil perfil =service.addPerfilToUser(usuarioCensService.saveUsuario(usuario), PerfilTrabajadorCensType.ADMINISTRADOR);
		Assert.assertNotNull(perfil);
	}
	
	@Test
	public void testCreationDuplicated() throws Exception{
		
		usuario = usuarioCensService.saveUsuario(usuario);
		Perfil perfil = null;
		
		usuario.setPerfil(new ArrayList<Perfil>());
		
		
		for(int i = 0 ; i<2;i++){
			perfil =  new Perfil();
			perfil.setPerfilType(PerfilTrabajadorCensType.ADMINISTRADOR);
			usuario.getPerfil().add(perfil);
			service.addPerfilesToUsuarios(usuario);
		}
		List<Perfil> a =service.listPerfilFromUsuario(usuario);
		Assert.assertTrue(a.size()==1);
	}
	
	@Test
	public void testListPerfiles() throws Exception{
		usuario = usuarioCensService.saveUsuario(usuario);
		for(PerfilTrabajadorCensType perfilTrabajadorCensType : PerfilTrabajadorCensType.values()){
			service.addPerfilToUser(usuario, perfilTrabajadorCensType);
		}
		List<Perfil> perfiles =service.listPerfilFromUsuario(usuario);
		Assert.assertNotNull(perfiles);
		Assert.assertEquals(PerfilTrabajadorCensType.values().length, perfiles.size());
	}
	
	public static void main(String[] args) {
		try {
			FileInputStream fileInputStream = new FileInputStream("c:\\expor_dt.xlsx");
			XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
			XSSFSheet worksheet = workbook.getSheet("Export Worksheet");
			Date d = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			String date = sdf.format(d);
			StringBuilder sb = new StringBuilder("INSERT INTO DIM_DOC_TYPE (DOC_TYPE,DOC_SUB_TYPE,DOC_VERSION,CREATED_BY,UPDATED_BY,CREATED_DATE,UPDATED_DATE) VALUES ");
			for(int i = 1; i<worksheet.getPhysicalNumberOfRows();i++){
				XSSFRow row1 = worksheet.getRow(i);
				XSSFCell cellA1 = row1.getCell( 0);
				String a = cellA1.getStringCellValue();
				XSSFCell cellB1 = row1.getCell(1);
				String b = cellB1.getStringCellValue();
				XSSFCell cellC1 = row1.getCell( 2);
				String c = cellC1.getStringCellValue();
				c = (c==null || c.isEmpty()) ? null : "'"+c+"'";
				sb.append( "('"+a+"','"+b+"',"+c+",-1,-1,'"+date+"','"+date+"'),");
			}
			String finaldata = sb.toString();

System.out.println(finaldata.substring(0, finaldata.length()-1)+";");
	
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
