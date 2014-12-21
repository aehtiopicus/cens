package com.aehtiopicus.cens.service;

import java.util.Map;


import javax.mail.MessagingException;

import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public interface EmailService {
	public void send(MimeMessageHelper message, String templatePath, Map model);
	public MimeMessageHelper getMessage()  throws MessagingException;
	String getListaDirecciones();
	
	void enviarEmail(Map<String, String> model, String subject);
	void enviarEmail(String templateName, Map<String, String> model, String subject);
}