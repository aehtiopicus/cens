package com.aehtiopicus.cens.service;

import java.util.Map;

import javax.mail.MessagingException;

import org.apache.log4j.Logger;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

@Service
public class EmailServiceImpl implements EmailService {
	
	private static final Logger logger = Logger.getLogger(EmailServiceImpl.class);
	
	private JavaMailSender mailSender;
	private VelocityEngine velocityEngine;
	
	private String pathTemplates;
	private String listaDirecciones;
	private String from;


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
		return new MimeMessageHelper(this.mailSender.createMimeMessage(), false);
	}
	
	public void send(MimeMessageHelper message, String template, Map model){
		try{
			String content = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, pathTemplates + template , "utf-8", model);
			message.setText(content, true);
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
	public void enviarEmail(Map<String, String> model, String subject){
		try{
			MimeMessageHelper message = this.getMessage();
			message.setFrom(this.getFrom());
			String[] myStringArray = this.getListaDirecciones().split(";");
			message.setTo(myStringArray);
			message.setSubject(subject);
			
			this.send(message, "shareByEmail.vm", model);
			}catch(Exception e){
				logger.error(e);
			}
	}

	@Override
	public void enviarEmail(String templateName, Map<String, String> model,	String subject) {

		try{
			MimeMessageHelper message = this.getMessage();
			message.setFrom(this.getFrom());
			String[] myStringArray = this.getListaDirecciones().replaceAll(" ","").split(";");
			message.setTo(myStringArray);
			message.setSubject(subject);
			
			this.send(message, templateName, model);
			}catch(Exception e){
				logger.error(e);
				e.printStackTrace();
			}
		
	}

}