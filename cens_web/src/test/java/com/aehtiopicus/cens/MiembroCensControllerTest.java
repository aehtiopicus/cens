package com.aehtiopicus.cens;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.aehtiopicus.cens.dto.cens.MiembroCensDto;
import com.aehtiopicus.cens.dto.cens.PerfilDto;
import com.aehtiopicus.cens.dto.cens.UsuariosDto;
import com.aehtiopicus.cens.enumeration.PerfilTrabajadorCensType;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = TestContextConfig.class)
@TransactionConfiguration(defaultRollback = true)
@Transactional
@ActiveProfiles("development")
public class MiembroCensControllerTest {

	private static final int MIEMBRO_SIZE = 5;
	private static final Logger log = LoggerFactory.getLogger(MiembroCensControllerTest.class);
	
	@Autowired
    private WebApplicationContext wac;
	
	private MockMvc mockMvc;
	
	private Gson gson;
	
	private MiembroCensDto miembroCensDto;
	
	private UsuariosDto usuarioDto;
	
	private PerfilDto perfilDto;
	
	private Type listType;
	
	private Mapper mapper = new DozerBeanMapper();
	
	@Before
	public void setUp(){
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
		listType =  new TypeToken<ArrayList<MiembroCensDto>>() {
	    }.getType();
		gson = TestUtils.getSon();		
		miembroCensDto = new MiembroCensDto();
		miembroCensDto.setApellido("test+a");
		miembroCensDto.setBaja(false);
		miembroCensDto.setDni("dni1234");
		miembroCensDto.setFechaNac(new java.util.Date());
		miembroCensDto.setNombre("vaca");
		usuarioDto = new UsuariosDto();
		usuarioDto.setEnabled(true);
		usuarioDto.setPassword("vaca");
		usuarioDto.setUsername("doncow");
		perfilDto = new PerfilDto();
		perfilDto.setPerfilType(PerfilTrabajadorCensType.ASESOR);
		usuarioDto.setPerfil(new ArrayList<PerfilDto>());
		usuarioDto.getPerfil().add(perfilDto);
		miembroCensDto.setUsuario(usuarioDto);
	}
	
	@Test
	public void testRestCreation() throws Exception{
		
		 MvcResult mvcResult=this.mockMvc.perform(post("/miembroCens").contentType(MediaType.APPLICATION_JSON)
		.content(gson.toJson(Arrays.asList(miembroCensDto)))).andExpect(status().isOk()).andReturn();
		 List<MiembroCensDto> dto =gson.fromJson(mvcResult.getResponse().getContentAsString(), listType);		 
		 Assert.assertNotNull(dto);
		 Assert.assertTrue(dto.size()==1);
		 Assert.assertNotNull(dto.get(0).getId());
		 log.info(mvcResult.getResponse().getContentAsString());
		 
	}
	
	@Test
	public void testRestCreationFailure() throws Exception{
		PerfilDto pdto = new PerfilDto();
		pdto.setPerfilType(PerfilTrabajadorCensType.ALUMNO);
		usuarioDto.getPerfil().add(pdto);
		 MvcResult mvcResult= this.mockMvc.perform(post("/miembroCens").contentType(MediaType.APPLICATION_JSON)
		.content(gson.toJson(Arrays.asList(miembroCensDto)))).andExpect(status().isBadRequest()).andReturn();
		Assert.assertTrue(mvcResult.getResponse().getStatus()== HttpStatus.BAD_REQUEST.value());
		log.info(mvcResult.getResponse().getContentAsString());
	}
	
