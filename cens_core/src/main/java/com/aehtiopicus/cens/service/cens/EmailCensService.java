package com.aehtiopicus.cens.service.cens;

import java.util.Map;





import javax.mail.MessagingException;

import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public interface EmailCensService {
	
	public void send(MimeMessageHelper message, String templatePath, Map<String,Object> model);
	
	public MimeMessageHelper getMessage()  throws MessagingException;
	
	String getListaDirecciones();

	
	public void enviarNotificacionEmail(Map<String, Object> model, String toEmail);

	public void enviarNotificacionEmailNoLeido(Map<String, Object> data,
			String key);
	
}