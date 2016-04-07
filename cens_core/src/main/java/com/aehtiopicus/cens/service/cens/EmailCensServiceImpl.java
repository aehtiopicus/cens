package com.aehtiopicus.cens.service.cens;

import java.util.Map;

import javax.activation.DataSource;
import javax.activation.URLDataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.aehtiopicus.cens.utils.CensException;

@Service
public class EmailCensServiceImpl implements EmailCensService {
	
	private static final Logger logger = Logger.getLogger(EmailCensServiceImpl.class);
	
	private static final String MAX_DAYS_NOT_SEEN = "#{notificacionProperties['max_no_notificado']}";
	
	@Value(MAX_DAYS_NOT_SEEN)
	private int days;
	
	
	private JavaMailSender mailSender;
	private VelocityEngine velocityEngine;
	
	private String pathTemplates;
	private String listaDirecciones;
	private String from;
	private static final String LOGO = "../../css/midasUI-theme/images/logo_cens.png";


	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}
	
	public void setListaDirecciones(String listaDirecciones) {
		this.listaDirecciones = listaDirecciones;
	}
	
	public void setPathTemplates(String pathTemplates) {
		this.pathTemplates = pathTemplates;
	}

	public void setFrom(String from) {
		this.from = from;
	}
	
	@Override
	public String getListaDirecciones() {
		return listaDirecciones;
	}


	public String getFrom() {
		return from;
	}

	
	public MimeMessageHelper getMessage() throws MessagingException{
		MimeMessage mm = this.mailSender.createMimeMessage();
		return new MimeMessageHelper(mm, true);
	}
	
	public void send(MimeMessageHelper message, String template, Map<String,Object> model){
		try{
			String content = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, pathTemplates + template , "utf-8", model);
			message.setText(content, true);
			message.addInline("logo", getDataHandler(LOGO));
			message.getMimeMultipart();
			this.mailSender.send(message.getMimeMessage());
		}catch(MailException me){
			logger.error("Mail Exception tratando de enviar el mail", me);
			me.printStackTrace();
		}catch (MessagingException me) {
			logger.error("Messaging Exception tratando de enviar el mail", me);
			me.printStackTrace();
		}catch (Exception e) {
			logger.error("Exception tratando de enviar el mail", e);
			e.printStackTrace();
		}
	}
	
	
	@Override
	public void enviarNotificacionEmail(Map<String,Object> model,String toEmail){
		try{
			MimeMessageHelper message = this.getMessage();
			message.setFrom(this.getFrom());			
			message.setTo(toEmail);
			message.setSubject("Notificación de Actividad");
			
			this.send(message, "notificacionEmail.vm", model);
			}catch(Exception e){
				logger.error(e);
				e.printStackTrace();
			}
	}
	
//	private String encodeBase64Img(String imgPath) throws CensException{
//		
//		try{
//			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
//			
//			ByteArrayOutputStream bos = new ByteArrayOutputStream();
//			IOUtils.copy(classLoader.getResourceAsStream(imgPath), bos);
//			return  Base64.encodeBase64String(bos.toByteArray());
//		}catch(Exception e){
//			throw new CensException("Error al cargar el logo");
//		}
//		
//	}
	
	private DataSource getDataHandler(String imgPath) throws CensException{
		
	
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			return new URLDataSource(classLoader.getResource(imgPath));
		
		
	}

	@Override
	public void enviarNotificacionEmailNoLeido(Map<String, Object> model,
			String toEmail) {
		try{
			MimeMessageHelper message = this.getMessage();
			message.setFrom(this.getFrom());			
			message.setTo(toEmail);
			message.setSubject("Seguimiento de Información");
			model.put("dias", days);
			
			this.send(message, "notificacionEmailNoLeido.vm", model);
			}catch(Exception e){
				logger.error(e);
				e.printStackTrace();
			}
		
	}
	

}