	@Test
	public void testRestRetrieveMiembros() throws Exception{
		List<MiembroCensDto> listMiembros = new ArrayList<MiembroCensDto>();
		for(int i = 0;i<MIEMBRO_SIZE;i++){
			listMiembros.add(copyToOther(miembroCensDto));		 
		}
		this.mockMvc.perform(post("/miembroCens").contentType(MediaType.APPLICATION_JSON)
				.content(gson.toJson(listMiembros))).andExpect(status().isOk()).andReturn();
					 
		 MvcResult mvcResult=this.mockMvc.perform(get("/miembroCens").contentType(MediaType.APPLICATION_JSON).
				 accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
		
		 List<MiembroCensDto> dto =gson.fromJson(mvcResult.getResponse().getContentAsString(), listType);
		 Assert.assertNotNull(dto);
		  Assert.assertNotNull(dto);
		 Assert.assertTrue(dto.size()==MIEMBRO_SIZE);
		 Assert.assertNotNull(dto.get(0).getId());
		 log.info(mvcResult.getResponse().getContentAsString());
	}
	
	@Test
	public void testRestRetrieveMiembro()throws Exception{
		
		
		 MvcResult mvcResult=this.mockMvc.perform(post("/miembroCens").contentType(MediaType.APPLICATION_JSON)
				.content(gson.toJson(Arrays.asList(miembroCensDto)))).andExpect(status().isOk()).andReturn();
					 		
		 List<MiembroCensDto> dto =gson.fromJson(mvcResult.getResponse().getContentAsString(), listType);
		 
		 mvcResult = this.mockMvc.perform(get("/miembroCens/"+dto.get(0).getId()).contentType(MediaType.APPLICATION_JSON).
				 accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();	
		 MiembroCensDto miembroDto =gson.fromJson(mvcResult.getResponse().getContentAsString(), MiembroCensDto.class);
		 Assert.assertNotNull(miembroDto);
		log.info(mvcResult.getResponse().getContentAsString());
	}
	
	@Test
	public void testRestUpdateMiembro()throws Exception{
		
		
		 MvcResult mvcResult=this.mockMvc.perform(post("/miembroCens").contentType(MediaType.APPLICATION_JSON)
				.content(gson.toJson(Arrays.asList(miembroCensDto)))).andExpect(status().isOk()).andReturn();
					 		
		 List<MiembroCensDto> dto =gson.fromJson(mvcResult.getResponse().getContentAsString(), listType);
		 
		 MiembroCensDto mcDto = copyToOther(dto.get(0));
		 
		 mcDto.setApellido("la gran vaca");
		 mcDto.getUsuario().setUsername("aehtiopicus");
		 
		 mvcResult=this.mockMvc.perform(put("/miembroCens/"+mcDto.getId()).contentType(MediaType.APPLICATION_JSON)
					.content(gson.toJson(mcDto))).andExpect(status().isOk()).andReturn();
		 		
		 MiembroCensDto miembroDto =gson.fromJson(mvcResult.getResponse().getContentAsString(), MiembroCensDto.class);
		 Assert.assertNotNull(miembroDto);
		 Assert.assertTrue(miembroDto.getId().equals(dto.get(0).getId()));
		 Assert.assertTrue(!miembroDto.getApellido().equals(dto.get(0).getApellido()));
		log.info(mvcResult.getResponse().getContentAsString());
	}
	
	@Test
	public void testRestUpdateMiembroAddPerfil()throws Exception{
		
		
		 MvcResult mvcResult=this.mockMvc.perform(post("/miembroCens").contentType(MediaType.APPLICATION_JSON)
				.content(gson.toJson(Arrays.asList(miembroCensDto)))).andExpect(status().isOk()).andReturn();
					 		
		 List<MiembroCensDto> dto =gson.fromJson(mvcResult.getResponse().getContentAsString(), listType);
		 
		 MiembroCensDto mcDto = copyToOther(dto.get(0));
		 
		PerfilDto pdto = new PerfilDto();
		pdto.setPerfilType(PerfilTrabajadorCensType.PROFESOR);
		mcDto.getUsuario().getPerfil().add(pdto);
		 mvcResult=this.mockMvc.perform(put("/miembroCens/"+mcDto.getId()).contentType(MediaType.APPLICATION_JSON)
					.content(gson.toJson(mcDto))).andExpect(status().isOk()).andReturn();
		 		
		 MiembroCensDto miembroDto =gson.fromJson(mvcResult.getResponse().getContentAsString(), MiembroCensDto.class);
		 Assert.assertNotNull(miembroDto);
		 Assert.assertTrue(miembroDto.getUsuario().getPerfil().size()==2);

		log.info(mvcResult.getResponse().getContentAsString());
	}
	
	@Test
	public void testRestUpdateMiembroRemovePerfil()throws Exception{
		PerfilDto pdto = new PerfilDto();
		pdto.setPerfilType(PerfilTrabajadorCensType.PROFESOR);
		miembroCensDto.getUsuario().getPerfil().add(pdto);
		
		 MvcResult mvcResult=this.mockMvc.perform(post("/miembroCens").contentType(MediaType.APPLICATION_JSON)
				.content(gson.toJson(Arrays.asList(miembroCensDto)))).andExpect(status().isOk()).andReturn();
					 		
		 List<MiembroCensDto> dto =gson.fromJson(mvcResult.getResponse().getContentAsString(), listType);
		 
		 MiembroCensDto mcDto = copyToOther(dto.get(0));
		 
		
		mcDto.getUsuario().getPerfil().remove(0);
		 mvcResult=this.mockMvc.perform(put("/miembroCens/"+mcDto.getId()).contentType(MediaType.APPLICATION_JSON)
					.content(gson.toJson(mcDto))).andExpect(status().isOk()).andReturn();
		 		
		 MiembroCensDto miembroDto =gson.fromJson(mvcResult.getResponse().getContentAsString(), MiembroCensDto.class);
		 Assert.assertNotNull(miembroDto);
		 Assert.assertTrue(miembroDto.getUsuario().getPerfil().size()==1);

		log.info(mvcResult.getResponse().getContentAsString());
	}
	
	private MiembroCensDto copyToOther(MiembroCensDto miembroDto){
		return mapper.map(miembroDto, MiembroCensDto.class);
	}
}
